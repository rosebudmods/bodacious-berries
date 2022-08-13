package io.ix0rai.bodacious_berries.mixin;

import io.ix0rai.bodacious_berries.registry.BodaciousBushes;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * reason: make mobs avoid spiky bushes
 * @author ix0rai
 */
@Mixin(LandPathNodeMaker.class)
public class PathNodeMakerInjector {
    @Inject(method="getCommonNodeType", at = @At("HEAD"), cancellable = true)
    private static void getCommonNodeType(BlockView blockView, BlockPos blockPos, CallbackInfoReturnable<PathNodeType> callbackInfoReturnable) {
        if (blockView.getBlockState(blockPos).isIn(BodaciousBushes.BERRY_BUSHES)) {
            callbackInfoReturnable.setReturnValue(PathNodeType.DAMAGE_OTHER);
        }
    }
}
