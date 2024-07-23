package io.ix0rai.bodacious_berries.block.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInput;

public record JuicerRecipeInput(ItemStack ingredient0, ItemStack ingredient1, ItemStack ingredient2, ItemStack receptacle0, ItemStack receptacle1, ItemStack receptacle2) implements RecipeInput {
	@Override
	public ItemStack get(int i) {
		return switch (i) {
			case 0 -> ingredient0;
			case 1 -> ingredient1;
			case 2 -> ingredient2;
			case 3 -> receptacle0;
			case 4 -> receptacle1;
			case 5 -> receptacle2;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int getSize() {
		return 4;
	}

	public JuicerRecipeInput(JuicerBlockEntity juicer) {
		this(juicer.getStack(3), juicer.getStack(4), juicer.getStack(5), juicer.getStack(0), juicer.getStack(1), juicer.getStack(2));
	}
}
