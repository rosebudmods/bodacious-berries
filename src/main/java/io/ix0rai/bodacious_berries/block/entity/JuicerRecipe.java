package io.ix0rai.bodacious_berries.block.entity;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeHolder;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record JuicerRecipe(Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, Ingredient receptacle, ItemStack result) implements Recipe<Inventory> {
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
        return result.getItem().equals(stack.getItem());
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
    public boolean matches(Inventory inv, World world) {
        if (inv.size() < 5) return false;
        return ingredient0.test(inv.getStack(3)) && ingredient1.test(inv.getStack(4)) && ingredient2.test(inv.getStack(5))
                && (receptacle.test(inv.getStack(0)) || receptacle.test(inv.getStack(1)) || receptacle.test(inv.getStack(2)));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return getResult(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.result;
    }

    public ItemStack getResult() {
        return this.result;
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
            RECIPES.addAll(server.getRecipeManager().listAllOfType(type).stream().map(RecipeHolder::value).toList());
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

    /**
     * Utility for parsing recipes via codec.
     */
    public record IngredientSet(Ingredient ingredient0, Ingredient ingredient1, Ingredient ingredient2, Ingredient receptacle) {
        private static final Codec<IngredientSet> ALL_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Ingredient.field_46095.fieldOf("all").forGetter((set) -> set.ingredient0),
                Ingredient.field_46095.fieldOf("receptacle").forGetter((set) -> set.receptacle)
        ).apply(instance, (ingredient, receptacle) -> new IngredientSet(ingredient, ingredient, ingredient, receptacle)));

        private static final Codec<IngredientSet> INDIVIDUAL_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Ingredient.field_46095.fieldOf("0").forGetter((set) -> set.ingredient0),
                Ingredient.field_46095.fieldOf("1").forGetter((set) -> set.ingredient1),
                Ingredient.field_46095.fieldOf("2").forGetter((set) -> set.ingredient2),
                Ingredient.field_46095.fieldOf("receptacle").forGetter((set) -> set.receptacle)
        ).apply(instance, IngredientSet::new));

        private static final Codec<Either<IngredientSet, IngredientSet>> EITHER_CODEC = Codec.either(
                ALL_CODEC,
                INDIVIDUAL_CODEC
        );

        private static final Codec<IngredientSet> CODEC = EITHER_CODEC
                .flatXmap(
                        either -> either.map(DataResult::success, DataResult::success),
                        set -> DataResult.success(
                                set.ingredientsMatch() ?
                                        Either.left(set) :
                                        Either.right(set)
                        )
                );

        private boolean ingredientsMatch() {
            return this.ingredient0.equals(this.ingredient1) && this.ingredient0.equals(ingredient2);
        }
    }

    public static class Serializer implements RecipeSerializer<JuicerRecipe> {
        private static final Codec<JuicerRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                IngredientSet.CODEC.fieldOf("ingredients").forGetter((recipe) -> new IngredientSet(recipe.ingredient0, recipe.ingredient1, recipe.ingredient2, recipe.receptacle)),
                Registries.ITEM.getCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter((recipe) -> recipe.result)
        ).apply(instance, (set, result) -> new JuicerRecipe(set.ingredient0(), set.ingredient1(), set.ingredient2(), set.receptacle(), result)));

        @Override
        public JuicerRecipe read(PacketByteBuf buf) {
            Ingredient input1 = Ingredient.fromPacket(buf);
            Ingredient input2 = Ingredient.fromPacket(buf);
            Ingredient input3 = Ingredient.fromPacket(buf);
            Ingredient receptacle = Ingredient.fromPacket(buf);
            return new JuicerRecipe(input1, input2, input3, receptacle, buf.readItemStack());
        }

        @Override
        public Codec<JuicerRecipe> method_53736() {
            return CODEC;
        }

        @Override
        public void write(PacketByteBuf packetData, JuicerRecipe recipe) {
            recipe.ingredient0().write(packetData);
            recipe.ingredient1().write(packetData);
            recipe.ingredient2().write(packetData);
            recipe.receptacle().write(packetData);
            packetData.writeItemStack(recipe.getResult());
        }
    }
}
