package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.Identifier;

import java.util.List;

public class MoreBerries {
    //reference for ids: https://github.com/MarcusElg/More-Berries/blob/master/src/main/java/moreberries/MoreBerries.java
    public static void init() {
        String modId = "moreberries";

        if (FabricLoaderImpl.INSTANCE.isModLoaded(modId)) {
            List<String> berryIds = List.of("purple", "yellow", "green", "black", "orange", "blue");

            for (String berryId : berryIds) {
                JuicerRecipes.addJuiceRecipe(new Identifier(modId, berryId + "_berries"), new Identifier(modId, berryId + "_berry_juice"));
            }
        }
    }
}
