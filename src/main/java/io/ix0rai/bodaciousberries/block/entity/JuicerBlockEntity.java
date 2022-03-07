package io.ix0rai.bodaciousberries.block.entity;

import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO: test hopper compatibility
public class JuicerBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory;
    private int brewTime;
    private Item itemBrewing;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return brewTime;
        }

        @Override
        public void set(int index, int value) {
            brewTime = value;
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public JuicerBlockEntity(BlockPos pos, BlockState state) {
        super(BodaciousBlocks.JUICER_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
        this.brewTime = 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    private static void craft(World world, BlockPos pos, DefaultedList<ItemStack> slots) {
        ItemStack[] ingredients = getIngredients(slots);

        //craft items for all three slots - as long as there's a bottle to contain the juice
        for(int i = 0; i < 3; i ++) {
            if (slots.get(i).getItem().equals(Items.GLASS_BOTTLE)) {
                slots.set(i, JuicerRecipes.craft(ingredients[0], ingredients[1], ingredients[2], slots.get(i)));
            }
        }

        //decrement stack and give recipe remainder
        for (ItemStack ingredient : ingredients) {
            ingredient.decrement(1);
            if (ingredient.getItem().hasRecipeRemainder()) {
                ItemStack newStack = new ItemStack(ingredient.getItem().getRecipeRemainder());
                if (ingredient.isEmpty()) {
                    ingredient = newStack;
                } else {
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), newStack);
                }
            }

            ingredient.decrement(1);
        }

        slots.set(3, ingredients[0]);
        slots.set(4, ingredients[1]);
        slots.set(5, ingredients[2]);
    }

    private static ItemStack[] getIngredients(DefaultedList<ItemStack> slots) {
        return new ItemStack[]{slots.get(3), slots.get(4), slots.get(5)};
    }

    public static void tick(World world, BlockPos pos, BlockState state, JuicerBlockEntity juicer) {
        boolean canCraft = canCraft(juicer.inventory);
        boolean isBrewing = juicer.brewTime > 0;
        ItemStack ingredient = juicer.inventory.get(3);

        if (isBrewing) {
            juicer.brewTime --;

            //if brewing is finished, craft the juices
            if (juicer.brewTime == 0 && canCraft) {
                craft(world, pos, juicer.inventory);
                markDirty(world, pos, state);
            } else if (!canCraft || !ingredient.isOf(juicer.itemBrewing)) {
                //if we cannot craft, the ingredient has been removed/changed, and we should stop brewing without giving a result
                juicer.brewTime = 0;
                markDirty(world, pos, state);
            }
        } else if (canCraft) {
            //if we're not currently brewing, start brewing with the ingredient
            juicer.brewTime = 400;
            juicer.itemBrewing = ingredient.getItem();
            markDirty(world, pos, state);
        }
    }

    private static boolean canCraft(DefaultedList<ItemStack> slots) {
        Item ingredient1 = slots.get(3).getItem();
        Item ingredient2 = slots.get(4).getItem();
        Item ingredient3 = slots.get(5).getItem();

        if (JuicerRecipes.isIngredient(ingredient1) && JuicerRecipes.isIngredient(ingredient2) && JuicerRecipes.isIngredient(ingredient3)) {
            for (int i = 0; i < 3; i ++) {
                ItemStack stack = slots.get(i);
                if (!stack.isEmpty() && stack.getItem().equals(Items.GLASS_BOTTLE) && JuicerRecipes.hasRecipeFor(ingredient1, ingredient2, ingredient3)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new JuicerScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
