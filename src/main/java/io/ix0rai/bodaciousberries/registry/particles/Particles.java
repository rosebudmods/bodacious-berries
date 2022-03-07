package io.ix0rai.bodaciousberries.registry.particles;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.mixin.accessors.DefaultParticleTypeAccessor;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class Particles {
    public static final DefaultParticleType RAINBOW_PARTICLE = DefaultParticleTypeAccessor.create(false);
    public static final DefaultParticleType SLICEY_PARTICLE = DefaultParticleTypeAccessor.create(false);

    public static void registerParticles() {
        registerParticle("rainbow_particle", RAINBOW_PARTICLE, RainbowParticleFactory::new);
        registerParticle("slicey_particle", SLICEY_PARTICLE, SliceyParticle.Factory::new);
    }

    private static void registerParticle(String name, DefaultParticleType particle, ParticleFactoryRegistry.PendingParticleFactory<DefaultParticleType> constructor) {
        Registry.register(Registry.PARTICLE_TYPE, Bodaciousberries.getIdentifier(name), particle);
        ParticleFactoryRegistryImpl.INSTANCE.register(particle, constructor);
    }
}
