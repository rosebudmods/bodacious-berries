package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.registry.Bushes;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;

public class FourStageBush extends BasicBerryBush {
    public FourStageBush(Item berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(berryType, 4, smallShape, largeShape, sizeChangeAge);
    }

    @Override
    public IntProperty getAge() {
        return Bushes.AGE_4;
    }
}
