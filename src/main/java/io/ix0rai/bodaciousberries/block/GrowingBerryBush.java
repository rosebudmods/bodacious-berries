package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.util.BerryTypeConfigurationException;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class GrowingBerryBush extends BasicBerryBush {
    private final DoubleBerryBush futureBush;

    public GrowingBerryBush(Settings settings, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge, DoubleBerryBush bush) {
        super(settings, bush.getBerryType(), bush.getMaxAge(), smallShape, largeShape, sizeChangeAge);
        this.futureBush = bush;
    }

    public void grow(ServerWorld world, BlockPos pos, BlockState state, int newAge) {
        if (newAge < maxAge) {
            world.setBlockState(pos, state.with(AGE, newAge), 2);
        } else {
            TallPlantBlock.placeAt(world, futureBush.getDefaultState(), pos, 2);
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        //workaround for GrowingBerryBushes generating above their configured max age
        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BerryTypeConfigurationException.check(berryType);

        //a GrowingBerryBush cannot produce berries until it grows to its double bush state

        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            final int newAge = Math.min(maxAge, state.get(AGE) + 1);
            if (newAge < maxAge) {
                return ActionResult.PASS;
            } else {
                TallPlantBlock.placeAt(world, futureBush.getDefaultState(), pos, 2);
            }

            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }
}
