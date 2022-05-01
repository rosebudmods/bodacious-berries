package io.ix0rai.bodaciousberries.block.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.registry.Juices;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JuicerRecipes {
    public static final List<JsonObject> JUICER_RECIPES = new ArrayList<>();

    public static void addRecipe(Identifier[] ids, Identifier receptacle, Identifier output) {
        JUICER_RECIPES.add(createRecipeJson(ids, receptacle, output));
    }

    public static void addJuiceRecipe(Identifier input0, Identifier input1, Identifier input2, Identifier output) {
        addRecipe(new Identifier[]{input0, input1, input2}, Registry.ITEM.getId(Juices.JUICE_RECEPTACLE), output);
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

    public static boolean check(Function<JuicerRecipe, Boolean> function) {
        for (JsonObject recipe : JUICER_RECIPES) {
            if (function.apply(JuicerRecipe.Serializer.INSTANCE.read(recipe))) {
                return true;
            }
        }

        return false;
    }

    public static JsonObject createRecipeJson(Identifier[] ingredients, Identifier receptacle, Identifier output) {
        JsonObject json = new JsonObject();
        //add type
        //adds: "type": "bodaciousberries:juicer_recipe"
        json.addProperty("type", JuicerRecipe.Serializer.ID.toString());

        //add ingredients
        //adds: "ingredient(i)": {"item": "ingredients[i]"}
        for (int i = 0; i < ingredients.length; i++) {
            json.add("ingredient" + i, getItemProperty(ingredients[i].toString()));
        }

        json.add("receptacle", getItemProperty(receptacle.toString()));

        //add result
        //adds: "result": "output"
        json.addProperty("result", output.toString());

        return json;
    }

    public static JsonObject createShapelessJson(Identifier ingredient, Identifier output) {
        JsonObject json = new JsonObject();
        //add type
        //adds: "type": "minecraft:crafting_shapeless"
        json.addProperty("type", "minecraft:crafting_shapeless");

        //add ingredients
        //adds "ingredients": {"item": "ingredient", "item", "bodaciousberries:chorus_berry_juice}
        JsonArray ingredientArray = new JsonArray();
        ingredientArray.add(getItemProperty(ingredient.toString()));
        ingredientArray.add(getItemProperty(Bodaciousberries.idString("chorus_berry_juice")));
        json.add("ingredients", ingredientArray);

        //add result
        //adds: "result": {"item": "output", "count": 1}
        JsonObject result = getItemProperty(output.toString());
        result.addProperty("count", 1);
        json.add("result", result);

        return json;
    }

    private static JsonObject getItemProperty(String string) {
        JsonObject property = new JsonObject();
        property.addProperty("item", string);
        return property;
    }
}
