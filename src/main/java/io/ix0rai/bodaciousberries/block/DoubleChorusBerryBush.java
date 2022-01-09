package io.ix0rai.bodaciousberries.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DoubleChorusBerryBush extends DoubleBerryBush {
    public DoubleChorusBerryBush(Settings settings, Item berryType) {
        super(settings, berryType);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.END_STONE);
    }
}
