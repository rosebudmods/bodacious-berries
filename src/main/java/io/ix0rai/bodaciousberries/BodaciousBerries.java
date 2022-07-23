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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BodaciousBerries implements ModInitializer {
    public static final String MOD_ID = "bodaciousberries";

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

    public static Text translatableText(String key) {
        return Text.translatable("text." + MOD_ID + "." + key);
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
        Compat.init();
    }
}
