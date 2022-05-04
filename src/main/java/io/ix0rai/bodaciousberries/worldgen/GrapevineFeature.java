package io.ix0rai.bodaciousberries.worldgen;

import com.mojang.serialization.Codec;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.minecraft.block.Block;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.VinesFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GrapevineFeature extends VinesFeature implements FeatureConfig {
    public GrapevineFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();

        //ensure that block we're generating to is air
        if (structureWorldAccess.isAir(blockPos)) {
            //check all directions
            Direction direction = checkDirections(structureWorldAccess, blockPos);
            if (direction != null) {
                //if we found a direction, generate vines
                structureWorldAccess.setBlockState(blockPos, Bushes.GRAPEVINE.getDefaultState().with(VineBlock.getFacingProperty(direction), true).with(Bushes.GRAPEVINE.getAge(), Bushes.GRAPEVINE.getMaxAge()), Block.NOTIFY_LISTENERS);
                return true;
            }
        }

        return false;
    }

    private Direction checkDirections(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && VineBlock.shouldConnectTo(structureWorldAccess, blockPos.offset(direction), direction)) {
                return direction;
            }
        }

        return null;
    }
}
