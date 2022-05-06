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

    public static void addRecipe(Id[] ids, Id receptacle, Identifier output) {
        JUICER_RECIPES.add(createRecipeJson(ids, receptacle, output));
    }

    public static void addJuiceRecipe(Id input0, Id input1, Id input2, Identifier output) {
        addRecipe(new Id[]{input0, input1, input2}, new Id(Registry.ITEM.getId(Juices.JUICE_RECEPTACLE), false), output);
    }

    public static void addJuiceRecipe(Identifier input, Identifier output) {
        Id id = new Id(input, false);
        addJuiceRecipe(id, id, id, output);
    }

    public static void addJuiceRecipeByTag(Identifier input, Identifier output) {
        Id id = new Id(input, true);
        addJuiceRecipe(id, id, id, output);
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
            if (function.apply(JuicerRecipe.Serializer.INSTANCE.read(recipe))) {
                return true;
            }
        }

        return false;
    }

    public static JsonObject createRecipeJson(Id[] ingredients, Id receptacle, Identifier output) {
        JsonObject json = new JsonObject();
        //add type
        //adds: "type": "bodaciousberries:juicer_recipe"
        json.addProperty("type", JuicerRecipe.Serializer.ID.toString());

        //add ingredients
        //adds: "ingredient(i)": {"item || tag": "ingredients[i]"}
        for (int i = 0; i < ingredients.length; i++) {
            json.add("ingredient" + i, ingredients[i].getAsProperty());
        }

        //add receptacle
        //adds: "receptacle": {"item": "receptacle_id"}
        json.add("receptacle", receptacle.getAsProperty());

        //add result
        //adds: "result": "output_id"
        json.addProperty("result", output.toString());

        return json;
    }

    public static JsonObject createShapelessJson(Id ingredient, Identifier output) {
        JsonObject json = new JsonObject();
        //add type
        //adds: "type": "minecraft:crafting_shapeless"
        json.addProperty("type", "minecraft:crafting_shapeless");

        //add ingredients
        //adds "ingredients": {"item": "ingredient", "item", "bodaciousberries:chorus_berry_juice}
        JsonArray ingredientArray = new JsonArray();
        ingredientArray.add(ingredient.getAsProperty());
        ingredientArray.add(new Id(Bodaciousberries.id("chorus_berry_juice"), false).getAsProperty());
        json.add("ingredients", ingredientArray);

        //add result
        //adds: "result": {"item": "output", "count": 1}
        JsonObject result = new Id(output, false).getAsProperty();
        result.addProperty("count", 1);
        json.add("result", result);

        return json;
    }

    public record Id(Identifier id, boolean tag) {
        public Id(Identifier id, boolean tag) {
            this.tag = tag;

            if (tag) {
                String string = id.toString();
                if (string.endsWith("y")) {
                    string = string.substring(0, string.length() - 1) + "ies";
                }

                string = "c:" + string.split(":")[1];
                this.id = new Identifier(string);
            } else {
                this.id = id;
            }
        }

        public JsonObject getAsProperty() {
            JsonObject property = new JsonObject();
            //pluralizes the string if it is a tag
            property.addProperty(tag() ? "tag" : "item", id().toString());
            return property;
        }
    }
}
