package io.ix0rai.bodaciousberries;

import io.ix0rai.bodaciousberries.compat.Compat;
import io.ix0rai.bodaciousberries.particle.Particles;
import io.ix0rai.bodaciousberries.registry.Berries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import io.ix0rai.bodaciousberries.registry.Bushes;
import io.ix0rai.bodaciousberries.registry.Juices;
import io.ix0rai.bodaciousberries.registry.Sounds;
import io.ix0rai.bodaciousberries.worldgen.BerryBushPatchGen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Bodaciousberries implements ModInitializer {
    public static final String MOD_ID = "bodaciousberries";

    public static Identifier getIdentifier(String path) {
        return new Identifier(getId(path));
    }

    public static String getId(String path) {
        return MOD_ID + ":" + path;
    }

    @Override
    public void onInitialize() {
        Bushes.registerBushes();
        Berries.registerBerries();
        BodaciousBlocks.registerBlocks();
        Juices.registerJuice();
        Sounds.registerSounds();
        BerryBushPatchGen.registerFeatures();
        Particles.registerParticles();
        Compat.registerCompat();
    }
}
