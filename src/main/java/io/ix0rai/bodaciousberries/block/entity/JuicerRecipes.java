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

    public static void addRecipe(Identifier input0, Identifier input1, Identifier input2, Identifier receptacle, Identifier output) {
        JUICER_RECIPES.add(createRecipeJson(List.of(input0, input1, input2), receptacle, output));
    }

    public static void addJuiceRecipe(Identifier input0, Identifier input1, Identifier input2, Identifier output) {
        addRecipe(input0, input1, input2, new Identifier("minecraft:glass_bottle"), output);
    }

    public static void addJuiceRecipe(Identifier input, Identifier output) {
        addJuiceRecipe(input, input, input, output);
    }

    public static void addJuiceRecipe(Item input0, Item input1, Item input2, Item output) {
        Identifier id1 = input0.getRegistryEntry().registryKey().getValue();
        Identifier id2 = input1.getRegistryEntry().registryKey().getValue();
        Identifier id3 = input2.getRegistryEntry().registryKey().getValue();
        Identifier id4 = output.getRegistryEntry().registryKey().getValue();

        addJuiceRecipe(id1, id2, id3, id4);
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

    public static boolean isReceptacle(ItemStack stack) {
        if (stack != null) {
            for (JsonObject recipe : JUICER_RECIPES) {
                if (JuicerRecipe.JuicerRecipeSerializer.INSTANCE.read(recipe).isReceptacle(stack)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static JsonObject createRecipeJson(List<Identifier> ingredients, Identifier receptacle, Identifier output) {
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

        JsonObject receptacleJson = new JsonObject();
        receptacleJson.addProperty("item", receptacle.toString());
        json.add("receptacle", receptacleJson);

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
