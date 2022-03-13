package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.registry.Bushes;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;

public class SpikyFourStageBush extends SpikedBerryBush {
    public SpikyFourStageBush(Item berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge, float damage) {
        super(berryType, 4, smallShape, largeShape, sizeChangeAge, damage);
    }

    @Override
    public IntProperty getAge() {
        return Bushes.AGE_4;
    }
}
