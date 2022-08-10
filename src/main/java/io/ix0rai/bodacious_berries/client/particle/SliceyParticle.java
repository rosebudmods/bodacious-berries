package io.ix0rai.bodacious_berries.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class SliceyParticle extends AnimatedParticle {
    SliceyParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0f);
        this.scale = 0.25f;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getSize(float tickDelta) {
        float f = (this.age + tickDelta) / this.maxAge;
        return this.scale * MathHelper.clamp((1.5f - MathHelper.square(f)), 0.75f, 1.5f);
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            double posX = randomise(clientWorld.random, d);
            double posY = randomise(clientWorld.random, e);
            double posZ = randomise(clientWorld.random, f);
            return new SliceyParticle(clientWorld, posX, posY, posZ, this.spriteProvider);
        }

        private static double randomise(Random random, double number) {
            return random.nextBoolean() ? number + MathHelper.nextBetween(random, 0.1f, 0.3f) : number - MathHelper.nextBetween(random, 0.1f, 0.3f);
        }
    }
}
