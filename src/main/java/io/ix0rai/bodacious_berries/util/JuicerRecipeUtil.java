package io.ix0rai.bodacious_berries.util;

import com.google.gson.JsonObject;
import io.ix0rai.bodacious_berries.block.entity.JuicerRecipe;
import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JuicerRecipeUtil {
    public static final List<JsonObject> JUICER_RECIPES = new ArrayList<>();

    /**
     * registers a juicer recipe with all
     * @param ingredients the ingredients of the recipe, must have exactly three elements
     * @param receptacle the receptacle of the recipe
     * @param output the resulting juice item
     */
    public static void registerJuicerRecipe(Identifier[] ingredients, Identifier receptacle, Identifier output) {
        confirmSize(ingredients);
        JUICER_RECIPES.add(createRecipeJson(ingredients, receptacle, output));
    }

    /**
     * registers a juicer recipe with the given three ingredients, the given output as the output, and a glass bottle as the receptacle
     * @param input0 the first input berry
     * @param input1 the second input berry
     * @param input2 the third input berry
     * @param output the resulting juice
     */
    public static void registerJuiceRecipe(Identifier input0, Identifier input1, Identifier input2, Identifier output) {
        registerJuicerRecipe(new Identifier[]{input0, input1, input2}, Registries.ITEM.getId(BodaciousJuices.JUICE_RECEPTACLE), output);
    }

    /**
     * registers a juicer recipe with the given input as all ingredients, the given output as the output, and a glass bottle as the receptacle
     * @param input the input berry of the recipe
     * @param output the resulting juice
     */
    public static void registerJuiceRecipe(Identifier input, Identifier output) {
        registerJuiceRecipe(input, input, input, output);
    }

    /**
     * checks whether the given stack is an ingredient in any registered juicer recipe
     * @param stack the stack to check
     * @return true if the stack is a valid ingredient
     */
    public static boolean isIngredient(ItemStack stack) {
        return check(juicerRecipe -> juicerRecipe.isIngredient(stack));
    }

    /**
     * checks whether the given stack is the result of any registered juicer recipe
     * @param stack the stack to check
     * @return true if the stack is a valid result
     */
    public static boolean isResult(ItemStack stack) {
        return check(juicerRecipe -> juicerRecipe.isResult(stack));
    }

    /**
     * checks whether the given stack is the receptacle of any registered juicer recipe
     * @param stack the stack to check
     * @return true if the stack is a valid receptacle
     */
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
        confirmSize(ingredients);

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

    private static void confirmSize(Identifier[] ingredients) {
        if (ingredients.length != 3) {
            throw new IllegalArgumentException("ingredients must be an array of length 3");
        }
    }
}

