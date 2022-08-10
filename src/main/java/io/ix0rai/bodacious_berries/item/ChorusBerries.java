package io.ix0rai.bodacious_berries.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

public class ChorusBerries extends AliasedBlockItem {
    public ChorusBerries(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // modified code from ChorusFruitItem
        if (!world.isClient) {
            RandomGenerator random = user.getRandom();

            double originalX = user.getX();
            double originalY = user.getY();
            double originalZ = user.getZ();

            for (int i = 0; i < 16; i++) {
                double x = user.getX() + (random.nextDouble() - 0.5D) * 8.0D;
                double y = MathHelper.clamp(user.getY() + (random.nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + world.getHeight() - 1));
                double z = user.getZ() + (random.nextDouble() - 0.5D) * 8.0D;

                if (user.hasVehicle()) {
                    user.stopRiding();
                }

                if (user.teleport(x, y, z, true)) {
                    SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound(null, originalX, originalY, originalZ, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    user.playSound(soundEvent, 1.0F, 1.0F);
                    break;
                }
            }

            if (user instanceof PlayerEntity entity) {
                entity.getItemCooldownManager().set(this, 10);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
