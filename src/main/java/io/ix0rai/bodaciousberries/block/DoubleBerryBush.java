package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.registry.BodaciousItems;
import io.ix0rai.bodaciousberries.util.ImproperConfigurationException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

//minecraft deprecates methods oddly
@SuppressWarnings({"deprecation", "unused"})
public class DoubleBerryBush extends TallPlantBlock implements BerryBush {
    private static final Vec3d DOUBLE_BUSH_SLOWING_VECTOR = new Vec3d(0.7D, 0.9D, 0.7D);
    private static final int MAX_BERRY_AMOUNT = 6;

    private static final IntProperty BERRY_AGE = IntProperty.of("berry_age", 0, 3);
    private final int maxBerryAge;
    private Item berryType;
    private Item unripeBerryType;
    private final boolean spiky;
    private final DamageSource damageSource;

    public DoubleBerryBush(Settings settings, Item berryType, Item unripeBerryType, int maxBerryAge, BasicBerryBush.DamageSourceTwoElectricBoogaloo damageSource) {
        super(settings);
        this.maxBerryAge = maxBerryAge;
        this.berryType = berryType;
        this.unripeBerryType = unripeBerryType;
        this.spiky = damageSource != null;
        this.damageSource = damageSource;
    }

    public DoubleBerryBush(Settings settings, Item berryType, Item unripeBerryType, int maxBerryAge) {
        this(settings, berryType, unripeBerryType, maxBerryAge, null);
    }

    public void setBerryType(Item berryType) {
        this.berryType = berryType;
    }

    public void setUnripeBerryType(Item unripeBerryType) {
        this.unripeBerryType = unripeBerryType;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(BodaciousItems.SASKATOON_BERRIES);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        final EntityType<?> type = entity.getType();

        if (entity instanceof LivingEntity && !BasicBerryBush.SMALL_ENTITIES.contains(type)) {
            entity.slowMovement(state, DOUBLE_BUSH_SLOWING_VECTOR);
            //damage as well if our bush is thorny
            if (spiky) {
                entity.damage(damageSource, 1.0F);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        //so it turns out I spent about an hour and a half debugging a crash before realising it originated from this property not existing
        //yay me
        //this is staying in the final version
        builder.add(BERRY_AGE).add(HALF);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(BERRY_AGE) < maxBerryAge;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(BERRY_AGE);
        //if the age isn't maximum and the light level is high enough grow the bush
        if (age < maxBerryAge && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(BERRY_AGE, age + 1), 2);
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return hasRandomTicks(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return hasRandomTicks(state);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int newBerryAge = Math.min(maxBerryAge, state.get(BERRY_AGE) + 1);
        grow(world, random, pos, state, newBerryAge);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, Integer newAge) {
        world.setBlockState(pos, state.with(BERRY_AGE, newAge), 2);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (berryType == null) {
            throw new ImproperConfigurationException("parameter berryType is null, use method setBerryType(Item) to ensure that it is set before the berry bush is registered");
        }

        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            world.setBlockState(pos, state.with(BERRY_AGE, Math.min(maxBerryAge, state.get(BERRY_AGE) + 1)), 2);
            return ActionResult.PASS;
        } else if (state.get(BERRY_AGE) > 1) {
            return BasicBerryBush.pickBerries(pos, world, state, berryType, unripeBerryType, MAX_BERRY_AMOUNT, maxBerryAge, 0, BERRY_AGE);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public Item getBerryType() {
        return berryType;
    }

    @Override
    public Item getUnripeBerryType() {
        return unripeBerryType;
    }

    @Override
    public int getBerryAge(BlockState state) {
        return state.get(BERRY_AGE);
    }

    @Override
    public IntProperty getBerryAgeProperty() {
        return BERRY_AGE;
    }

    @Override
    public int getMaxBerryAge() {
        return maxBerryAge;
    }

    @Override
    public VoxelShape getSmallShape() {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getLargeShape() {
        return VoxelShapes.fullCube();
    }

    /**
     * DO NOT USE, SIZE CHANGE AGE DOES NOT APPLY TO DOUBLE BERRY BUSHES
     * @return 0
     */
    @Override
    public int getSizeChangeAge() {
        return 0;
    }

    @Override
    public boolean isSpiky() {
        return spiky;
    }

    @Override
    public DamageSource getDamageSource() {
        return damageSource;
    }
}
