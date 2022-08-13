package io.ix0rai.bodacious_berries.mixin;

import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceInvoker {
    @Invoker("<init>")
    static DamageSource create(String name) {
        throw new UnsupportedOperationException("trying to create a damage source with name " + name + " failed; this is a mixin and cannot be called outside of a mixin environment");
    }
}
