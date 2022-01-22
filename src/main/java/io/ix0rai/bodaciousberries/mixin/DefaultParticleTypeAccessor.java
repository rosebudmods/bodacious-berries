package io.ix0rai.bodaciousberries.mixin;

import net.minecraft.particle.DefaultParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DefaultParticleType.class)
@SuppressWarnings("unused")
public interface DefaultParticleTypeAccessor {
    @Invoker("<init>")
    static DefaultParticleType create(boolean alwaysShow) {
        throw new AssertionError();
    }
}