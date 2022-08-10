package io.ix0rai.bodacious_berries.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GojiBerries extends AliasedBlockItem {
    public GojiBerries(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // clear the user of poison only
        for (StatusEffectInstance effect : user.getStatusEffects()) {
            if (effect.getEffectType().equals(StatusEffects.POISON)) {
                user.removeStatusEffect(StatusEffects.POISON);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}