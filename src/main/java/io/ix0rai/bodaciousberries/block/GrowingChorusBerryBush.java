package io.ix0rai.bodaciousberries.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GrowingChorusBerryBush extends GrowingBerryBush {
    public GrowingChorusBerryBush(Settings settings, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge, DoubleBerryBush bush) {
        super(settings, smallShape, largeShape, sizeChangeAge, bush);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.END_STONE);
    }
}
