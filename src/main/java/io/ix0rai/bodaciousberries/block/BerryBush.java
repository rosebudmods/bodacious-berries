package io.ix0rai.bodaciousberries.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.Item;

public interface BerryBush extends Fertilizable {
    void setBerryType(Item berryType);
    int getMaxAge();
    BlockState getDefaultState();
}
