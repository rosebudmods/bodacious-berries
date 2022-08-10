package io.ix0rai.bodacious_berries.registry;

import com.google.gson.JsonObject;
import io.ix0rai.bodacious_berries.block.entity.JuicerRecipes;
import io.ix0rai.bodacious_berries.item.Blend;
import io.ix0rai.bodacious_berries.item.ChorusBerryJuice;
import io.ix0rai.bodacious_berries.item.EndBlend;
import io.ix0rai.bodacious_berries.item.GojiBerryBlend;
import io.ix0rai.bodacious_berries.item.Juice;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class BodaciousJuices {
    public static final Item JUICE_RECEPTACLE = Items.GLASS_BOTTLE;
    public static final Item.Settings JUICE_SETTINGS = new Item.Settings().recipeRemainder(JUICE_RECEPTACLE).group(ItemGroup.FOOD).maxCount(16);
    public static final List<JsonObject> RECIPES = new ArrayList<>();
    public static final Juice DUBIOUS_JUICE = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(2).saturationModifier(3F).build()));

    public static void register() {
        register(io.ix0rai.bodacious_berries.BodaciousBerries.id("dubious_juice"), DUBIOUS_JUICE);
        register("saskatoon_berry_juice", new Juice(BodaciousItems.saskatoonBerries, io.ix0rai.bodacious_berries.BodaciousBerries.translatableText("hint.more_purple")));
        register("strawberry_juice", new Juice(BodaciousItems.strawberries));
        register("raspberry_juice", new Juice(BodaciousItems.raspberries));
        register("blackberry_juice", new Juice(BodaciousItems.blackberries, io.ix0rai.bodacious_berries.BodaciousBerries.translatableText("hint.blackberries_and_sweetness")));
        register("rainberry_juice", new Juice(BodaciousItems.rainberries));
        register("lingonberry_juice", new Juice(BodaciousItems.lingonberries, io.ix0rai.bodacious_berries.BodaciousBerries.translatableText("hint.more_red")));
        register("grape_juice", new Juice(BodaciousItems.grapes));
        register("goji_berry_juice", new Juice(BodaciousItems.gojiBerries, io.ix0rai.bodacious_berries.BodaciousBerries.translatableText("hint.sweetness")));
        register("gooseberry_juice", new Juice(BodaciousItems.gooseberries, io.ix0rai.bodacious_berries.BodaciousBerries.translatableText("hint.red_and_yellow")));
        register("glow_berry_juice", new Juice(Items.GLOW_BERRIES, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 90, 1), 1.0F)));
        register("sweet_berry_juice", new Juice(Items.SWEET_BERRIES, io.ix0rai.bodacious_berries.BodaciousBerries.translatableText("hint.vanilla")));
        register("chorus_berry_juice", new ChorusBerryJuice(BodaciousItems.chorusBerries, null));
        register("cloudberry_juice", new Juice(BodaciousItems.cloudberries, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200, 1), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 1), 1.0f)));

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

        Item[] biomeItems = new Item[]{
                Items.CORNFLOWER, Items.SNOWBALL, Items.CLAY_BALL,
                Items.SAND, Items.SWEET_BERRIES, Items.BIRCH_SAPLING,
                Items.COD, Items.RED_MUSHROOM, Items.SUNFLOWER,
                Items.OAK_SAPLING, Items.PEONY, Items.DARK_OAK_SAPLING,
                Items.ACACIA_SAPLING, Items.RED_SAND, Items.POPPY,
                Items.GLOW_BERRIES, Items.POINTED_DRIPSTONE, Items.COCOA_BEANS
        };

        for (int i = 0; i < biomes.length; i++) {
            RegistryKey<?> key = biomes[i];
            ChorusBerryJuice juice = new ChorusBerryJuice(BodaciousItems.chorusBerries, key.getValue());
            Identifier id = io.ix0rai.bodacious_berries.BodaciousBerries.id("chorus_berry_juice_" + key.getValue().getPath());

            RECIPES.add(JuicerRecipes.createShapelessJson(Registry.ITEM.getId(biomeItems[i]), id));

            Registry.register(Registry.ITEM, id, juice);
        }
    }

    private static void registerBlends() {
        Blend gojiBerryBlend = new GojiBerryBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(6.0F).statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 800, 1), 1.0F).build()), BodaciousItems.gojiBerries, BodaciousItems.gojiBerries, Items.SUGAR_CANE);
        registerBlend("goji_berry_blend", gojiBerryBlend);
        Blend oppositeJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(4).saturationModifier(5.0F).build()), BodaciousItems.raspberries, BodaciousItems.blackberries, Items.SUGAR_CANE);
        registerBlend("opposite_juice", oppositeJuice);
        Blend rainberryBlend = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(10.0F).build()), BodaciousItems.rainberries, BodaciousItems.gojiBerries, Items.GOLDEN_APPLE);
        registerBlend("rainberry_blend", rainberryBlend);
        Blend gooseberryRum = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturationModifier(4.0f).build()), BodaciousItems.gooseberries, BodaciousItems.gooseberries, Items.WHEAT);
        registerBlend("gooseberry_rum", gooseberryRum);
        Blend redJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(6.0F).build()), BodaciousItems.strawberries, Items.SWEET_BERRIES, BodaciousItems.lingonberries);
        registerBlend("red_juice", redJuice);
        Blend endBlend = new EndBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(3).saturationModifier(6.0F).build()), BodaciousItems.chorusBerries, BodaciousItems.rainberries, Items.CHORUS_FRUIT);
        registerBlend("end_blend", endBlend);
        Blend purpleDelight = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(5.0F).build()), BodaciousItems.chorusBerries, BodaciousItems.grapes, BodaciousItems.saskatoonBerries);
        registerBlend("purple_delight", purpleDelight);
        Blend trafficLightJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(6.0F).build()), BodaciousItems.gooseberries, Items.GLOW_BERRIES, BodaciousItems.raspberries);
        registerBlend("traffic_light_juice", trafficLightJuice);
        Blend vanillaDelight = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(4).saturationModifier(4.0F).build()), Items.GLOW_BERRIES, Items.SWEET_BERRIES, Items.APPLE);
        registerBlend("vanilla_delight", vanillaDelight);
    }

    private static void register(String name, Juice juice) {
        Identifier id = io.ix0rai.bodacious_berries.BodaciousBerries.id(name);
        JuicerRecipes.addJuiceRecipe(new Identifier("c", Registry.ITEM.getId(juice.getBerry()).getPath()), id);
        register(id, juice);
    }

    private static void registerBlend(String name, Blend blend) {
        Identifier id = io.ix0rai.bodacious_berries.BodaciousBerries.id(name);
        register(id, blend);
        JuicerRecipes.addJuiceRecipe(Registry.ITEM.getId(blend.getIngredient0()), Registry.ITEM.getId(blend.getIngredient1()), Registry.ITEM.getId(blend.getIngredient2()), id);
    }

    private static void register(Identifier id, Juice juice) {
        Registry.register(Registry.ITEM, id, juice);
    }
}
