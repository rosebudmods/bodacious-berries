package io.ix0rai.bodaciousberries.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Iterator;

public class GojiBerryBlend extends Juice {
    public GojiBerryBlend(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //note: a mod conflict causes a cme here, so we use an iterator over an enhanced for
        Iterator<StatusEffectInstance> iterator = user.getStatusEffects().iterator();

        while (iterator.hasNext()) {
            StatusEffectInstance instance = iterator.next();
            user.removeStatusEffect(instance.getEffectType());
        }

        return super.finishUsing(stack, world, user);
    }
}
