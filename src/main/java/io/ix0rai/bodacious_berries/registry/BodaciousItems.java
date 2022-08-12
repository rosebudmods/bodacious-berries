package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.BerryBush;
import io.ix0rai.bodacious_berries.block.DoubleBerryBush;
import io.ix0rai.bodacious_berries.block.GrowingBerryBush;
import io.ix0rai.bodacious_berries.item.ChorusBerries;
import io.ix0rai.bodacious_berries.item.GojiBerries;
import io.ix0rai.bodacious_berries.item.Rainberries;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodaciousItems {
    public static final TagKey<Item> BERRY_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "berries"));

    private static final Item.Settings berrySettings = new Item.Settings().group(ItemGroup.FOOD);

    // berry items
    public static AliasedBlockItem saskatoonBerries;
    public static AliasedBlockItem strawberries;
    public static AliasedBlockItem raspberries;
    public static AliasedBlockItem blackberries;
    public static AliasedBlockItem chorusBerries;
    public static AliasedBlockItem rainberries;
    public static AliasedBlockItem lingonberries;
    public static AliasedBlockItem grapes;
    public static AliasedBlockItem gojiBerries;
    public static AliasedBlockItem gooseberries;
    public static AliasedBlockItem cloudberries;

    public static void register() {
        // create items for each berry
        saskatoonBerries = new AliasedBlockItem(BodaciousBushes.SASKATOON_BERRY_BUSH, settings(3, 0.5f));
        strawberries = new AliasedBlockItem(BodaciousBushes.STRAWBERRY_BUSH, settings(3, 1f));
        raspberries = new AliasedBlockItem(BodaciousBushes.RASPBERRY_BUSH, settings(3, 0.5f));
        blackberries = new AliasedBlockItem(BodaciousBushes.BLACKBERRY_BUSH, settings(1, 1.5f));
        chorusBerries = new ChorusBerries(BodaciousBushes.CHORUS_BERRY_BUSH, settings(2, 2.5f));
        rainberries = new Rainberries(BodaciousBushes.RAINBERRY_BUSH, new Item.Settings().group(ItemGroup.MATERIALS));
        lingonberries = new AliasedBlockItem(BodaciousBushes.LINGONBERRY_BUSH, settings(2, 0.5f));
        grapes = new AliasedBlockItem(BodaciousBushes.GRAPEVINE, settings(2, 1f));
        gojiBerries = new GojiBerries(BodaciousBushes.GOJI_BERRY_BUSH, settings(1, 2.5f));
        gooseberries = new AliasedBlockItem(BodaciousBushes.GOOSEBERRY_BUSH, settings(2, 0.5f));
        cloudberries = new AliasedBlockItem(BodaciousBushes.CLOUDBERRY_BUSH, berrySettings.food(new FoodComponent.Builder().hunger(2).saturationModifier(1f).statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, 1), 1).snack().build()));

        associate(BodaciousBushes.SASKATOON_BERRY_BUSH, BodaciousBushes.DOUBLE_SASKATOON_BERRY_BUSH, saskatoonBerries);
        associate(BodaciousBushes.STRAWBERRY_BUSH, strawberries);
        associate(BodaciousBushes.RASPBERRY_BUSH, raspberries);
        associate(BodaciousBushes.BLACKBERRY_BUSH, blackberries);
        associate(BodaciousBushes.CHORUS_BERRY_BUSH, chorusBerries);
        associate(BodaciousBushes.RAINBERRY_BUSH, rainberries);
        associate(BodaciousBushes.LINGONBERRY_BUSH, lingonberries);
        associate(BodaciousBushes.GRAPEVINE, grapes);
        associate(BodaciousBushes.GOJI_BERRY_BUSH, BodaciousBushes.DOUBLE_GOJI_BERRY_BUSH, gojiBerries);
        associate(BodaciousBushes.GOOSEBERRY_BUSH, gooseberries);
        associate(BodaciousBushes.CLOUDBERRY_BUSH, cloudberries);

        excludeFromColourProvider(BodaciousBushes.CLOUDBERRY_BUSH);
        excludeFromColourProvider(BodaciousBushes.CHORUS_BERRY_BUSH);
        excludeFromColourProvider(BodaciousBushes.RAINBERRY_BUSH);

        initialiseBerries();

        // register
        register(saskatoonBerries, "saskatoon_berries");
        register(strawberries, "strawberries");
        register(raspberries, "raspberries");
        register(blackberries, "blackberries");
        register(chorusBerries, "chorus_berries");
        register(rainberries, "rainberries");
        register(lingonberries, "lingonberries");
        register(grapes, "grapes");
        register(gojiBerries, "goji_berries");
        register(gooseberries, "gooseberries");
        register(cloudberries, "cloudberries");
    }

    /**
     * map containing a berry bush, its associated berry, and unripe form if applicable
     * <br> should not be directly added to, use {@link #associate(BerryBush, Item)}
     */
    public static final Map<BerryBush, Item> BERRY_BUSHES = new HashMap<>();

    public static final List<BerryBush> COLOUR_PROVIDER_EXCLUDED = new ArrayList<>();

    /**
     * the chance that a berry will raise the level of compost in a composter
     * <br> out of one: 0.4 = 40%, etc
     */
    private static final float BERRY_COMPOST_LEVEL_INCREASE_CHANCE = 0.4f;

    /**
     * makes a berry item compostable via composter
     * @param berry the berry to make compostable
     */
    private static void registerCompostableBerry(Item berry) {
        CompostingChanceRegistry.INSTANCE.add(berry, BERRY_COMPOST_LEVEL_INCREASE_CHANCE);
    }

    /**
     * add a berry bush to the automatic registration list
     * @param bush the berry bush you wish to associate your berries with
     * @param berries the base form of the berries to associate
     */
    public static void associate(BerryBush bush, Item berries) {
        BERRY_BUSHES.put(bush, berries);
    }

    /**
     * uses {@link #associate(BerryBush, Item)} to add both forms of a double berry bush to the automatic registration list
     * @param smallBush the small form of the berry bush
     * @param bigBush the double form of the berry bush
     * @param berries the base form of the berries to associate
     */
    public static void associate(GrowingBerryBush smallBush, DoubleBerryBush bigBush, Item berries) {
        BERRY_BUSHES.put(smallBush, berries);
        BERRY_BUSHES.put(bigBush, berries);
    }

    public static void excludeFromColourProvider(BerryBush bush) {
        COLOUR_PROVIDER_EXCLUDED.add(bush);
    }

    private static void register(Item berry, String name) {
        Registry.register(Registry.ITEM, BodaciousBerries.id(name), berry);
    }

    private static Item.Settings settings(int hunger, float saturation) {
        return new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).snack().build());
    }

    /**
     * sets the berry type of each bush in the list and registers all berries as compostable
     */
    public static void initialiseBerries() {
        for (var entry : BERRY_BUSHES.entrySet()) {
            BerryBush bush = entry.getKey();
            // set berry types
            Item berryType = entry.getValue();
            bush.setBerryType(berryType);

            // register as compostable
            registerCompostableBerry(berryType);
        }
    }
}
