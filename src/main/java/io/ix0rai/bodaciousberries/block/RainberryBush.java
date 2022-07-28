package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.client.particle.Particles;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
public class RainberryBush extends BasicBerryBush.FourStageBush {
    public RainberryBush(Item berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(berryType, smallShape, largeShape, sizeChangeAge);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {
        if (state.get(getAge()) == maxAge) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = i + random.nextDouble();
            double e = j + 0.7D;
            double f = k + random.nextDouble();
            world.addParticle(Particles.RAINBOW_PARTICLE, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }
}
