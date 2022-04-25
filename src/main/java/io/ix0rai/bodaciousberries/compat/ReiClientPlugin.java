package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;

public class ReiClientPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(JuicerRecipe.class, JuicerDisplay::new);
    }


    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new JuicerDisplayCategory());
    }
}
