package io.ix0rai.bodaciousberries.mixin.accessors;

import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WaterSuspendParticle.class)
@SuppressWarnings("unused")
public interface WaterSuspendParticleAccessor {
    @Invoker("<init>")
    static WaterSuspendParticle create(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        throw new AssertionError();
    }
}
