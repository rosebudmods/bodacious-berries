package io.ix0rai.bodaciousberries.block.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class JuicerRecipes {
    public static final List<JsonObject> JUICER_RECIPES = new ArrayList<>();

    public static void addRecipe(Identifier input1, Identifier input2, Identifier input3, Identifier output) {
        JUICER_RECIPES.add(createRecipeJson(List.of(input1, input2, input3), output));
    }

    public static void addRecipe(Item input1, Item input2, Item input3, Item output) {
        Identifier id1 = input1.getRegistryEntry().registryKey().getValue();
        Identifier id2 = input2.getRegistryEntry().registryKey().getValue();
        Identifier id3 = input3.getRegistryEntry().registryKey().getValue();
        Identifier id4 = output.getRegistryEntry().registryKey().getValue();

        addRecipe(id1, id2, id3, id4);
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

    public static JsonObject createShapelessJson(Identifier ingredient, Identifier output) {
        JsonObject json = new JsonObject();

        json.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredientArray = new JsonArray();

        JsonObject ingredientObject = new JsonObject();
        ingredientObject.addProperty("item", ingredient.toString());
        ingredientArray.add(ingredientObject);

        JsonObject chorusBerryJuice = new JsonObject();
        chorusBerryJuice.addProperty("item", "bodaciousberries:chorus_berry_juice");
        ingredientArray.add(chorusBerryJuice);

        json.add("ingredients", ingredientArray);

        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", 1);

        json.add("result", result);

        return json;
    }
}
