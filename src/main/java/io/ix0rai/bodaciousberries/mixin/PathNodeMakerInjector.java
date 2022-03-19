package io.ix0rai.bodaciousberries.mixin;

import io.ix0rai.bodaciousberries.block.BerryBush;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class PathNodeMakerInjector {
    @Inject(method="getCommonNodeType", at = @At("HEAD"), cancellable = true)
    private static void getCommonNodeType(BlockView blockView, BlockPos blockPos, CallbackInfoReturnable<PathNodeType> callbackInfoReturnable) {
        //this method is what determines if a block is dangerous and is used by the sweet berry bush, so we need to inject here to make our bushes a danger
        //honestly, I have no idea how path nodes work, so we're checking the higher state too, just to be safe
        BlockState state = blockView.getBlockState(blockPos.up());
        BlockState state2 = blockView.getBlockState(blockPos);

        if (state.getBlock() instanceof BerryBush || state2.getBlock() instanceof BerryBush) {
            callbackInfoReturnable.setReturnValue(PathNodeType.DAMAGE_OTHER);
        }
    }
}
