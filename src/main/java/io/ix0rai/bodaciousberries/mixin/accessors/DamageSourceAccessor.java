package io.ix0rai.bodaciousberries.mixin.accessors;

import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceAccessor {
    @Invoker("<init>")
    static DamageSource create(String name) {
        throw new AssertionError();
    }
}
