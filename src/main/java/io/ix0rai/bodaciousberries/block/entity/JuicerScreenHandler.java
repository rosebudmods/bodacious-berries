package io.ix0rai.bodaciousberries.block.entity;

import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class JuicerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
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
        super(BodaciousBlocks.JUICER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);

        //output slots
        this.addSlot(new JuicerOutputSlot(inventory, 0, 56, 51));
        this.addSlot(new JuicerOutputSlot(inventory, 1, 79, 58));
        this.addSlot(new JuicerOutputSlot(inventory, 2, 102, 51));
        //ingredient slots
        this.addSlot(new JuicerIngredientSlot(inventory, 3, 59, 10));
        this.addSlot(new JuicerIngredientSlot(inventory, 4, 79, 18));
        this.addSlot(new JuicerIngredientSlot(inventory, 5, 99, 10));

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
        return this.propertyDelegate.get(0);
    }

    public boolean brewingBlend() {
        return this.propertyDelegate.get(2) == 1;
    }

    public boolean brewingDubiously() {
        return this.propertyDelegate.get(1) == 1;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        final ItemStack empty = ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasStack()) {
            ItemStack slotItems = slot.getStack();
            stack = slotItems.copy();
            int finalJuicerSlot = 5;
            int finalInventorySlot = 41;
            int firstInventorySlot = 32;

            if (index > finalJuicerSlot) {
                //ingredient item
                if (JuicerRecipes.isIngredient(stack)) {
                    //attempt to insert into any ingredient slot
                    if (!this.insertItem(slotItems, 3, finalJuicerSlot + 1, false)) {
                        return empty;
                    }
                //output item
                } else if (JuicerOutputSlot.matches(stack)) {
                    //iterate over output slots
                    for (int i = 0; i < 3; i++) {
                        //check if tested output slot is empty
                        if (this.slots.get(i).getStack().isEmpty()) {
                            //if so, insert a single item and decrement stack sending the item by 1
                            if (this.insertItem(new ItemStack(slotItems.getItem()), i, i + 1, false)) {
                                slotItems.decrement(1);
                            }
                            return empty;
                        }
                    }
                //code for moving items around the vanilla inventory
                } else if (index < firstInventorySlot) {
                    if (!this.insertItem(slotItems, firstInventorySlot, finalInventorySlot, false)) {
                        return empty;
                    }
                } else if (index < finalInventorySlot) {
                    if (!this.insertItem(slotItems, finalJuicerSlot, firstInventorySlot, false)) {
                        return empty;
                    }
                } else if (!this.insertItem(slotItems, finalJuicerSlot, finalInventorySlot, false)) {
                    return empty;
                }
            } else {
                if (!this.insertItem(slotItems, finalJuicerSlot, finalInventorySlot, true)) {
                    return empty;
                }

                slot.onQuickTransfer(slotItems, stack);
            }

            if (slotItems.isEmpty()) {
                slot.setStack(empty);
            }

            if (slotItems.getCount() == stack.getCount()) {
                return empty;
            }
        }

        return stack;
    }

    public static class JuicerIngredientSlot extends Slot {
        public JuicerIngredientSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return JuicerRecipes.isIngredient(stack);
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
            return JuicerRecipes.isReceptacle(stack) || JuicerRecipes.isResult(stack);
        }
    }
}
