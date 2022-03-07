package io.ix0rai.bodaciousberries.block.entity;

import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import io.ix0rai.bodaciousberries.registry.items.Juice;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class JuicerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate brewTime;

    public JuicerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9), new ArrayPropertyDelegate(1));
    }

    /**
     * slot map:
     * 0: leftmost output slot
     * 1: middle output slot
     * 2: rightmost output slot
     * 3: ingredient slot
     * 4: ingredient slot 2
     * 5: ingredient slot 3
     */
    public JuicerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate brewTime) {
        super(BodaciousBlocks.JUICER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        this.brewTime = brewTime;
        this.addProperties(brewTime);

        //output slots
        this.addSlot(new JuicerOutputSlot(inventory, 0, 56, 51));
        this.addSlot(new JuicerOutputSlot(inventory, 1, 79, 58));
        this.addSlot(new JuicerOutputSlot(inventory, 2, 102, 51));
        //ingredient slots
        this.addSlot(new JuicerIngredientSlot(inventory, 3, 56, 17));
        this.addSlot(new JuicerIngredientSlot(inventory, 4, 79, 17));
        this.addSlot(new JuicerIngredientSlot(inventory, 5, 102, 17));

        //player inventory
        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public int getBrewTime() {
        return this.brewTime.get(0);
    }

    //todo: when shift clicking, you can put more than one item into an output slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
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

    public static class JuicerIngredientSlot extends Slot {
        public JuicerIngredientSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return JuicerRecipes.isIngredient(stack.getItem());
        }
    }

    public static class JuicerOutputSlot extends Slot {
        public JuicerOutputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            Item item = stack.getItem();
            return this.getStack().isEmpty() && (item instanceof Juice || item.equals(Items.GLASS_BOTTLE) || JuicerRecipes.isOutput(item));
        }
    }
}
