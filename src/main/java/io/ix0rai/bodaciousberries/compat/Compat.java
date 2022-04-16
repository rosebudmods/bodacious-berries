package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Compat {
    public static void registerCompat() {
        final List<String> loadedCompat = init();

        if (!loadedCompat.isEmpty()) {
            Bodaciousberries.LOGGER.info("compat loaded with mods: " + loadedCompat);
        }
    }
    
    public static List<String> init() {
        final List<String> loadedCompat = new ArrayList<>();

        String id = "moreberries";
        if (FabricLoaderImpl.INSTANCE.isModLoaded(id)) {
            String[] berryIds = new String[]{"purple", "yellow", "green", "black", "orange", "blue"};

            for (String berryId : berryIds) {
                JuicerRecipes.addJuiceRecipe(new Identifier(id, berryId + "_berries"), new Identifier(id, berryId + "_berry_juice"));
            }

            loadedCompat.add(id);
        }

        id = "improved_berries";
        if (FabricLoaderImpl.INSTANCE.isModLoaded(id)) {
            JuicerRecipes.addJuiceRecipe(Registry.ITEM.getId(Items.SWEET_BERRIES), Registry.ITEM.getId(Items.SWEET_BERRIES), Registry.ITEM.getId(Items.SUGAR), new Identifier(id, "sweet_berry_wine"));
            loadedCompat.add(id);
        }

        return loadedCompat;
    }
}
