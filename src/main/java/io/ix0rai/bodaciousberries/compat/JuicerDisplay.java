package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;
import java.util.List;

public class JuicerDisplay extends BasicDisplay {
    public static final CategoryIdentifier<JuicerDisplay> IDENTIFIER = CategoryIdentifier.of(Bodaciousberries.id("juicer"));

    public JuicerDisplay(JuicerRecipe recipe) {
        super(EntryIngredients.ofIngredients(List.of(recipe.getIngredient0(), recipe.getIngredient1(), recipe.getIngredient2())), Collections.singletonList(EntryIngredients.ofItemStacks(Collections.singletonList(recipe.getOutput()))));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return IDENTIFIER;
    }
}
