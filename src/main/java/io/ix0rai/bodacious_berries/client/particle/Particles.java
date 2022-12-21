package io.ix0rai.bodacious_berries.client.particle;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Particles {
    public static final DefaultParticleType RAINBOW_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType SLICEY_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        registerParticle("rainbow_particle", RAINBOW_PARTICLE, RainbowParticleFactory::new);
        registerParticle("slicey_particle", SLICEY_PARTICLE, SliceyParticle.Factory::new);
    }

    private static void registerParticle(String name, DefaultParticleType particle, ParticleFactoryRegistry.PendingParticleFactory<DefaultParticleType> constructor) {
        Registry.register(Registries.PARTICLE_TYPE, BodaciousBerries.id(name), particle);
        ParticleFactoryRegistry.getInstance().register(particle, constructor);
    }
}
