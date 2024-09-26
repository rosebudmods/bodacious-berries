package io.ix0rai.bodacious_berries.block;

import io.ix0rai.bodacious_berries.registry.Berry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface BerryBush extends Fertilizable {
    List<EntityType<?>> UNSLOWED_ENTITIES = List.of(
            EntityType.FOX,
            EntityType.BEE,
            EntityType.RABBIT,
            EntityType.CAT,
            EntityType.ENDERMITE,
            EntityType.BAT,
            EntityType.SILVERFISH,
            EntityType.OCELOT,
            EntityType.PARROT
    );

    /**
     * @return the type of berry that this bush gives when picked
     */
    Berry getBerry();

    /**
     * @return the maximum age to which the bush can grow
     */
    int getMaxAge();

    /**
     * @return the age at which the bush grows into its largest form
     */
    int getSizeChangeAge();

    /**
     * @return the bush's age property
     */
    IntProperty getAge();

    /**
     * @return the maximum amount of berries that can be picked from the bush in one action
     */
    int getMaxBerryAmount();

    /**
     * @param state the block state to check
     * @return whether the specified block state is at its maximum age
     */
    default boolean isFullyGrown(BlockState state) {
        return state.get(getAge()) == getMaxAge();
    }

    /**
     * @return whether the bush can be harvested when at max age.
     */
    default boolean canBeHarvested() {
        return true;
    }

    /**
     * sets the age of the bush its minimum possible age while preserving its size
     * @param world the world in which the bush is growing
     * @param pos the position of the bush
     * @param state the block state to reset
     */
    default void resetAge(World world, BlockPos pos, BlockState state) {
        IntProperty age = getAge();
        int currentAge = state.get(age);

        if (currentAge >= getSizeChangeAge()) {
            world.setBlockState(pos, state.with(age, getSizeChangeAge()), Block.NOTIFY_LISTENERS);
        } else {
            world.setBlockState(pos, state.with(age, 0), Block.NOTIFY_LISTENERS);
        }
    }

    /**
     * gets the registered item associated with the bush's berry
     */
    default Item getBerryItem() {
        return Registries.ITEM.get(getBerry().get());
    }
}
