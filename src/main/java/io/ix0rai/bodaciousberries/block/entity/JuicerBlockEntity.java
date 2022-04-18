package io.ix0rai.bodaciousberries.block.entity;

import io.ix0rai.bodaciousberries.block.JuicerBlock;
import io.ix0rai.bodaciousberries.registry.Berries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import io.ix0rai.bodaciousberries.registry.Juices;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JuicerBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory, NamedScreenHandlerFactory {
    public static final int TOTAL_BREW_TIME = 300;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    private int brewTime = 0;
    private boolean makingDubiousJuice = false;
    private boolean makingBerryBlend = false;

    private final PropertyDelegate propertyDelegate = new ArrayPropertyDelegate(3) {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> brewTime;
                case 1 -> makingDubiousJuice ? 1 : 0;
                case 2 -> makingBerryBlend ? 1 : 0;
                default -> throw new IllegalArgumentException("invalid property index: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> brewTime = value;
                case 1 -> makingDubiousJuice = value == 1;
                case 2 -> makingBerryBlend = value == 1;
                default -> throw new IllegalArgumentException("invalid property index: " + index);
            }
        }
    };

    public JuicerBlockEntity(BlockPos pos, BlockState state) {
        super(BodaciousBlocks.JUICER_ENTITY, pos, state);
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

    private static void craft(World world, BlockPos pos, ItemStack result, Ingredient receptacle, DefaultedList<ItemStack> slots) {
        ItemStack[] ingredients = new ItemStack[]{slots.get(3), slots.get(4), slots.get(5)};

        //craft items for all three slots - as long as there's a bottle to contain the juice
        for(int i = 0; i < 3; i ++) {
            if (receptacle.test(slots.get(i))) {
                slots.set(i, new ItemStack(result.getItem()));
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

    public static void craft(World world, BlockPos pos, JuicerRecipe recipe, DefaultedList<ItemStack> slots) {
        craft(world, pos, recipe.getOutput(), recipe.getReceptacle(), slots);
    }

    public static void tick(World world, BlockPos pos, BlockState state, JuicerBlockEntity juicer) {
        Optional<JuicerRecipe> recipe = world.getRecipeManager().getFirstMatch(JuicerRecipe.Type.INSTANCE, juicer, world);
        boolean isBrewing = juicer.brewTime > 0;

        if (isBrewing) {
            juicer.brewTime --;

            //if brewing is finished, craft the juices
            if (juicer.hasValidReceptacle() && juicer.brewTime == 0) {
                if (recipe.isPresent()) {
                    craft(world, pos, recipe.get(), juicer.inventory);
                } else if (juicer.makingDubiousJuice) {
                    craft(world, pos, new ItemStack(Juices.DUBIOUS_JUICE), Ingredient.ofItems(Juices.JUICE_RECEPTACLE), juicer.inventory);
                }

                markDirty(world, pos, state);
                world.setBlockState(pos, state.with(JuicerBlock.RUNNING, false), Block.NOTIFY_LISTENERS);
                juicer.makingDubiousJuice = false;
            } else if (recipe.isEmpty() && !(juicer.makingDubiousJuice && juicer.hasAllIngredients()) || !juicer.hasValidReceptacle()) {
                //if we cannot craft, the ingredient has been removed/changed, and we should stop brewing without giving a result
                juicer.brewTime = 0;
                markDirty(world, pos, state);
                world.setBlockState(pos, state.with(JuicerBlock.RUNNING, false), Block.NOTIFY_LISTENERS);
                juicer.makingDubiousJuice = false;
                juicer.makingBerryBlend = false;
            }
        } else if (juicer.hasValidReceptacle()) {
            DefaultedList<ItemStack> inventory = juicer.getItems();

            if (recipe.isPresent()) {
                if (!recipe.get().ingredientsMatch(inventory.get(3))) {
                    juicer.makingBerryBlend = true;
                }

                //if we're not currently brewing, start brewing with the ingredient
                juicer.brewTime = TOTAL_BREW_TIME;
                markDirty(world, pos, state);
                world.setBlockState(pos, state.with(JuicerBlock.RUNNING, true), Block.NOTIFY_LISTENERS);
            } else if (inventory.get(3).isIn(Berries.BERRY_TAG) && inventory.get(4).isIn(Berries.BERRY_TAG) && inventory.get(5).isIn(Berries.BERRY_TAG)
                                && (inventory.get(0).getItem().equals(Juices.JUICE_RECEPTACLE) || inventory.get(1).getItem().equals(Juices.JUICE_RECEPTACLE) || inventory.get(2).getItem().equals(Juices.JUICE_RECEPTACLE))) {
                //everything in the juicer is a berry, so logically we can make something
                juicer.brewTime = TOTAL_BREW_TIME;
                markDirty(world, pos, state);
                world.setBlockState(pos, state.with(JuicerBlock.RUNNING, true), Block.NOTIFY_LISTENERS);
                juicer.makingDubiousJuice = true;
            }
        }
    }

    public boolean hasAllIngredients() {
        return !inventory.get(3).isEmpty() && !inventory.get(4).isEmpty() && !inventory.get(5).isEmpty();
    }

    public boolean hasValidReceptacle() {
        return JuicerRecipes.isReceptacle(inventory.get(0)) || JuicerRecipes.isReceptacle(inventory.get(1)) || JuicerRecipes.isReceptacle(inventory.get(2));
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

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }

        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot < 3) {
            return this.inventory.get(slot).isEmpty() && JuicerScreenHandler.JuicerOutputSlot.matches(stack);
        } else if (slot <= 5) {
            return JuicerRecipes.isIngredient(stack);
        } else {
            return false;
        }
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot < 3 && !JuicerRecipes.isReceptacle(stack);
    }
}
