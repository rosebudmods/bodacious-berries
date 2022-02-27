package io.ix0rai.bodaciousberries;

import io.ix0rai.bodaciousberries.registry.Berries;
import io.ix0rai.bodaciousberries.registry.BodaciousThings;
import io.ix0rai.bodaciousberries.registry.Bushes;
import io.ix0rai.bodaciousberries.registry.particles.Particles;
import io.ix0rai.bodaciousberries.registry.Sounds;
import io.ix0rai.bodaciousberries.worldgen.BerryBushPatchGen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Bodaciousberries implements ModInitializer {
    public static final String MOD_ID = "bodaciousberries";

    public static Identifier getIdentifier(String path) {
        return new Identifier(MOD_ID + ":" + path);
    }

    @Override
    public void onInitialize() {
        Bushes.registerBushes();
        Berries.registerBerries();
        BodaciousThings.registerThings();
        Sounds.registerSounds();
        BerryBushPatchGen.registerFeatures();
        Particles.registerParticles();
    }
}
