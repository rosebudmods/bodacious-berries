package io.ix0rai.bodaciousberries.block.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public record JuicerRecipe(Identifier id, Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, Ingredient receptacle, ItemStack result) implements Recipe<ImplementedInventory> {
    public Ingredient getIngredient0() {
        return this.ingredient0;
    }

    public Ingredient getIngredient1() {
        return this.ingredient1;
    }

    public Ingredient getIngredient2() {
        return this.ingredient2;
    }

    public Ingredient getReceptacle() {
        return this.receptacle;
    }

    public boolean isIngredient(ItemStack stack) {
        return ingredient0.test(stack) || ingredient1.test(stack) || ingredient2.test(stack);
    }

    public boolean isResult(ItemStack stack) {
        return result.getItem().equals(stack.getItem());
    }

    public boolean isReceptacle(ItemStack stack) {
        return receptacle.test(stack);
    }

    @Override
    public boolean matches(ImplementedInventory inv, World world) {
        if (inv.size() < 5) return false;
        return ingredient0.test(inv.getStack(3)) && ingredient1.test(inv.getStack(4)) && ingredient2.test(inv.getStack(5))
                && (receptacle.test(inv.getStack(0)) || receptacle.test(inv.getStack(1)) || receptacle.test(inv.getStack(2)));
    }

    @Override
    public ItemStack craft(ImplementedInventory inventory) {
        return getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return JuicerRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<JuicerRecipe> {
        private Type() {

        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "juicer_recipe";
    }

    private static class JuicerRecipeJsonFormat {
        public JsonObject ingredient0;
        public JsonObject ingredient1;
        public JsonObject ingredient2;
        public JsonObject receptacle;
        public String result;
    }

    public static class JuicerRecipeSerializer implements RecipeSerializer<JuicerRecipe> {
        private JuicerRecipeSerializer() {

        }

        public static final JuicerRecipeSerializer INSTANCE = new JuicerRecipeSerializer();

        public static final Identifier ID = Bodaciousberries.getIdentifier(Type.ID);

        @Override
        public JuicerRecipe read(Identifier id, JsonObject json) {
            JuicerRecipeJsonFormat recipeJson = new Gson().fromJson(json, JuicerRecipeJsonFormat.class);

            if (recipeJson.ingredient0 == null || recipeJson.ingredient1 == null || recipeJson.ingredient2 == null || recipeJson.result == null) {
                throw new JsonSyntaxException("a required attribute is missing!");
            }

            Ingredient input0 = Ingredient.fromJson(recipeJson.ingredient0);
            Ingredient input1 = Ingredient.fromJson(recipeJson.ingredient1);
            Ingredient input2 = Ingredient.fromJson(recipeJson.ingredient2);
            Ingredient receptacle = Ingredient.fromJson(recipeJson.receptacle);
            Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                    .orElseThrow(() -> new JsonSyntaxException("no such item: " + recipeJson.result));
            ItemStack output = new ItemStack(outputItem);

            return new JuicerRecipe(id, input0, input1, input2, receptacle, output);
        }

        public JuicerRecipe read(JsonObject json) {
            return read(Bodaciousberries.getIdentifier(json.get("result").getAsString().split(":")[1]), json);
        }

        @Override
        public void write(PacketByteBuf packetData, JuicerRecipe recipe) {
            recipe.getIngredient0().write(packetData);
            recipe.getIngredient1().write(packetData);
            recipe.getIngredient2().write(packetData);
            recipe.getReceptacle().write(packetData);
            packetData.writeItemStack(recipe.getOutput());
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
