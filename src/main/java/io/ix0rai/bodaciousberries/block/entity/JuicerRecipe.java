package io.ix0rai.bodaciousberries.block.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.inventory.CraftingInventory;
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

public class JuicerRecipe implements Recipe<CraftingInventory> {
    private final Ingredient ingredient1;
    private final Ingredient ingredient2;
    private final Ingredient ingredient3;
    private final ItemStack result;
    private final Identifier id;

    public JuicerRecipe(Identifier id, Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, ItemStack result) {
        this.id = id;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.result = result;
    }

    public Ingredient getIngredient1() {
        return this.ingredient1;
    }

    public Ingredient getIngredient2() {
        return this.ingredient2;
    }

    public Ingredient getIngredient3() {
        return this.ingredient3;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        if(inv.size() < 3) return false;
        return ingredient1.test(inv.getStack(0)) && ingredient2.test(inv.getStack(1)) && ingredient3.test(inv.getStack(2));
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
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

    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return JuicerRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<JuicerRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "juicer_recipe";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    static class JuicerRecipeJsonFormat {
        JsonObject ingredient1;
        JsonObject ingredient2;
        JsonObject ingredient3;
        String result;
    }

    public static class JuicerRecipeSerializer implements RecipeSerializer<JuicerRecipe> {
        private JuicerRecipeSerializer() {

        }

        public static final JuicerRecipeSerializer INSTANCE = new JuicerRecipeSerializer();

        public static final Identifier ID = Bodaciousberries.getIdentifier("juicer_recipe");

        @Override
        public JuicerRecipe read(Identifier id, JsonObject json) {
            JuicerRecipeJsonFormat recipeJson = new Gson().fromJson(json, JuicerRecipeJsonFormat.class);

            if (recipeJson.ingredient1 == null || recipeJson.ingredient2 == null || recipeJson.ingredient3 == null || recipeJson.result == null) {
                throw new JsonSyntaxException("a required attribute is missing!");
            }

            Ingredient input1 = Ingredient.fromJson(recipeJson.ingredient1);
            Ingredient input2 = Ingredient.fromJson(recipeJson.ingredient2);
            Ingredient input3 = Ingredient.fromJson(recipeJson.ingredient3);
            Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                    .orElseThrow(() -> new JsonSyntaxException("no such item: " + recipeJson.result));
            ItemStack output = new ItemStack(outputItem);

            return new JuicerRecipe(id, input1, input2, input3, output);
        }

        @Override
        public void write(PacketByteBuf packetData, JuicerRecipe recipe) {
            recipe.getIngredient1().write(packetData);
            recipe.getIngredient2().write(packetData);
            recipe.getIngredient3().write(packetData);
            packetData.writeItemStack(recipe.getOutput());
        }

        @Override
        public JuicerRecipe read(Identifier id, PacketByteBuf packetData) {
            Ingredient input1 = Ingredient.fromPacket(packetData);
            Ingredient input2 = Ingredient.fromPacket(packetData);
            Ingredient input3 = Ingredient.fromPacket(packetData);
            ItemStack output = packetData.readItemStack();
            return new JuicerRecipe(id, input1, input2, input3, output);
        }
    }
}
