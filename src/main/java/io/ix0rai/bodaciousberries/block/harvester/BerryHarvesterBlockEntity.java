package io.ix0rai.bodaciousberries.block.harvester;

import io.ix0rai.bodaciousberries.block.BasicBerryBush;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.registry.BodaciousThings;
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
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BerryHarvesterBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private int tickCounter;
    private static final int ATTEMPT_HARVEST_ON = 100;

    public BerryHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(BodaciousThings.BERRY_HARVESTER_ENTITY, pos, state);
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

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public static void tick(World world, BlockPos pos, BlockState state, BerryHarvesterBlockEntity harvester) {
        harvester.tickCounter++;
        if (harvester.tickCounter >= ATTEMPT_HARVEST_ON) {
            //block the harvester is facing must be a berry bush
            BlockPos bushPos = pos.offset(state.get(BerryHarvesterBlock.FACING));
            BlockState bush = world.getBlockState(bushPos);

            //also ensure bush is ready to harvest
            if (bush.getBlock() instanceof BerryBush berryBush && berryBush.isFullyGrown(bush)) {
                ItemStack berries = new ItemStack(berryBush.getBerryType(), berryBush.getMaxBerryAmount());

                //find an open slot and insert it
                for (int i = 0; i < harvester.getItems().size(); i++) {
                    ItemStack stack = harvester.getItems().get(i);
                    if ((stack.isEmpty() || stack.getItem().equals(berries.getItem())) && stack.getCount() <= 64) {
                        berries.setCount(stack.getCount() + berries.getCount());
                        if (berries.getCount() > 64) {
                            //split the stack, inserting one stack of 64 and running the remainder back through the loop
                            harvester.setStack(i, new ItemStack(berries.getItem(), 64));
                            berries.decrement(64);
                        } else {
                            harvester.setStack(i, berries);
                            break;
                        }
                    }
                }

                //play pick sound and reset bush growth
                world.playSound(null, pos, BasicBerryBush.selectPickSound(world), SoundCategory.BLOCKS, 0.3F, 1.0F);
                berryBush.resetAge(world, bushPos, bush);
            }

            harvester.tickCounter = 0;
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[getItems().size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }

        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BerryHarvesterScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
