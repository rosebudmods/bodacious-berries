package io.ix0rai.bodaciousberries.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import net.minecraft.client.gui.DrawableHelper;
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
        this.inputs = list;
        this.output = EmiStack.of(recipe.getOutput());
        this.receptacle = EmiIngredient.of(recipe.receptacle());
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
        // input and output slots
        widgets.addSlot(inputs.get(0), 4, 3);
        widgets.addSlot(inputs.get(1), 24, 10);
        widgets.addSlot(inputs.get(2), 44, 3);
        widgets.addSlot(output, 1, 36).recipeContext(this);
        widgets.addSlot(output, 24, 43).recipeContext(this);
        widgets.addSlot(output, 47, 36).recipeContext(this);

        // slot showing required receptacle
        widgets.addSlot(receptacle, 87, 22);

        // juicer background and animated textures
        widgets.addDrawable(-19, 3, 64, 59, (matrices, mouseX, mouseY, delta) -> {
            // add juicer background
            RenderSystem.setShaderTexture(0, JuicerEmiPlugin.JUICER_TEXTURE);
            DrawableHelper.drawTexture(matrices, 20, 0, 55, 17, 64, 59, 256, 256);

            // add progress bar
            final double time = System.currentTimeMillis() / 250d;

            int progress = MathHelper.floor(time % 16);
            DrawableHelper.drawTexture(matrices, 38, 24, 187, 0, 28, progress, 256, 256);

            // add animated bubbles
            int height = MathHelper.floor(time % 9);
            DrawableHelper.drawTexture(matrices, 24, 24 + height, 176, height, 11, 9 - height, 256, 256);
            DrawableHelper.drawTexture(matrices, 70, 24 + height, 176, height, 11, 9 - height, 256, 256);
        });
    }
}
