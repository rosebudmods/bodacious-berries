package io.ix0rai.bodacious_berries.block.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public abstract class DefaultScreenHandler extends ScreenHandler {
    protected final Inventory inventory;

    protected DefaultScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
        super(type, syncId);
        this.inventory = playerInventory;
    }

    protected void addPlayerInventory(PlayerInventory playerInventory) {
        int m;
        int l;
        for (m = 0; m < 3; m ++) {
            for (l = 0; l < 9; l ++) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; m ++) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
