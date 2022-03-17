package io.ix0rai.bodaciousberries.block.entity;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class JuicerRecipes {
    public static final List<JsonObject> JUICER_RECIPES = new ArrayList<>();
    private static final List<Recipe<Item>> JUICER_RECIPES_RAW = new ArrayList<>();

    public static void addRecipe(Identifier input1, Identifier input2, Identifier input3, Identifier output) {
        Item ingredient1 = Registry.ITEM.get(input1);
        Item ingredient2 = Registry.ITEM.get(input2);
        Item ingredient3 = Registry.ITEM.get(input3);
        Item result = Registry.ITEM.get(output);

        JUICER_RECIPES_RAW.add(new Recipe<>(ingredient1, ingredient2, ingredient3, result));
        JUICER_RECIPES.add(createRecipeJson(List.of(input1, input2, input3), output));
    }

    public static void addRecipe(Identifier input, Identifier output) {
        //for recipes that only use one type of berry
        addRecipe(input, input, input, output);
    }

    public static boolean isIngredient(ItemStack stack) {
        if (stack != null) {
            final Item item = stack.getItem();

            for (Recipe<Item> recipe : JUICER_RECIPES_RAW) {
                if (recipe.isIngredient(item)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isOutput(ItemStack stack) {
        if (stack != null) {
            final Item item = stack.getItem();

            for (Recipe<Item> recipe : JUICER_RECIPES_RAW) {
                if (recipe.isOutput(item)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static JsonObject createRecipeJson(List<Identifier> ingredients, Identifier output) {
        JsonObject json = new JsonObject();
        //add type
        //adds: "type": "juicer_recipe"
        json.addProperty("type", JuicerRecipe.JuicerRecipeSerializer.ID.toString());

        //add ingredients
        //adds: "ingredient(i)": {"item": "ingredients[i]"}
        for (int i = 0; i < ingredients.size(); i++) {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", ingredients.get(i).toString());
            json.add("ingredient" + i, ingredient);
        }

        //add result
        //adds: "result": "output"
        json.addProperty("result", output.toString());

        return json;
    }

    record Recipe<T>(Item input1, Item input2, Item input3, T output) {
        public boolean isIngredient(Item item) {
            return input1.equals(item) || input2.equals(item) || input3.equals(item);
        }

        public boolean isOutput(Item item) {
            return output.equals(item);
        }
    }
}
