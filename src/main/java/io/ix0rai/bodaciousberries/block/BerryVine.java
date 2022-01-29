package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.util.BerryTypeConfigurationException;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.client.render.RenderLayer;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BerryVine extends VineBlock implements BerryBush {
    protected static final int MAX_AGE = 3;
    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
    protected static final int MAX_BERRY_AMOUNT = 3;

    protected Item berryType;

    public BerryVine(Settings settings, Item berry) {
        super(settings.nonOpaque());
        this.berryType = berry;
        //ensure cutout texture is rendered
        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
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
        grow(world, pos, state, newBerryAge);
    }

    public void grow(ServerWorld world, BlockPos pos, BlockState state, int newAge) {
        world.setBlockState(pos, state.with(AGE, newAge), 2);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BerryTypeConfigurationException.check(berryType);

        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (state.get(AGE) == MAX_AGE) {
            return BasicBerryBush.pickBerries(pos, world, state, berryType, MAX_BERRY_AMOUNT, 0, AGE);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
}
