package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.mixin.accessors.DamageSourceAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SpikedBerryBush extends BasicBerryBush {
    public static final DamageSource BERRY_BUSH = DamageSourceAccessor.create("berry_bush");
    //copy of SweetBerryBushBlock's minimum distance
    private static final float MINIMUM_DAMAGE_DISTANCE = 0.003f;
    private final float damage;

    /**
     * derived from {@link BasicBerryBush}
     * @param damage the amount of damage the berry bush does on contact
     */
    public SpikedBerryBush(Settings settings, Item berryType, int maxAge, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge, float damage) {
        super(settings, berryType, maxAge, smallShape, largeShape, sizeChangeAge);
        if (damage < 1.0f) {
            throw new IllegalArgumentException("damage must be greater than or equal to 1");
        }
        this.damage = damage;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || SMALL_ENTITIES.contains(entity.getType()) || state.get(AGE) < sizeChangeAge) {
            return;
        }

        entity.slowMovement(state, BERRY_BUSH_SLOWING_VECTOR);

        boolean entityDidNotMove = entity.lastRenderX == entity.getX() && entity.lastRenderZ == entity.getZ();
        if (!(world.isClient || entityDidNotMove)) {
            //the entity must move a minimum distance to be damaged
            //this is implemented so if you accidentally touch the keyboard for a millisecond, you won't be damaged
            double distanceMovedX = Math.abs(entity.getX() - entity.lastRenderX);
            double distanceMovedZ = Math.abs(entity.getZ() - entity.lastRenderZ);
            if (distanceMovedX >= MINIMUM_DAMAGE_DISTANCE || distanceMovedZ >= MINIMUM_DAMAGE_DISTANCE) {
                entity.damage(BERRY_BUSH, damage);
            }
        }
    }

    //TODO: for some reason returning false on this method DOES NOT STOP MOBS FROM PATHFINDING THROUGH
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return state.get(AGE) < sizeChangeAge;
    }
}
