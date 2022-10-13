package io.ix0rai.bodacious_berries.block.entity;

import io.ix0rai.bodacious_berries.registry.BodaciousBlocks;
import io.ix0rai.bodacious_berries.util.JuicerRecipeUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class JuicerScreenHandler extends BodaciousScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public JuicerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(3));
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
    public JuicerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(BodaciousBlocks.JUICER_SCREEN_HANDLER, syncId, playerInventory);
        checkSize(inventory, 6);
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);

        // output slots
        this.addSlot(new JuicerOutputSlot(inventory, 0, 56, 51));
        this.addSlot(new JuicerOutputSlot(inventory, 1, 79, 58));
        this.addSlot(new JuicerOutputSlot(inventory, 2, 102, 51));
        // ingredient slots
        this.addSlot(new JuicerIngredientSlot(inventory, 3, 59, 10));
        this.addSlot(new JuicerIngredientSlot(inventory, 4, 79, 18));
        this.addSlot(new JuicerIngredientSlot(inventory, 5, 99, 10));

        addPlayerInventory(playerInventory);
    }

    public int getBrewTime() {
        return this.propertyDelegate.get(0);
    }

    public boolean brewingBlend() {
        return this.propertyDelegate.get(2) == 1;
    }

    public boolean brewingDubiously() {
        return this.propertyDelegate.get(1) == 1;
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        ItemStack slotItems = ItemStack.EMPTY;

        if (slot.hasStack()) {
            slotItems = slot.getStack();
            ItemStack copy = slotItems.copy();

            if (attemptInsert(slotItems, index)) {
                return ItemStack.EMPTY;
            }

            if (slotItems.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            }

            if (slotItems.getCount() == copy.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return slotItems;
    }

    private boolean attemptInsert(ItemStack stack, int index) {
        if (index > 5) {
            return attemptInsertToJuicer(stack)
                    || attemptInsertToHotbar(stack, index)
                    || attemptInsertToInventory(stack);
        } else {
            return !attemptInsertToInventory(stack);
        }
    }

    private boolean attemptInsertToInventory(ItemStack stack) {
        return this.insertItem(stack, 6, 41, false);
    }

    private boolean attemptInsertToHotbar(ItemStack stack, int index) {
        return index < 32 && this.insertItem(stack, 32, 41, false)
                || index < 41 && this.insertItem(stack, 6, 32, false);
    }

    private boolean attemptInsertToJuicer(ItemStack stack) {
        return JuicerRecipeUtil.isIngredient(stack) && !this.insertItem(stack, 3, 6, false)
                || JuicerOutputSlot.matches(stack) && attemptInsertToOutputSlot(stack);
    }

    private boolean attemptInsertToOutputSlot(ItemStack stack) {
        // iterate over output slots
        for (int i = 0; i < 3; i++) {
            // check if tested output slot is empty
            if (this.slots.get(i).getStack().isEmpty()
                    && this.insertItem(new ItemStack(stack.getItem()), i, i + 1, false)) {
                // if so, insert a single item and decrement the stack sending the item by 1
                stack.decrement(1);
                return true;
            }
        }

        return false;
    }

    public static class JuicerIngredientSlot extends Slot {
        public JuicerIngredientSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return JuicerRecipeUtil.isIngredient(stack);
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
            return matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            return JuicerRecipeUtil.isReceptacle(stack) || JuicerRecipeUtil.isResult(stack);
        }
    }
}
