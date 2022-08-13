package io.ix0rai.bodacious_berries.compat;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.util.JuicerRecipeUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Compat {
    public static void register() {
        String id = "moreberries";
        if (FabricLoader.getInstance().isModLoaded(id)) {
            String[] berryIds = new String[]{"purple", "yellow", "green", "black", "orange", "blue"};

            for (String berryId : berryIds) {
                // add recipes for all moreberries berries -> all moreberries juice
                JuicerRecipeUtil.registerJuiceRecipe(new Identifier(id, berryId + "_berries"), new Identifier(id, berryId + "_berry_juice"));
            }
        }

        id = "improved_berries";
        if (FabricLoader.getInstance().isModLoaded(id)) {
            // sweet berries and sugar cane -> improved berries sweet berry wine
            Identifier sweetBerryId = Registry.ITEM.getId(Items.SWEET_BERRIES);
            JuicerRecipeUtil.registerJuiceRecipe(sweetBerryId, sweetBerryId, Registry.ITEM.getId(Items.SUGAR_CANE), new Identifier(id, "sweet_berry_wine"));
        }

        id = "croptopia";
        if (FabricLoader.getInstance().isModLoaded(id)) {
            // croptopia grapes -> bodaciousberries grape juice
            JuicerRecipeUtil.registerJuiceRecipe(new Identifier("c", "grapes"), BodaciousBerries.id("grape_juice"));
            // vanilla melon slices -> croptopia melon juice
            JuicerRecipeUtil.registerJuiceRecipe(Registry.ITEM.getId(Items.MELON_SLICE), new Identifier(id, "melon_juice"));
            // vanilla apples -> croptopia apple juice
            JuicerRecipeUtil.registerJuiceRecipe(Registry.ITEM.getId(Items.APPLE), new Identifier(id, "apple_juice"));
            // croptopia cranberries -> croptopia cranberry juice
            JuicerRecipeUtil.registerJuiceRecipe(new Identifier("c", "cranberries"), new Identifier(id, "cranberry_juice"));
            // croptopia jam
            String[] berriesWithJam = new String[]{"blueberry", "grape", "strawberry", "peach", "apricot", "blackberry", "raspberry", "elderberry", "cherry"};
            for (String berryIdString : berriesWithJam) {
                Identifier berryId = new Identifier(id, berryIdString);
                JuicerRecipeUtil.registerJuicerRecipe(new Identifier[]{berryId, berryId, Registry.ITEM.getId(Items.SUGAR)}, Registry.ITEM.getId(Items.GLASS_BOTTLE), new Identifier(id, berryIdString + "_jam"));
            }
        }
    }
}
