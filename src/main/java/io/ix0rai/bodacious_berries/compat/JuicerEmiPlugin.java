package io.ix0rai.bodacious_berries.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.entity.JuicerRecipe;
import io.ix0rai.bodacious_berries.registry.BodaciousBlocks;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class JuicerEmiPlugin implements EmiPlugin {
    public static final Identifier JUICER_TEXTURE = BodaciousBerries.id("textures/gui/juicer_emi.png");
    public static final EmiStack JUICER = EmiStack.of(BodaciousBlocks.JUICER_BLOCK);
    public static final EmiRecipeCategory JUICER_RECIPE_CATEGORY
            = new EmiRecipeCategory(BodaciousBlocks.JUICER, EmiStack.of(BodaciousBlocks.JUICER_BLOCK));

    @Override
    public void register(EmiRegistry registry) {
        // register recipe category with emi
        registry.addCategory(JUICER_RECIPE_CATEGORY);
        registry.addWorkstation(JUICER_RECIPE_CATEGORY, JUICER);

        // add all juicer recipes to emi's recipe manager
        RecipeManager manager = registry.getRecipeManager();
        for (JuicerRecipe recipe : manager.listAllOfType(JuicerRecipe.type)) {
            registry.addRecipe(new JuicerEmiRecipe(recipe));
        }
    }
}
