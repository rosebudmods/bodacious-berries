package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.registry.Particles;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.Random;

public class RainberryBush extends BasicBerryBush {
    public RainberryBush(Settings settings, Item berryType, int maxAge, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(settings, berryType, maxAge, smallShape, largeShape, sizeChangeAge);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(AGE) == maxAge) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = i + random.nextDouble();
            double e = j + 0.7D;
            double f = k + random.nextDouble();
            world.addParticle(Particles.RAINBOW_PARTICLE_TYPE, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }
}
