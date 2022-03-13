package io.ix0rai.bodaciousberries.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ChorusBerryBush extends FourStageBush {
    public ChorusBerryBush(Item berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(berryType, smallShape, largeShape, sizeChangeAge);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.END_STONE);
    }
}
