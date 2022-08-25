package io.ix0rai.bodacious_berries.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChorusBerryBush extends BasicBerryBush.FourStageBush {
    public ChorusBerryBush(Identifier berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(berryType, smallShape, largeShape, sizeChangeAge);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.END_STONE);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {
        if (state.get(getAge()) == maxAge) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 0.3D;
            double z = pos.getZ() + random.nextDouble();
            if (random.nextBoolean()) {
                world.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
