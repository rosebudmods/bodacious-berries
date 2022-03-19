package io.ix0rai.bodaciousberries.mixin.accessors;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface FoodEffectAccessor {
    @Invoker
    void invokeApplyFoodEffects(ItemStack stack, World world, LivingEntity user);
}
