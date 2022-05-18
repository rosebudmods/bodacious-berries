package io.ix0rai.bodaciousberries;

import io.ix0rai.bodaciousberries.compat.Compat;
import io.ix0rai.bodaciousberries.config.BodaciousConfig;
import io.ix0rai.bodaciousberries.registry.Berries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import io.ix0rai.bodaciousberries.registry.Bushes;
import io.ix0rai.bodaciousberries.registry.Juices;
import io.ix0rai.bodaciousberries.registry.Sounds;
import io.ix0rai.bodaciousberries.worldgen.BerryBushPatchGen;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bodaciousberries implements ModInitializer {
    public static final String MOD_ID = "bodaciousberries";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(idString(path));
    }

    public static String idString(String path) {
        return MOD_ID + ":" + path;
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(BodaciousConfig.class, Toml4jConfigSerializer::new);

        Bushes.registerBushes();
        Berries.registerBerries();
        BodaciousBlocks.registerBlocks();
        Juices.registerJuice();
        Sounds.registerSounds();
        BerryBushPatchGen.register();
        Compat.registerCompat();
    }
}
