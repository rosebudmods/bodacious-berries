package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BodaciousStatusEffects {
    public static final StatusEffect REFRESHED = new RefreshedStatusEffect(StatusEffectType.BENEFICIAL, 0xFF0066)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, BodaciousBerries.id("refreshed_movement_speed"), 0.015, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, BodaciousBerries.id("refreshed_attack_damage"), 0.5, EntityAttributeModifier.Operation.ADD_VALUE);

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, BodaciousBerries.id("refreshed"), REFRESHED);
    }

    private static class RefreshedStatusEffect extends StatusEffect {
        public RefreshedStatusEffect(StatusEffectType type, int color) {
            super(type, color);
        }
    }
}
