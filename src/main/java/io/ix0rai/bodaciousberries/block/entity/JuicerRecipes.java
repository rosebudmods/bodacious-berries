package io.ix0rai.bodaciousberries.block.entity;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class JuicerRecipes {
    public static final List<JsonObject> JUICER_RECIPES = new ArrayList<>();

    public static void addRecipe(Identifier input1, Identifier input2, Identifier input3, Identifier output) {
        JUICER_RECIPES.add(createRecipeJson(List.of(input1, input2, input3), output));
    }

    public static void addRecipe(Identifier input, Identifier output) {
        //for recipes that only use one type of berry
        addRecipe(input, input, input, output);
    }

    public static boolean isIngredient(ItemStack stack) {
        if (stack != null) {
            for (JsonObject recipe : JUICER_RECIPES) {
                if (JuicerRecipe.JuicerRecipeSerializer.INSTANCE.read(recipe).isIngredient(stack)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isOutput(ItemStack stack) {
        if (stack != null) {
            for (JsonObject recipe : JUICER_RECIPES) {
                if (JuicerRecipe.JuicerRecipeSerializer.INSTANCE.read(recipe).isResult(stack)) {
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
}
