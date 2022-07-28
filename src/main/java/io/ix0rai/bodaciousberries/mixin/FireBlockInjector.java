package io.ix0rai.bodaciousberries.mixin;

import io.ix0rai.bodaciousberries.registry.Bushes;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * reason: allow fire to spread to berry bushes
 * @author ix0rai
 */
@Mixin(FireBlock.class)
public class FireBlockInjector {
    @Inject(method = "getSpreadChance", at = @At("HEAD"), cancellable = true)
    private void getSpreadChance(BlockState state, CallbackInfoReturnable<Integer> ci) {
        if (state.isIn(Bushes.BERRY_BUSHES)) {
            ci.setReturnValue(100);
        }
    }

    @Inject(method = "getBurnChance(Lnet/minecraft/block/BlockState;)I", at = @At("HEAD"), cancellable = true)
    private void getBurnChance(BlockState state, CallbackInfoReturnable<Integer> ci) {
        if (state.isIn(Bushes.BERRY_BUSHES)) {
            ci.setReturnValue(60);
        }
    }
}