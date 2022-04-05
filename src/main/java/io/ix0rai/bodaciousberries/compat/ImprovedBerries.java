package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ImprovedBerries {
    public static String init() {
        String id = "improved_berries";

        if (FabricLoaderImpl.INSTANCE.isModLoaded(id)) {
            JuicerRecipes.addJuiceRecipe(Registry.ITEM.getId(Items.SWEET_BERRIES), Registry.ITEM.getId(Items.SWEET_BERRIES), Registry.ITEM.getId(Items.SUGAR), new Identifier(id, "sweet_berry_wine"));
            return id + ", ";
        }

        return "";
    }
}
