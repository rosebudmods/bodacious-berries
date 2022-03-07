package io.ix0rai.bodaciousberries.block.entity;

import io.ix0rai.bodaciousberries.registry.items.Juice;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JuicerRecipes {
    private static final List<Recipe<Juice>> JUICER_RECIPES = new ArrayList<>();

    public static void addRecipe(Item input1, Item input2, Item input3, Juice output) {
        JUICER_RECIPES.add(new Recipe<>(input1, input2, input3, output));
    }

    public static void addRecipe(Item input, Juice output) {
        //for recipes that only use one type of berry
        addRecipe(input, input, input, output);
    }

    public static boolean hasRecipeFor(Item input1, Item input2, Item input3) {
        for (Recipe<Juice> recipe : JUICER_RECIPES) {
            if (recipe.ingredientsMatch(input1, input2, input3)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isIngredient(Item item) {
        if (item != null) {
            for (Recipe<Juice> recipe : JUICER_RECIPES) {
                if (recipe.isIngredient(item)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isOutput(Item item) {
        if (item != null) {
            for (Recipe<Juice> recipe : JUICER_RECIPES) {
                if (recipe.isOutput(item)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static ItemStack craft(ItemStack ingredient1, ItemStack ingredient2, ItemStack ingredient3, ItemStack input) {
        //ensure all ingredient are not empty
        if (!anyEmpty(ingredient1, ingredient2, ingredient3)) {
            int i = 0;
            //check all recipes against ingredients
            for(int j = JUICER_RECIPES.size(); i < j; ++i) {
                Recipe<Juice> recipe = JUICER_RECIPES.get(i);
                if (recipe.ingredientsMatch(ingredient1, ingredient2, ingredient3)) {
                    //if we have a recipe with the correct ingredients, return the output
                    return new ItemStack(recipe.output);
                }
            }
        }

        return input;
    }

    private static boolean anyEmpty(ItemStack stack1, ItemStack stack2, ItemStack stack3) {
        return stack1.isEmpty() || stack2.isEmpty() || stack3.isEmpty();
    }

    record Recipe<T>(Item input1, Item input2, Item input3, T output) {
        public boolean isIngredient(Item item) {
            return input1.equals(item) || input2.equals(item) || input3.equals(item);
        }

        public boolean isOutput(Item item) {
            return output.equals(item);
        }

        public boolean ingredientsMatch(ItemStack ingredient1, ItemStack ingredient2, ItemStack ingredient3) {
            return ingredientsMatch(ingredient1.getItem(), ingredient2.getItem(), ingredient3.getItem());
        }

        public boolean ingredientsMatch(Item ingredient1, Item ingredient2, Item ingredient3) {
            return input1.equals(ingredient1) && input2.equals(ingredient2) && input3.equals(ingredient3);
        }
    }
}
