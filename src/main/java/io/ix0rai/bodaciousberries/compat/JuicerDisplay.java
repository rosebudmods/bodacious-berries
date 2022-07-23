package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.BodaciousBerries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;
import java.util.List;

public class JuicerDisplay extends BasicDisplay {
    public static final CategoryIdentifier<JuicerDisplay> IDENTIFIER = CategoryIdentifier.of(BodaciousBerries.id("plugins/juicer"));
    private final EntryIngredient receptacleEntry;

    public JuicerDisplay(JuicerRecipe recipe) {
        super(EntryIngredients.ofIngredients(List.of(recipe.ingredient0(), recipe.ingredient1(), recipe.ingredient2())), Collections.singletonList(EntryIngredients.of(recipe.getOutput())));
        this.receptacleEntry = EntryIngredients.ofIngredient(recipe.receptacle());
    }
   public EntryIngredient getReceptacleEntry() {
        return this.receptacleEntry;
   }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return IDENTIFIER;
    }
}
