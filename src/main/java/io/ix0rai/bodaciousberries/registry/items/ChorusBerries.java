package io.ix0rai.bodaciousberries.registry.items;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Random;

public class ChorusBerries extends AliasedBlockItem {
    public ChorusBerries(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Random worldRandom = world.getRandom();
        boolean b = worldRandom.nextBoolean();
        user.teleport(
                (b ? user.getX() + worldRandom.nextInt(0, 10) : user.getX() - worldRandom.nextInt(0, 10)),
                user.getY(),
                (b ? user.getZ() - worldRandom.nextInt(0, 10) : user.getZ() + worldRandom.nextInt(0, 10)),
                true
        );

        world.playSound((PlayerEntity) user, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);

        return super.finishUsing(stack, world, user);
    }
}
