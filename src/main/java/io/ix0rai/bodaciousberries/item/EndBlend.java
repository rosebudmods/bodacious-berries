package io.ix0rai.bodaciousberries.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class EndBlend extends Blend {
    public EndBlend(Settings settings, Item ingredient0, Item ingredient1, Item ingredient2) {
        super(settings, ingredient0, ingredient1, ingredient2);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            Random random = user.getRandom();

            double originalX = user.getX();
            double originalY = user.getY();
            double originalZ = user.getZ();

            for (int i = 0; i < 16; i++) {
                double x = user.getX() + (random.nextDouble() - 0.5D) * 40.0D;
                double y = MathHelper.clamp(user.getY() + (random.nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + world.getHeight() - 1));
                double z = user.getZ() + (random.nextDouble() - 0.5D) * 40.0D;

                if (user.hasVehicle()) {
                    user.stopRiding();
                }

                if (user.teleport(x, y, z, true)) {
                    SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
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
