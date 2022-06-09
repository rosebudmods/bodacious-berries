package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Compat {
    private static final List<String> loadedCompat = new ArrayList<>();

    public static void registerCompat() {
        init();

        if (!loadedCompat.isEmpty()) {
            Bodaciousberries.LOGGER.info("compat loaded with mods: " + loadedCompat);
        }
    }
    
    public static void init() {
        String id = "moreberries";
        if (checkCompat(id)) {
            String[] berryIds = new String[]{"purple", "yellow", "green", "black", "orange", "blue"};

            for (String berryId : berryIds) {
                // add recipes for all moreberries berries -> all moreberries juice
                JuicerRecipes.addJuiceRecipe(new Identifier(id, berryId + "_berries"), new Identifier(id, berryId + "_berry_juice"));
            }
        }

        id = "improved_berries";
        if (checkCompat(id)) {
            // sweet berries and sugar cane -> improved berries sweet berry wine
            Identifier sweetBerryId = Registry.ITEM.getId(Items.SWEET_BERRIES);
            JuicerRecipes.addJuiceRecipe(sweetBerryId, sweetBerryId, Registry.ITEM.getId(Items.SUGAR_CANE), new Identifier(id, "sweet_berry_wine"));
        }

        id = "croptopia";
        if (checkCompat(id)) {
            // croptopia grapes -> bodaciousberries grape juice
            JuicerRecipes.addJuiceRecipe(new Identifier("c", "grapes"), Bodaciousberries.id("grape_juice"));
            // vanilla melon slices -> croptopia melon juice
            JuicerRecipes.addJuiceRecipe(Registry.ITEM.getId(Items.MELON_SLICE), new Identifier(id, "melon_juice"));
            // vanilla apples -> croptopia apple juice
            JuicerRecipes.addJuiceRecipe(Registry.ITEM.getId(Items.APPLE), new Identifier(id, "apple_juice"));
            // croptopia cranberries -> croptopia cranberry juice
            JuicerRecipes.addJuiceRecipe(new Identifier("c", "cranberries"), new Identifier(id, "cranberry_juice"));
            // croptopia jam
            String[] berriesWithJam = new String[]{"blueberry", "grape", "strawberry", "peach", "apricot", "blackberry", "raspberry", "elderberry", "cherry"};
            for (String berryIdString : berriesWithJam) {
                Identifier berryId = new Identifier(id, berryIdString);
                JuicerRecipes.addRecipe(new Identifier[]{berryId, berryId, Registry.ITEM.getId(Items.SUGAR)}, Registry.ITEM.getId(Items.GLASS_BOTTLE), new Identifier(id, berryIdString + "_jam"));
            }
        }

        checkCompat("roughlyenoughitems");
    }

    private static boolean checkCompat(String id) {
        if (FabricLoader.getInstance().isModLoaded(id)) {
            if (!loadedCompat.contains(id)) {
                loadedCompat.add(id);
            }
            return true;
        }

        return false;
    }
}
