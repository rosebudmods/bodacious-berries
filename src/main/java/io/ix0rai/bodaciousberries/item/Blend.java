package io.ix0rai.bodaciousberries.item;

import io.ix0rai.bodaciousberries.registry.BodaciousStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class Blend extends Juice {
    private final Item ingredient0;
    private final Item ingredient1;
    private final Item ingredient2;

    public Blend(Settings settings, Item ingredient0, Item ingredient1, Item ingredient2) {
        super(settings);
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity entity) {
            entity.addStatusEffect(new StatusEffectInstance(BodaciousStatusEffects.REFRESHED, 200, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 20, 0));
        }

        return super.finishUsing(stack, world, user);
    }

    public Item getIngredient0() {
        return ingredient0;
    }

    public Item getIngredient1() {
        return ingredient1;
    }

    public Item getIngredient2() {
        return ingredient2;
    }
}
