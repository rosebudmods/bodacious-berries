package io.ix0rai.bodaciousberries.block;

import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;

public class ThreeStageBush extends BasicBerryBush {
    public ThreeStageBush(Item berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(berryType, 3, smallShape, largeShape, sizeChangeAge);
    }

    @Override
    public IntProperty getAge() {
        return Properties.AGE_3;
    }
}
