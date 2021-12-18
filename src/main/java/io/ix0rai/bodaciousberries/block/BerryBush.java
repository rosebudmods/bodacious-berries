package io.ix0rai.bodaciousberries.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

import java.util.Random;

@SuppressWarnings("unused")
public interface BerryBush extends Fertilizable {
    Item getBerryType();
    Item getUnripeBerryType();
    IntProperty getBerryAge();
    int getMaxBerryAge();
    VoxelShape getSmallShape();
    VoxelShape getLargeShape();
    int getSizeChangeAge();
    void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, Integer newAge);
    void setBerryType(Item berryType);
    void setUnripeBerryType(Item unripeBerryType);
}
