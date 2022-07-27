package io.ix0rai.dfubestie.impl.mixin;

import io.ix0rai.dfubestie.impl.DataFixesInternals;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void addModDataVersions(NbtCompound tag, CallbackInfo ci) {
        DataFixesInternals.addModDataVersions(tag);
    }
}
