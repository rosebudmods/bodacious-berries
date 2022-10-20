package io.ix0rai.bodacious_berries.block;

import io.ix0rai.bodacious_berries.registry.Berry;
import io.ix0rai.bodacious_berries.registry.BodaciousBushes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BerryVine extends VineBlock implements BerryBush {
    protected static final int MAX_AGE = 3;
    protected static final int MAX_BERRY_AMOUNT = 3;
    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    protected final Berry berry;

    public BerryVine(Berry berry) {
        super(BodaciousBushes.BERRY_BUSH_SETTINGS);
        this.berry = berry;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return this.getBerryItem().getDefaultStack();
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        super.randomTick(state, world, pos, random);

        int age = state.get(AGE);
        // if the age isn't maximum and the light level is high enough grow the bush
        if (age < MAX_AGE && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, age + 1), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return hasRandomTicks(state);
    }

    @Override
    public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) {
        return hasRandomTicks(state);
    }

    @Override
    public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
        int newBerryAge = Math.min(MAX_AGE, state.get(AGE) + 1);
        grow(world, pos, state, newBerryAge);
    }

    public void grow(ServerWorld world, BlockPos pos, BlockState state, int newAge) {
        world.setBlockState(pos, state.with(AGE, newAge), Block.NOTIFY_LISTENERS);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (state.get(AGE) == MAX_AGE) {
            return BasicBerryBush.pickBerries(pos, world, state, this.getBerryItem());
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getSizeChangeAge() {
        return 0;
    }

    @Override
    public IntProperty getAge() {
        return AGE;
    }

    @Override
    public Berry getBerry() {
        return berry;
    }

    @Override
    public int getMaxBerryAmount() {
        return MAX_BERRY_AMOUNT;
    }
}
