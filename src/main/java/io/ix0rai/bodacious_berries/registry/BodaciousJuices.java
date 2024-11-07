package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.item.ChorusBerryJuice;
import io.ix0rai.bodacious_berries.item.EndBlend;
import io.ix0rai.bodacious_berries.item.GojiBerryBlend;
import io.ix0rai.bodacious_berries.item.Juice;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.unmapped.C_pmcnnsvg;
import net.minecraft.unmapped.C_uaiigijw;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.minecraft.world.biome.Biomes.*;

public class BodaciousJuices {
    public static final Item JUICE_RECEPTACLE = Items.GLASS_BOTTLE;
    public static final Item.Settings JUICE_SETTINGS = new Item.Settings().recipeRemainder(JUICE_RECEPTACLE).maxCount(16);
    public static final Juice DUBIOUS_JUICE = new Juice("dubious_juice", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(2).saturation(2F).build()));

    public static void register() {
        register(DUBIOUS_JUICE);
        register(new Juice(Berry.SASKATOON_BERRIES, BodaciousItems.SASKATOON_BERRIES));
        register(new Juice(Berry.STRAWBERRIES, BodaciousItems.STRAWBERRIES));
        register(new Juice(Berry.RASPBERRIES, BodaciousItems.RASPBERRIES));
        register(new Juice(Berry.BLACKBERRIES, BodaciousItems.BLACKBERRIES));
        register(new Juice(Berry.RAINBERRIES, BodaciousItems.RAINBERRIES));
        register(new Juice(Berry.LINGONBERRIES, BodaciousItems.LINGONBERRIES));
        register(new Juice(Berry.GRAPES, BodaciousItems.GRAPES));
        register(new Juice(Berry.GOJI_BERRIES, BodaciousItems.GOJI_BERRIES));
        register(new Juice(Berry.GOOSEBERRIES, BodaciousItems.GOOSEBERRIES));
        register(new Juice("glow_berry_juice", Items.GLOW_BERRIES, new FoodComponent.Builder(), C_uaiigijw.method_62859().method_62854(new C_pmcnnsvg(new StatusEffectInstance(StatusEffects.GLOWING, 90, 1), 1.0F))));
        register(new Juice("sweet_berry_juice", Items.SWEET_BERRIES));
        register(new ChorusBerryJuice(BodaciousItems.CHORUS_BERRIES, null));
        register(new Juice(Berry.CLOUDBERRIES, BodaciousItems.CLOUDBERRIES, new FoodComponent.Builder(), C_uaiigijw.method_62859().method_62854(new C_pmcnnsvg(List.of(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200, 1), new StatusEffectInstance(StatusEffects.LEVITATION, 600, 1))))));

        createBiomeChorusJuice();
        registerBlends();
    }

    private static void createBiomeChorusJuice() {
        RegistryKey<?>[] biomes = new RegistryKey<?>[]{
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                LUSH_CAVES, DRIPSTONE_CAVES, JUNGLE
        };

        for (RegistryKey<?> key : biomes) {
            ChorusBerryJuice juice = new ChorusBerryJuice(BodaciousItems.CHORUS_BERRIES, key.getValue());
            Identifier id = BodaciousBerries.id("chorus_berry_juice_" + key.getValue().getPath());
            Registry.register(Registries.ITEM, id, juice);
        }
    }

    private static void registerBlends() {
        register(new GojiBerryBlend(JUICE_SETTINGS.food(
                new FoodComponent.Builder().hunger(5).saturation(1.8F).build(),
                C_uaiigijw.method_62859().method_62854(new C_pmcnnsvg(new StatusEffectInstance(StatusEffects.GLOWING, 800, 1), 1.0F)).method_62851())));
        register(new Juice("opposite_juice", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(4).saturation(1.2F).build())));
        register(new Juice("rainberry_blend", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturation(1.6F).build())));
        register(new Juice("gooseberry_rum", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturation(1.0f).build())));
        register(new Juice("red_juice", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturation(1.3F).build())));
        register(new EndBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(3).saturation(3.3F).build())));
        register(new Juice("purple_delight", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturation(1.6F).build())));
        register(new Juice("traffic_light_juice", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturation(1.4F).build())));
        register(new Juice("vanilla_delight", JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(4).saturation(2.0F).build())));
    }

    private static void register(Juice juice) {
        register(juice.getJuiceId(), juice);
    }

    private static void register(Identifier id, Juice juice) {
        Item item = Registry.register(Registries.ITEM, id, juice);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINKS).register(entries -> entries.addItem(item));
    }
}
