package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.item.ChorusBerries;
import io.ix0rai.bodacious_berries.item.GojiBerries;
import io.ix0rai.bodacious_berries.item.Rainberries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.unmapped.C_pmcnnsvg;
import net.minecraft.unmapped.C_uaiigijw;
import net.minecraft.util.Identifier;

public class BodaciousItems {
    public static final TagKey<Item> BERRY_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "berries"));

    // berry items
    public static final BlockItem SASKATOON_BERRIES = new BlockItem(BodaciousBushes.SASKATOON_BERRY_BUSH, settings(Berry.SASKATOON_BERRIES.id(), 3, 0.5f));
    public static final BlockItem STRAWBERRIES = new BlockItem(BodaciousBushes.STRAWBERRY_BUSH, settings(Berry.STRAWBERRIES.id(), 3, 1f));
    public static final BlockItem RASPBERRIES = new BlockItem(BodaciousBushes.RASPBERRY_BUSH, settings(Berry.RASPBERRIES.id(), 3, 0.5f));
    public static final BlockItem BLACKBERRIES = new BlockItem(BodaciousBushes.BLACKBERRY_BUSH, settings(Berry.BLACKBERRIES.id(), 1, 1.5f));
    public static final BlockItem CHORUS_BERRIES = new ChorusBerries(BodaciousBushes.CHORUS_BERRY_BUSH, settings(Berry.CHORUS_BERRIES.id(), 2, 1.6f));
    public static final BlockItem RAINBERRIES = new Rainberries(BodaciousBushes.RAINBERRY_BUSH, settings(Berry.RAINBERRIES.id(), 3, 1.0f));
    public static final BlockItem LINGONBERRIES = new BlockItem(BodaciousBushes.LINGONBERRY_BUSH, settings(Berry.LINGONBERRIES.id(), 2, 0.5f));
    public static final BlockItem GRAPES = new BlockItem(BodaciousBushes.GRAPEVINE, settings(Berry.GRAPES.id(), 2, 1f));
    public static final BlockItem GOJI_BERRIES = new GojiBerries(BodaciousBushes.GOJI_BERRY_BUSH, settings(Berry.GOJI_BERRIES.id(), 1, 2.2f));
    public static final BlockItem GOOSEBERRIES = new BlockItem(BodaciousBushes.GOOSEBERRY_BUSH, settings(Berry.GOOSEBERRIES.id(), 2, 0.5f));
    public static final BlockItem CLOUDBERRIES = new BlockItem(BodaciousBushes.CLOUDBERRY_BUSH, settings(Berry.CLOUDBERRIES.id(), 2, 1).food(
            new FoodComponent.Builder().hunger(2).saturation(1f).build(),
            C_uaiigijw.method_62858().method_62854(new C_pmcnnsvg(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, 1), 1)).method_62852(0.8f).method_62851()));

    public static void register() {
        register(SASKATOON_BERRIES, Berry.SASKATOON_BERRIES);
        register(STRAWBERRIES, Berry.STRAWBERRIES);
        register(RASPBERRIES, Berry.RASPBERRIES);
        register(BLACKBERRIES, Berry.BLACKBERRIES);
        register(CHORUS_BERRIES, Berry.CHORUS_BERRIES);
        register(RAINBERRIES, Berry.RAINBERRIES);
        register(LINGONBERRIES, Berry.LINGONBERRIES);
        register(GRAPES, Berry.GRAPES);
        register(GOJI_BERRIES, Berry.GOJI_BERRIES);
        register(GOOSEBERRIES, Berry.GOOSEBERRIES);
        register(CLOUDBERRIES, Berry.CLOUDBERRIES);

        BodaciousJuices.register();
    }

    /**
     * the chance that a berry will raise the level of compost in a composter
     * <br> out of one: 0.4 = 40%, etc
     */
    private static final float BERRY_COMPOST_LEVEL_INCREASE_CHANCE = 0.4f;

    private static void register(Item item, Berry berry) {
        Item registeredItem = Registry.register(Registries.ITEM, berry.id(), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.addItem(registeredItem));
        CompostingChanceRegistry.INSTANCE.add(item, BERRY_COMPOST_LEVEL_INCREASE_CHANCE);
    }

    private static Item.Settings settings(Identifier id, int hunger, float saturation) {
        return new Item.Settings()
                .key(RegistryKey.of(RegistryKeys.ITEM, id))
                .food(new FoodComponent.Builder().hunger(hunger).saturation(saturation).build(),
                    C_uaiigijw.method_62858().method_62852(0.8f).method_62851());
    }
}
