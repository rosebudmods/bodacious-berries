package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.List;

public class MoreBerries {
    //reference for ids: https://github.com/MarcusElg/More-Berries/blob/master/src/main/java/moreberries/MoreBerries.java
    public static void init() {
        String modId = "moreberries";

        if (FabricLoaderImpl.INSTANCE.isModLoaded(modId)) {
            List<String> berryIds = List.of("purple", "yellow", "green", "black", "orange", "blue");

            registerRecipes(berryIds, modId);

            RegistryEntryAddedCallback.event(Registry.ITEM).register((i, id, item) -> {
                if (id.getNamespace().equals(modId)) {
                    registerRecipes(berryIds, modId);
                }
            });
        }
    }

    private static void registerRecipes(List<String> berryIds, String modId) {
        for (String colour : berryIds) {
            RegistryKey<Item> key = RegistryKey.of(Registry.ITEM_KEY, new Identifier(modId, colour + "_berries"));
            RegistryKey<Item> juiceKey = RegistryKey.of(Registry.ITEM_KEY, new Identifier(modId, colour + "_berry_juice"));
            if (Registry.ITEM.contains(key) && Registry.ITEM.contains(juiceKey)) {
                JuicerRecipes.addRecipe(Registry.ITEM.get(key), Registry.ITEM.get(juiceKey));
            }
        }
    }
}
