package io.ix0rai.bodaciousberries.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GojiBerryBlend extends Juice {
    public GojiBerryBlend(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        for (StatusEffectInstance instance : user.getStatusEffects()) {
            user.removeStatusEffect(instance.getEffectType());
        }

        return super.finishUsing(stack, world, user);
    }
}
