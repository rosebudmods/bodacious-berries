package io.ix0rai.bodaciousberries.registry;

import com.google.gson.JsonObject;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import io.ix0rai.bodaciousberries.item.ChorusBerryJuice;
import io.ix0rai.bodaciousberries.item.EndJuice;
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
import net.minecraft.world.biome.Biome;

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

        registerSimple("dubious_juice", DUBIOUS_JUICE);
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

        Juice gojiBerryBlend = new GojiBerryBlend(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(6.0F).statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 800, 1), 1.0F).build()));
        registerSimple("goji_berry_blend", gojiBerryBlend);
        JuicerRecipes.addJuiceRecipe(Berries.GOJI_BERRIES, Berries.GOJI_BERRIES, Items.SUGAR_CANE, gojiBerryBlend);
        Juice oppositeJuice = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(5.0F).build()));
        registerSimple("opposite_juice", oppositeJuice);
        JuicerRecipes.addJuiceRecipe(Berries.RASPBERRIES, Berries.BLACKBERRIES, Items.SUGAR_CANE, oppositeJuice);
        Juice rainberryBlend = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(8).saturationModifier(10.0F).build()));
        registerSimple("rainberry_blend", rainberryBlend);
        JuicerRecipes.addJuiceRecipe(Berries.RAINBERRY, Berries.GOJI_BERRIES, Items.GOLDEN_APPLE, rainberryBlend);
        Juice gooseberryRum = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturationModifier(4.0f).build()));
        registerSimple("gooseberry_rum", gooseberryRum);
        JuicerRecipes.addJuiceRecipe(Berries.GOOSEBERRIES, Berries.GOOSEBERRIES, Items.WHEAT, gooseberryRum);
        Juice redJuice = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(6.0F).build()));
        registerSimple("red_juice", redJuice);
        JuicerRecipes.addJuiceRecipe(Berries.STRAWBERRY, Items.SWEET_BERRIES, Berries.LINGONBERRIES, redJuice);
        Juice endBlend = new EndJuice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(3).saturationModifier(8.0F).build()));
        registerSimple("end_blend", endBlend);
        JuicerRecipes.addJuiceRecipe(Berries.CHORUS_BERRIES, Berries.RAINBERRY, Items.CHORUS_FRUIT, endBlend);
        Juice purpleDelight = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(7).saturationModifier(6.0F).build()));
        registerSimple("purple_delight", purpleDelight);
        JuicerRecipes.addJuiceRecipe(Berries.CHORUS_BERRIES, Berries.GRAPES, Berries.SASKATOON_BERRIES, purpleDelight);
        Juice trafficLightJuice = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(6).saturationModifier(8.0F).build()));
        registerSimple("traffic_light_juice", trafficLightJuice);
        JuicerRecipes.addJuiceRecipe(Berries.GOOSEBERRIES, Items.GLOW_BERRIES, Berries.RASPBERRIES, trafficLightJuice);
        Juice vanillaDelight = new Juice(JUICE_SETTINGS.food(new FoodComponent.Builder().hunger(5).saturationModifier(5.0F).build()));
        registerSimple("vanilla_delight", vanillaDelight);
        JuicerRecipes.addJuiceRecipe(Items.GLOW_BERRIES, Items.SWEET_BERRIES, Items.APPLE, vanillaDelight);

        List<RegistryKey<Biome>> biomes = List.of(
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                LUSH_CAVES, DRIPSTONE_CAVES, JUNGLE
        );

        List<Item> biomeItems = List.of(
                Items.CORNFLOWER, Items.SNOWBALL, Items.CLAY_BALL,
                Items.SAND, Items.SWEET_BERRIES, Items.BIRCH_SAPLING,
                Items.COD, Items.RED_MUSHROOM, Items.SUNFLOWER,
                Items.OAK_SAPLING, Items.PEONY, Items.DARK_OAK_SAPLING,
                Items.ACACIA_SAPLING, Items.RED_SAND, Items.POPPY,
                Items.GLOW_BERRIES, Items.POINTED_DRIPSTONE, Items.COCOA_BEANS
        );

        for (int i = 0; i < biomes.size(); i++) {
            RegistryKey<Biome> key = biomes.get(i);
            ChorusBerryJuice juice = new ChorusBerryJuice(Berries.CHORUS_BERRIES, key.getValue());
            Identifier id = Bodaciousberries.getIdentifier("chorus_berry_juice_" + key.getValue().getPath());

            RECIPES.add(JuicerRecipes.createShapelessJson(Registry.ITEM.getId(biomeItems.get(i)), id));

            Registry.register(Registry.ITEM, id, juice);
        }
    }

    public static void register(String name, Juice juice) {
        Identifier id = Bodaciousberries.getIdentifier(name);
        JuicerRecipes.addJuiceRecipe(Registry.ITEM.getId(juice.getBerry()), id);
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), juice);
    }

    public static void registerSimple(String name, Juice juice) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), juice);
    }
}
