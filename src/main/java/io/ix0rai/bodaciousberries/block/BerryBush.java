package io.ix0rai.bodaciousberries.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BerryBush extends Fertilizable {
    void setBerryType(Item berryType);
    Item getBerryType();
    int getMaxAge();
    BlockState getBaseState();
    int getSizeChangeAge();
    IntProperty getAge();
    int getMaxBerryAmount();
    default void resetAge(World world, BlockPos pos, BlockState state) {
        IntProperty age = getAge();
        int currentAge = state.get(age);

        if (currentAge >= getSizeChangeAge()) {
            world.setBlockState(pos, state.with(age, getSizeChangeAge()), Block.NOTIFY_LISTENERS);
        } else {
            world.setBlockState(pos, state.with(age, 0), Block.NOTIFY_LISTENERS);
        }
    }
}
