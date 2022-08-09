package io.ix0rai.bodaciousberries.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Iterator;

public class GojiBerryBlend extends Blend {
    public GojiBerryBlend(Settings settings, Item ingredient0, Item ingredient1, Item ingredient2) {
        super(settings, ingredient0, ingredient1, ingredient2);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // note: a mod conflict somehow causes a cme here, so we use an iterator over an enhanced for
        Iterator<StatusEffectInstance> iterator = user.getStatusEffects().iterator();
        do {
            StatusEffectInstance instance = iterator.next();
            if (instance.getEffectType().getType().equals(StatusEffectType.HARMFUL)) {
                user.removeStatusEffect(instance.getEffectType());
            }
        } while (iterator.hasNext());

        return super.finishUsing(stack, world, user);
    }
}
