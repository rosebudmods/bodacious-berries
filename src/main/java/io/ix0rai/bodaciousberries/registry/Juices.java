package io.ix0rai.bodaciousberries.registry;

import com.google.gson.JsonObject;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import io.ix0rai.bodaciousberries.item.Blend;
import io.ix0rai.bodaciousberries.item.ChorusBerryJuice;
import io.ix0rai.bodaciousberries.item.EndBlend;
import io.ix0rai.bodaciousberries.item.GojiBerryBlend;
import io.ix0rai.bodaciousberries.item.Juice;
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

public class Juices {
    public static final Item JUICE_RECEPTACLE = Items.GLASS_BOTTLE;
    public static final Item.Settings JUICE_SETTINGS = new Item.Settings().recipeRemainder(JUICE_RECEPTACLE).group(ItemGroup.FOOD).maxCount(16);
    public static final List<JsonObject> RECIPES = new ArrayList<>();
    public static final Juice DUBIOUS_JUICE = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(2).saturationModifier(3F).build()));

    public static void registerJuice() {
        Registry.register(Registry.RECIPE_SERIALIZER, JuicerRecipe.Serializer.ID, JuicerRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, JuicerRecipe.Type.ID, JuicerRecipe.Type.INSTANCE);

        register(Bodaciousberries.id("dubious_juice"), DUBIOUS_JUICE);
        register("saskatoon_berry_juice", new Juice(Berries.SASKATOON_BERRIES));
        register("strawberry_juice", new Juice(Berries.STRAWBERRY));
        register("raspberry_juice", new Juice(Berries.RASPBERRIES));
        register("blackberry_juice", new Juice(Berries.BLACKBERRIES));
        register("rainberry_juice", new Juice(Berries.RAINBERRY));
        register("lingonberry_juice", new Juice(Berries.LINGONBERRIES));
        register("grape_juice", new Juice(Berries.GRAPES));
        register("goji_berry_juice", new Juice(Berries.GOJI_BERRIES));
        register("gooseberry_juice", new Juice(Berries.GOOSEBERRIES));
        register("glow_berry_juice", new Juice(Items.GLOW_BERRIES, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 90, 1), 1.0F)));
        register("sweet_berry_juice", new Juice(Items.SWEET_BERRIES, new FoodComponent.Builder()));
        register("chorus_berry_juice", new ChorusBerryJuice(Berries.CHORUS_BERRIES, null));
        register("cloudberry_juice", new Juice(Berries.CLOUDBERRIES, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200, 1), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 1), 1.0f)));

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
            ChorusBerryJuice juice = new ChorusBerryJuice(Berries.CHORUS_BERRIES, key.getValue());
            Identifier id = Bodaciousberries.id("chorus_berry_juice_" + key.getValue().getPath());

            RECIPES.add(JuicerRecipes.createShapelessJson(new JuicerRecipes.Id(Registry.ITEM.getId(biomeItems[i]), false), id));

            Registry.register(Registry.ITEM, id, juice);
        }
    }

    private static void registerBlends() {
        Blend gojiBerryBlend = new GojiBerryBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(6.0F).statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 800, 1), 1.0F).build()), Berries.GOJI_BERRIES, Berries.GOJI_BERRIES, Items.SUGAR_CANE);
        registerBlend("goji_berry_blend", gojiBerryBlend);
        Blend oppositeJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(5.0F).build()), Berries.RASPBERRIES, Berries.BLACKBERRIES, Items.SUGAR_CANE);
        registerBlend("opposite_juice", oppositeJuice);
        Blend rainberryBlend = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(8).saturationModifier(10.0F).build()), Berries.RAINBERRY, Berries.GOJI_BERRIES, Items.GOLDEN_APPLE);
        registerBlend("rainberry_blend", rainberryBlend);
        Blend gooseberryRum = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturationModifier(4.0f).build()), Berries.GOOSEBERRIES, Berries.GOOSEBERRIES, Items.WHEAT);
        registerBlend("gooseberry_rum", gooseberryRum);
        Blend redJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(6.0F).build()), Berries.STRAWBERRY, Items.SWEET_BERRIES, Berries.LINGONBERRIES);
        registerBlend("red_juice", redJuice);
        Blend endBlend = new EndBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(3).saturationModifier(8.0F).build()), Berries.CHORUS_BERRIES, Berries.RAINBERRY, Items.CHORUS_FRUIT);
        registerBlend("end_blend", endBlend);
        Blend purpleDelight = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturationModifier(6.0F).build()), Berries.CHORUS_BERRIES, Berries.GRAPES, Berries.SASKATOON_BERRIES);
        registerBlend("purple_delight", purpleDelight);
        Blend trafficLightJuice = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(8.0F).build()), Berries.GOOSEBERRIES, Items.GLOW_BERRIES, Berries.RASPBERRIES);
        registerBlend("traffic_light_juice", trafficLightJuice);
        Blend vanillaDelight = new Blend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(5.0F).build()), Items.GLOW_BERRIES, Items.SWEET_BERRIES, Items.APPLE);
        registerBlend("vanilla_delight", vanillaDelight);
    }

    private static void register(String name, Juice juice) {
        Identifier id = Bodaciousberries.id(name);
        JuicerRecipes.addJuiceRecipeByTag(new Identifier("c", Registry.ITEM.getId(juice.getBerry()).getPath()), id);
        register(id, juice);
    }

    private static void registerBlend(String name, Blend blend) {
        Identifier id = Bodaciousberries.id(name);
        register(id, blend);
        JuicerRecipes.addJuiceRecipe(new JuicerRecipes.Id(Registry.ITEM.getId(blend.getIngredient0()), false), new JuicerRecipes.Id(Registry.ITEM.getId(blend.getIngredient1()), false), new JuicerRecipes.Id(Registry.ITEM.getId(blend.getIngredient2()), false), id);
    }

    private static void register(Identifier id, Juice juice) {
        Registry.register(Registry.ITEM, id, juice);
    }
}
