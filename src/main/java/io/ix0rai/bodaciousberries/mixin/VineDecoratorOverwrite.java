package io.ix0rai.bodaciousberries.mixin;

import io.ix0rai.bodaciousberries.block.BerryVine;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.BiConsumer;

@Mixin(LeavesVineTreeDecorator.class)
public class VineDecoratorOverwrite {
    /**
     * reason: get grapevines to spawn on trees more naturally
     * @author ix0rai
     */
    @Overwrite
    private static void placeVines(TestableWorld world, BlockPos pos, BooleanProperty facing, BiConsumer<BlockPos, BlockState> replacer) {
        placeVine((StructureWorldAccess) world, replacer, pos, facing);
        int i = 4;

        for(pos = pos.down(); Feature.isAir(world, pos) && i > 0; --i) {
            placeVine((StructureWorldAccess) world, replacer, pos, facing);
            pos = pos.down();
        }
    }

    private static void placeVine(StructureWorldAccess access, BiConsumer<BlockPos, BlockState> replacer, BlockPos pos, BooleanProperty facing) {
        BlockState block;

        if (access.getBlockState(pos.up()).getBlock() == Blocks.VINE) {
            block = Blocks.VINE.getDefaultState().with(facing, true);
        } else if (access.getBlockState(pos.up()).getBlock() == Bushes.GRAPEVINE){
            block = Bushes.GRAPEVINE.getDefaultState().with(facing, true).with(BerryVine.AGE, 3);
        } else if (reallyIncrediblyStupidAwfulHorrendousDumbCheck(access, pos)) {
            if (access.getRandom().nextInt(6) == 0 && access.getBiome(pos).getCategory() == Biome.Category.JUNGLE) {
                block = Bushes.GRAPEVINE.getDefaultState().with(facing, true).with(BerryVine.AGE, 3);
            } else if (access.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
                block = Blocks.VINE.getDefaultState().with(facing, true);
            } else {
                return;
            }
        } else {
            return;
        }

        replacer.accept(pos, block);
    }

    private static boolean reallyIncrediblyStupidAwfulHorrendousDumbCheck(StructureWorldAccess access, BlockPos pos) {
        final Block east = access.getBlockState(pos.east()).getBlock();
        final Block west = access.getBlockState(pos.west()).getBlock();
        final Block north = access.getBlockState(pos.north()).getBlock();
        final Block south = access.getBlockState(pos.south()).getBlock();

        return east != Blocks.AIR || west != Blocks.AIR || north != Blocks.AIR || south != Blocks.AIR
                && (east.canMobSpawnInside() || west.canMobSpawnInside() || north.canMobSpawnInside() || south.canMobSpawnInside());
    }
}
