package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.registry.items.Berries;
import io.ix0rai.bodaciousberries.util.ImproperConfigurationException;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class RainbowberryBush extends BasicBerryBush {
    public RainbowberryBush(Settings settings, Item berryType, int maxBerryAge, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        super(settings, berryType, maxBerryAge, smallShape, largeShape, sizeChangeAge);
    }

    /**
     * must be overridden to implement our custom {@code pickBerries()} method
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (berryType == null) {
            throw new ImproperConfigurationException("parameter berryType is null, use method setBerryType(Item) to ensure that it is set before the berry bush is registered");
        }

        final int currentBerryAge = state.get(BERRY_AGE);
        //if bone meal is allowed to be used, grow plant and pass action
        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            final int newAge = Math.min(maxBerryAge, currentBerryAge + 1);
            world.setBlockState(pos, state.with(BERRY_AGE, newAge), 2);
            return ActionResult.PASS;
        } else if (currentBerryAge > 1) {
            //otherwise, give berries
            return pickRainbowberries(pos, world, state);
        } else {
            //otherwise, do default use action from superclass
            return ActionResult.PASS;
        }
    }

    /**
     * handles picking rainbowberries
     * more documented version in superclass
     */
    public ActionResult pickRainbowberries(BlockPos pos, World world, BlockState state) {
        world.playSound(null, pos, selectPickSound(), SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);

        int berryAmount = world.random.nextInt(MAX_BERRY_AMOUNT + 1) + 1;
        for (int i = 0; i < berryAmount; i++) {
            Item selectedBerry = switch(world.random.nextInt(0, 16)) {
                case 0 -> Berries.WHITE_RAINBOWBERRIES;
                case 1 -> Berries.ORANGE_RAINBOWBERRIES;
                case 2 -> Berries.MAGENTA_RAINBOWBERRIES;
                case 3 -> Berries.LIGHT_BLUE_RAINBOWBERRIES;
                case 4 -> Berries.YELLOW_RAINBOWBERRIES;
                case 5 -> Berries.LIME_RAINBOWBERRIES;
                case 6 -> Berries.PINK_RAINBOWBERRIES;
                case 7 -> Berries.GRAY_RAINBOWBERRIES;
                case 8 -> Berries.LIGHT_GRAY_RAINBOWBERRIES;
                case 9 -> Berries.CYAN_RAINBOWBERRIES;
                case 10 -> Berries.PURPLE_RAINBOWBERRIES;
                case 11 -> Berries.BLUE_RAINBOWBERRIES;
                case 12 -> Berries.BROWN_RAINBOWBERRIES;
                case 13 -> Berries.GREEN_RAINBOWBERRIES;
                case 14 -> Berries.RED_RAINBOWBERRIES;
                default -> Berries.BLACK_RAINBOWBERRIES;
            };

            dropStack(world, pos, new ItemStack(selectedBerry, 1));
        }

        //reset berry growth; they were just picked
        world.setBlockState(pos, state.with(getBerryAge(), sizeChangeAge), 2);
        return ActionResult.success(world.isClient);
    }
}
