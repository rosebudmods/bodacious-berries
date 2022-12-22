package io.ix0rai.bodacious_berries;

import io.ix0rai.bodacious_berries.block.entity.JuicerRecipe;
import io.ix0rai.bodacious_berries.config.BodaciousConfig;
import io.ix0rai.bodacious_berries.registry.BodaciousBlocks;
import io.ix0rai.bodacious_berries.registry.BodaciousItems;
import io.ix0rai.bodacious_berries.registry.BodaciousSounds;
import io.ix0rai.bodacious_berries.registry.BodaciousStatusEffects;
import io.ix0rai.bodacious_berries.worldgen.BodaciousWorldgen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BodaciousBerries implements ModInitializer {
    public static final String MOD_ID = "bodacious_berries";
    public static final BodaciousConfig CONFIG = new BodaciousConfig();

    public static Identifier id(String path) {
        if (!path.startsWith(MOD_ID + ":")) {
            return new Identifier(idString(path));
        } else {
            return new Identifier(path);
        }
    }

    public static String idString(String path) {
        return MOD_ID + ":" + path;
    }

    public static String translatableTextKey(String path) {
        return MOD_ID + "." + path;
    }

    public static MutableText translatableText(String key) {
        return Text.translatable(translatableTextKey(key));
    }

    @Override
    public void onInitialize() {
        BodaciousBlocks.register();
        BodaciousItems.register();
        BodaciousStatusEffects.register();
        BodaciousSounds.register();
        BodaciousWorldgen.register();

        // ensure juicer recipes are always up-to-date for utility methods
        JuicerRecipe.register();
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> JuicerRecipe.Util.reloadRecipes(server));
        ServerLifecycleEvents.SERVER_STARTED.register((JuicerRecipe.Util::reloadRecipes));

        // declare bodacious berries classic resource pack
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(id("classic"), modContainer, ResourcePackActivationType.NORMAL)
        );
    }
}
