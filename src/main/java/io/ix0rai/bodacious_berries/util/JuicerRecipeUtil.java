package io.ix0rai.bodacious_berries.util;

import com.google.gson.JsonObject;
import io.ix0rai.bodacious_berries.block.entity.JuicerRecipe;
import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JuicerRecipeUtil {
    public static final List<JsonObject> JUICER_RECIPES = new ArrayList<>();

    public static void addRecipe(Identifier[] ids, Identifier receptacle, Identifier output) {
        JUICER_RECIPES.add(createRecipeJson(ids, receptacle, output));
    }

    public static void addJuiceRecipe(Identifier input0, Identifier input1, Identifier input2, Identifier output) {
        addRecipe(new Identifier[]{input0, input1, input2}, Registry.ITEM.getId(BodaciousJuices.JUICE_RECEPTACLE), output);
    }

    public static void addJuiceRecipe(Identifier input, Identifier output) {
        addJuiceRecipe(input, input, input, output);
    }

    public static boolean isIngredient(ItemStack stack) {
        return check(juicerRecipe -> juicerRecipe.isIngredient(stack));
    }

    public static boolean isResult(ItemStack stack) {
        return check(juicerRecipe -> juicerRecipe.isResult(stack));
    }

    public static boolean isReceptacle(ItemStack stack) {
        return check(juicerRecipe -> juicerRecipe.isReceptacle(stack));
    }

    private static boolean check(Function<JuicerRecipe, Boolean> function) {
        for (JsonObject recipe : JUICER_RECIPES) {
            if (function.apply(JuicerRecipe.SERIALIZER.read(recipe))) {
                return true;
            }
        }

        return false;
    }

    /**
     * creates a recipe json object using the default three-ingredient format
     * <br>documentation on what the json will look like can be found <a href="https://github.com/ix0rai/bodacious_berries/wiki/Custom-Juicing-Recipes">here</a>
     * @param ingredients an array of three ingredients to use in the recipe
     * @param receptacle the receptacle for the juice
     * @param output the output item of the recipe
     * @return a json object representing the recipe
     */
    public static JsonObject createRecipeJson(Identifier[] ingredients, Identifier receptacle, Identifier output) {
        if (ingredients.length != 3) {
            throw new IllegalArgumentException("ingredients must be an array of length 3");
        }

        JsonObject json = new JsonObject();
        json.addProperty("type", JuicerRecipe.RECIPE_ID);
        JsonObject jsonIngredients = new JsonObject();
        for (int i = 0; i < ingredients.length; i ++) {
            jsonIngredients.add(i + "", getAsProperty(ingredients[i]));
        }
        jsonIngredients.add("receptacle", getAsProperty(receptacle));
        json.add("ingredients", jsonIngredients);
        json.addProperty("result", output.toString());

        return json;
    }

    private static JsonObject getAsProperty(Identifier id) {
        JsonObject property = new JsonObject();
        // if the namespace is c: we can assume it's a tag
        if (id.getNamespace().equals("c")) {
            property.addProperty("tag", id.toString());
        } else {
            property.addProperty("item", id.toString());
        }

        return property;
    }
}

