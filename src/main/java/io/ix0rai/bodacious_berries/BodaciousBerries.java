package io.ix0rai.bodacious_berries;

import io.ix0rai.bodacious_berries.compat.Compat;
import io.ix0rai.bodacious_berries.config.BodaciousConfig;
import io.ix0rai.bodacious_berries.registry.*;
import io.ix0rai.bodacious_berries.worldgen.BerryBushPatchGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
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
        return new TranslatableText(translatableTextKey(key));
    }

    @Override
    public void onInitialize() {
        BodaciousBushes.register();
        BodaciousBlocks.register();
        BodaciousItems.register();
        BodaciousStatusEffects.register();
        BodaciousJuices.register();
        BodaciousSounds.register();
        BerryBushPatchGen.register();
        Compat.register();
        BodaciousDataFixers.register();

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(id("classic"), modContainer, ResourcePackActivationType.NORMAL)
        );
    }
}
