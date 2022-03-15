package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;

public class MoreBerries {
    //reference for ids: https://github.com/MarcusElg/More-Berries/blob/master/src/main/java/moreberries/MoreBerries.java
    public static void init() {
        String modId = "moreberries";

        if (FabricLoaderImpl.INSTANCE.isModLoaded(modId)) {
            HashMap<String, Boolean> berryIds = new HashMap<>(Map.of("purple", false, "yellow", false, "green", false, "black", false, "orange", false, "blue", false));

            registerRecipes(berryIds, modId);

            RegistryEntryAddedCallback.event(Registry.ITEM).register((i, id, item) -> {
                if (id.getNamespace().equals(modId)) {
                    registerRecipes(berryIds, modId);
                }
            });
        }
    }

    private static void registerRecipes(HashMap<String, Boolean> berryIds, String modId) {
        for (Map.Entry<String, Boolean> entry : berryIds.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                RegistryKey<Item> itemKey = RegistryKey.of(Registry.ITEM_KEY, new Identifier(modId, entry.getKey() + "_berries"));
                RegistryKey<Item> juiceKey = RegistryKey.of(Registry.ITEM_KEY, new Identifier(modId, entry.getKey() + "_berry_juice"));

                if (Registry.ITEM.contains(itemKey) && Registry.ITEM.contains(juiceKey)) {
                    JuicerRecipes.addRecipe(Registry.ITEM.get(itemKey), Registry.ITEM.get(juiceKey));
                    berryIds.put(entry.getKey(), true);
                }
            }
        }
    }
}
