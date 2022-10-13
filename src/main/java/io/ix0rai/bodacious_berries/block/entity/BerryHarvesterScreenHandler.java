package io.ix0rai.bodacious_berries.block.entity;

import io.ix0rai.bodacious_berries.registry.BodaciousBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BerryHarvesterScreenHandler extends BodaciousScreenHandler {
    public BerryHarvesterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    public BerryHarvesterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(BodaciousBlocks.BERRY_HARVESTER_SCREEN_HANDLER, syncId, playerInventory);
        checkSize(inventory, 9);
        inventory.onOpen(playerInventory.player);

        // harvester inventory
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18));
            }
        }

        addPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
