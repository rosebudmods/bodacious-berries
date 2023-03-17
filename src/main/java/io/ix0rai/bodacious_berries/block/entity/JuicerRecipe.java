package io.ix0rai.bodacious_berries.block.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record JuicerRecipe(Identifier id, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, Ingredient receptacle, ItemStack output) implements Recipe<ImplementedInventory> {
    public static final String RECIPE_ID = BodaciousBerries.idString("juicing");
    public static Serializer serializer;
    public static RecipeType<JuicerRecipe> type;

    public static void register() {
        // if this is not run on mod init, this file is loaded after the registry has already been frozen
        serializer = RecipeSerializer.register(RECIPE_ID, new Serializer());
        type = RecipeType.register(RECIPE_ID);
    }

    public boolean isIngredient(ItemStack stack) {
        return ingredient0.test(stack) || ingredient1.test(stack) || ingredient2.test(stack);
    }

    public boolean ingredientsMatch(ItemStack ingredient) {
        return ingredient0.test(ingredient) && ingredient1.test(ingredient) && ingredient2.test(ingredient);
    }

    public boolean isResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return output.getItem().equals(stack.getItem());
    }

    public boolean isReceptacle(ItemStack stack) {
        return receptacle.test(stack);
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(3);
        ingredients.add(ingredient0);
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        return ingredients;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean matches(ImplementedInventory inv, World world) {
        if (inv.size() < 5) return false;
        return ingredient0.test(inv.getStack(3)) && ingredient1.test(inv.getStack(4)) && ingredient2.test(inv.getStack(5))
                && (receptacle.test(inv.getStack(0)) || receptacle.test(inv.getStack(1)) || receptacle.test(inv.getStack(2)));
    }

    @Override
    public ItemStack craft(ImplementedInventory inventory, DynamicRegistryManager registryManager) {
        return getResult(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.output;
    }

    public ItemStack getResult() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }

    @SuppressWarnings("unused")
    private static class JuicerRecipeJsonFormat {
        public JsonObject ingredients;
        public String result;
    }

    public static class Util {
        private static final List<JuicerRecipe> RECIPES = new ArrayList<>();

        public static void reloadRecipes(MinecraftServer server) {
            RECIPES.clear();
            RECIPES.addAll(server.getRecipeManager().listAllOfType(type));
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

        private static boolean check(Predicate<JuicerRecipe> function) {
            for (JuicerRecipe recipe : RECIPES) {
                if (function.test(recipe)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static class Serializer implements RecipeSerializer<JuicerRecipe> {
        @Override
        public JuicerRecipe read(Identifier id, JsonObject json) {
            JuicerRecipeJsonFormat recipeJson = new Gson().fromJson(json, JuicerRecipeJsonFormat.class);

            if (recipeJson.ingredients == null || recipeJson.result == null) {
                throw new JsonSyntaxException("a required json attribute is missing (result or ingredients) in recipe " + id + "!");
            }

            Ingredient input0;
            Ingredient input1;
            Ingredient input2;
            JsonElement allIngredient = recipeJson.ingredients.get("all");
            if (allIngredient != null) {
                input0 = Ingredient.fromJson(allIngredient);
                input1 = Ingredient.fromJson(allIngredient);
                input2 = Ingredient.fromJson(allIngredient);
            } else {
                input0 = Ingredient.fromJson(recipeJson.ingredients.get("0"));
                input1 = Ingredient.fromJson(recipeJson.ingredients.get("1"));
                input2 = Ingredient.fromJson(recipeJson.ingredients.get("2"));
            }
            Ingredient receptacle = Ingredient.fromJson(recipeJson.ingredients.get("receptacle"));
            Item outputItem = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                    .orElseThrow(() -> new JsonSyntaxException("no such item: " + recipeJson.result));
            ItemStack output = new ItemStack(outputItem);

            return new JuicerRecipe(id, input0, input1, input2, receptacle, output);
        }

        @Override
        public void write(PacketByteBuf packetData, JuicerRecipe recipe) {
            recipe.ingredient0().write(packetData);
            recipe.ingredient1().write(packetData);
            recipe.ingredient2().write(packetData);
            recipe.receptacle().write(packetData);
            packetData.writeItemStack(recipe.getResult());
        }

        @Override
        public JuicerRecipe read(Identifier id, PacketByteBuf packetData) {
            Ingredient input1 = Ingredient.fromPacket(packetData);
            Ingredient input2 = Ingredient.fromPacket(packetData);
            Ingredient input3 = Ingredient.fromPacket(packetData);
            Ingredient receptacle = Ingredient.fromPacket(packetData);
            return new JuicerRecipe(id, input1, input2, input3, receptacle, packetData.readItemStack());
        }
    }
}
