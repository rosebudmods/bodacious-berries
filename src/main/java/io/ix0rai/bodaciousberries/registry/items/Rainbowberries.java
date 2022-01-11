package io.ix0rai.bodaciousberries.registry.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Rainbowberries extends AliasedBlockItem {
    public Rainbowberries(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        user.getInventory().insertStack(new ItemStack(switch(user.getRandom().nextInt(0, 16)) {
            case 0 -> Items.WHITE_DYE;
            case 1 -> Items.ORANGE_DYE;
            case 2 -> Items.MAGENTA_DYE;
            case 3 -> Items.LIGHT_BLUE_DYE;
            case 4 -> Items.YELLOW_DYE;
            case 5 -> Items.LIME_DYE;
            case 6 -> Items.PINK_DYE;
            case 7 -> Items.GRAY_DYE;
            case 8 -> Items.LIGHT_GRAY_DYE;
            case 9 -> Items.CYAN_DYE;
            case 10 -> Items.PURPLE_DYE;
            case 11 -> Items.BLUE_DYE;
            case 12 -> Items.BROWN_DYE;
            case 13 -> Items.GREEN_DYE;
            case 14 -> Items.RED_DYE;
            default -> Items.BLACK_DYE;
        }, 1));

        //magicky sounding sound
        user.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_HIT, SoundCategory.PLAYERS, 0.75F, 0.4F / (user.getRandom().nextFloat() * 0.4F + 0.8F));

        return TypedActionResult.success(stack);
    }
}
