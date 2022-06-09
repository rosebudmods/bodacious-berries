package io.ix0rai.bodaciousberries.particle;

import io.ix0rai.bodaciousberries.mixin.accessors.WaterSuspendParticleAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public record RainbowParticleFactory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
    private static final float[][] colours = new float[][]{
        // teal
        new float[]{0.17F, 0.77F, 0.88F},
        // green
        new float[]{0.00F, 0.72F, 0.01F},
        // blue
        new float[]{0.25F, 0.20F, 0.92F},
        // purple
        new float[]{0.47F, 0.16F, 0.84F},
        // orange
        new float[]{0.74F, 0.37F, 0.20F},
        // yellow
        new float[]{0.84F, 0.79F, 0.38F},
        // pink
        new float[]{0.90F, 0.40F, 0.74F},
        // red
        new float[]{0.88F, 0.15F, 0.15F},
    };

    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        WaterSuspendParticle waterSuspendParticle = WaterSuspendParticleAccessor.create(clientWorld, this.spriteProvider, d, e, f, 0.0D, -0.8D, 0.0D);
        // particles last a couple seconds at most
        waterSuspendParticle.setMaxAge(MathHelper.nextBetween(clientWorld.random, 10, 30));
        // pick random colour
        float[] colour = colours[clientWorld.random.range(0, 7)];
        waterSuspendParticle.setColor(colour[0], colour[1], colour[2]);
        return waterSuspendParticle;
    }
}
