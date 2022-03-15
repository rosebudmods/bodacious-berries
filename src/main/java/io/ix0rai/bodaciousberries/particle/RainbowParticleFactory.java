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

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public record RainbowParticleFactory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
    private static final List<float[]> colours = new ArrayList<>();
    static {
        //teal
        colours.add(new float[]{0.17F, 0.77F, 0.88F});
        //green
        colours.add(new float[]{0.00F, 0.72F, 0.01F});
        //blue
        colours.add(new float[]{0.25F, 0.20F, 0.92F});
        //purple
        colours.add(new float[]{0.47F, 0.16F, 0.84F});
        //orange
        colours.add(new float[]{0.74F, 0.37F, 0.20F});
        //yellow
        colours.add(new float[]{0.84F, 0.79F, 0.38F});
        //pink
        colours.add(new float[]{0.90F, 0.40F, 0.74F});
        //red
        colours.add(new float[]{0.88F, 0.15F, 0.15F});
    }

    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        WaterSuspendParticle waterSuspendParticle = WaterSuspendParticleAccessor.create(clientWorld, this.spriteProvider, d, e, f, 0.0D, -0.8D, 0.0D);
        //particles last a couple seconds at most
        waterSuspendParticle.setMaxAge(MathHelper.nextBetween(clientWorld.random, 10, 30));
        //pick random colour
        float[] colour = colours.get(clientWorld.random.nextInt(0, 7));
        waterSuspendParticle.setColor(colour[0], colour[1], colour[2]);
        return waterSuspendParticle;
    }
}
