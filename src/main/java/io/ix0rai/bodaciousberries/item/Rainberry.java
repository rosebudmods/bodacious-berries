package io.ix0rai.bodaciousberries.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Rainberry extends Berry {
    private final FoodComponent foodComponent = new FoodComponent.Builder().hunger(3).saturationModifier(3.5F).build();
    public Rainberry(Block block, String name, Settings settings) {
        super(block, name, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        //give the player random dye
        user.getInventory().insertStack(new ItemStack(DyeItem.byColor(DyeColor.byId(world.random.nextInt(0, 16))), 1));

        //magicky sounding sound
        user.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_HIT, SoundCategory.PLAYERS, 0.75F, 0.4F / (user.getRandom().nextFloat() * 0.4F + 0.8F));

        return TypedActionResult.success(stack);
    }

    @Override
    public FoodComponent getFoodComponent() {
        return foodComponent;
    }
}
