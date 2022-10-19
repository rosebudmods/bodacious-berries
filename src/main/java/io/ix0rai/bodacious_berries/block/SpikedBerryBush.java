package io.ix0rai.bodacious_berries.block;

import io.ix0rai.bodacious_berries.mixin.DamageSourceInvoker;
import io.ix0rai.bodacious_berries.registry.Berry;
import io.ix0rai.bodacious_berries.registry.BodaciousBushes;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class SpikedBerryBush extends BasicBerryBush {
    public static final DamageSource BERRY_BUSH = DamageSourceInvoker.create("berry_bush");
    // copy of SweetBerryBushBlock's minimum distance
    private static final float MINIMUM_DAMAGE_DISTANCE = 0.003f;
    private final float damage;

    /**
     * derived from {@link BasicBerryBush}
     * @param damage the amount of damage the berry bush does on contact
     */
    public SpikedBerryBush(Berry berry, int maxAge, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge, float damage) {
        super(berry, maxAge, smallShape, largeShape, sizeChangeAge);
        LandPathNodeTypesRegistry.register(this, PathNodeType.DAMAGE_OTHER, null);
        if (damage < 1.0f) {
            throw new IllegalArgumentException("damage must be greater than or equal to 1");
        }
        this.damage = damage;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || UNSLOWED_ENTITIES.contains(entity.getType()) || state.get(getAge()) < sizeChangeAge) {
            return;
        }

        entity.slowMovement(state, BERRY_BUSH_SLOWING_VECTOR);

        if (!(world.isClient) && movedMinDistance(entity)) {
            entity.damage(BERRY_BUSH, damage);
        }
    }

    private boolean movedMinDistance(Entity entity) {
        // the entity must move a minimum distance to be damaged
        // this is implemented so if you accidentally touch the keyboard for a millisecond, you won't be damaged
        return !(entity.lastRenderX == entity.getX() && entity.lastRenderZ == entity.getZ())
                && (Math.abs(entity.getX() - entity.lastRenderX) >= MINIMUM_DAMAGE_DISTANCE
                || Math.abs(entity.getZ() - entity.lastRenderZ) >= MINIMUM_DAMAGE_DISTANCE);
    }

    public static class SpikyFourStageBush extends SpikedBerryBush {
        public SpikyFourStageBush(Berry berryType, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge, float damage) {
            super(berryType, 4, smallShape, largeShape, sizeChangeAge, damage);
        }

        @Override
        public IntProperty getAge() {
            return BodaciousBushes.AGE_4;
        }
    }
}
