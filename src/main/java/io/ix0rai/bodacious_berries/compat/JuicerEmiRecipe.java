package io.ix0rai.bodacious_berries.compat;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.entity.JuicerRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class JuicerEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;
    private final EmiIngredient receptacle;

    public JuicerEmiRecipe(JuicerRecipe recipe) {
        this.id = recipe.getId();
        List<EmiIngredient> list = new ArrayList<>();
        recipe.getIngredients().forEach(ingredient -> list.add(EmiIngredient.of(ingredient)));
        for (int i = 0; i < 3; i++) {
            list.add(EmiIngredient.of(recipe.receptacle()));
        }
        this.inputs = list;
        this.output = EmiStack.of(recipe.getResult(), 3);
        this.receptacle = EmiIngredient.of(recipe.receptacle(), 3);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return JuicerEmiPlugin.JUICER_RECIPE_CATEGORY;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 128;
    }

    @Override
    public int getDisplayHeight() {
        return 64;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // juicer background and animated textures
        widgets.addDrawable(-19, 3, 64, 59, (graphics, mouseX, mouseY, delta) -> {
            // add juicer background
            graphics.drawTexture(JuicerEmiPlugin.JUICER_TEXTURE, 20, 0, 55, 17, 64, 59, 256, 256);

            // add progress bar
            final double time = System.currentTimeMillis() / 250d;

            int progress = MathHelper.floor(time % 16);
            graphics.drawTexture(JuicerEmiPlugin.JUICER_TEXTURE, 38, 24, 187, 0, 28, progress, 256, 256);

            // add animated bubbles
            int height = MathHelper.floor(time % 9);
            graphics.drawTexture(JuicerEmiPlugin.JUICER_TEXTURE, 24, 24 + height, 176, height, 11, 9 - height, 256, 256);
            graphics.drawTexture(JuicerEmiPlugin.JUICER_TEXTURE, 70, 24 + height, 176, height, 11, 9 - height, 256, 256);
        });

        // input and output slots
        widgets.addSlot(inputs.get(0), 4, 3);
        widgets.addSlot(inputs.get(1), 24, 10);
        widgets.addSlot(inputs.get(2), 44, 3);
        EmiStack outputWithSingleItem = EmiStack.of(output.getItemStack(), 1);
        widgets.addSlot(outputWithSingleItem, 1, 36).recipeContext(this);
        widgets.addSlot(outputWithSingleItem, 24, 43).recipeContext(this);
        widgets.addSlot(outputWithSingleItem, 47, 36).recipeContext(this);

        // slot showing required receptacle
        widgets.addSlot(receptacle, 87, 22);

        // "receptacle" text
        widgets.addText(BodaciousBerries.translatableText("receptacle").asOrderedText(), 67, 8, 0xFFFFFF, true);
    }
}
