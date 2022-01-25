package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.util.ImproperConfigurationException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BerryVine extends VineBlock implements BerryBush {
    protected static final int MAX_AGE = 3;
    protected static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
    protected static final int MAX_BERRY_AMOUNT = 3;

    protected Item berryType;

    public BerryVine(Settings settings, Item berry) {
        super(settings);
        this.berryType = berry;
    }

    @Override
    public void setBerryType(Item berryType) {
        this.berryType = berryType;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(berryType);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, UP, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        int age = state.get(AGE);
        //if the age isn't maximum and the light level is high enough grow the bush
        if (age < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, age + 1), 2);
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
        int newBerryAge = Math.min(MAX_AGE, state.get(AGE) + 1);
        grow(world, random, pos, state, newBerryAge);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, int newAge) {
        world.setBlockState(pos, state.with(AGE, newAge), 2);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (berryType == null) {
            throw new ImproperConfigurationException("parameter berryType is null, use method setBerryType(Item) to ensure that it is set before the berry bush is registered");
        }

        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (state.get(AGE) == MAX_AGE) {
            return BasicBerryBush.pickBerries(pos, world, state, berryType, MAX_BERRY_AMOUNT, 0, AGE);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public Item getBerryType() {
        return berryType;
    }

    @Override
    public IntProperty getAge() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public VoxelShape getSmallShape() {
        throw new IllegalStateException("this method should not be called on a vine, use getOutlineShape(...) instead");
    }

    @Override
    public VoxelShape getLargeShape() {
        throw new IllegalStateException("this method should not be called on a vine, use getOutlineShape(...) instead");
    }

    @Override
    public int getSizeChangeAge() {
        throw new IllegalStateException("size change age does not apply to vine type berry bushes");
    }
}
