package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.BodaciousBerries;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import io.ix0rai.bodaciousberries.item.ChorusBerries;
import io.ix0rai.bodaciousberries.item.GojiBerries;
import io.ix0rai.bodaciousberries.item.Rainberry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static net.fabricmc.fabric.impl.content.registry.CompostingChanceRegistryImpl.INSTANCE;

public class Berries {
    public static final TagKey<Item> BERRY_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "berries"));

    private static final Item.Settings berrySettings = new Item.Settings().group(ItemGroup.FOOD);

    // berry items
    public static AliasedBlockItem saskatoonBerries;
    public static AliasedBlockItem strawberry;
    public static AliasedBlockItem raspberries;
    public static AliasedBlockItem blackberries;
    public static AliasedBlockItem chorusBerries;
    public static AliasedBlockItem rainberry;
    public static AliasedBlockItem lingonberries;
    public static AliasedBlockItem grapes;
    public static AliasedBlockItem gojiBerries;
    public static AliasedBlockItem gooseberries;
    public static AliasedBlockItem cloudberries;

    public static void registerBerries() {
        // create items for each berry
        saskatoonBerries = new AliasedBlockItem(Bushes.SASKATOON_BERRY_BUSH, settings(3, 2f));
        strawberry = new AliasedBlockItem(Bushes.STRAWBERRY_BUSH, settings(3, 1.5f));
        raspberries = new AliasedBlockItem(Bushes.RASPBERRY_BUSH, settings(2, 3f));
        blackberries = new AliasedBlockItem(Bushes.BLACKBERRY_BUSH, settings(2, 3.5f));
        chorusBerries = new ChorusBerries(Bushes.CHORUS_BERRY_BUSH, settings(2, 2.5f));
        rainberry = new Rainberry(Bushes.RAINBERRY_BUSH, new Item.Settings().group(ItemGroup.MATERIALS));
        lingonberries = new AliasedBlockItem(Bushes.LINGONBERRY_BUSH, settings(2, 1.5f));
        grapes = new AliasedBlockItem(Bushes.GRAPEVINE, settings(2, 2f));
        gojiBerries = new GojiBerries(Bushes.GOJI_BERRY_BUSH, settings(2, 2.5f));
        gooseberries = new AliasedBlockItem(Bushes.GOOSEBERRY_BUSH, settings(2, 1f));
        cloudberries = new AliasedBlockItem(Bushes.CLOUDBERRY_BUSH, berrySettings.food(new FoodComponent.Builder().hunger(2).saturationModifier(1.5f).statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, 1), 1).snack().build()));

        initialiseBerries();

        // register
        register(saskatoonBerries, "saskatoon_berries");
        register(strawberry, "strawberry");
        register(raspberries, "raspberries");
        register(blackberries, "blackberries");
        register(chorusBerries, "chorus_berries");
        register(rainberry, "rainberry");
        register(lingonberries, "lingonberries");
        register(grapes, "grapes");
        register(gojiBerries, "goji_berries");
        register(gooseberries, "gooseberries");
        register(cloudberries, "cloudberries");
    }

    /**
     * map containing a berry bush, its associated berry, and unripe form if applicable
     * <br> should not be directly added to, use {@link #initialise(BerryBush, Item)}
     */
    public static final Map<BerryBush, Item> BERRY_BUSHES = new HashMap<>();

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
        INSTANCE.add(berry, BERRY_COMPOST_LEVEL_INCREASE_CHANCE);
    }

    /**
     * add a berry bush to the automatic registration list
     * @param bush the berry bush you wish to associate your berries with
     * @param berries the base form of the berries to associate
     */
    public static void initialise(BerryBush bush, Item berries) {
        BERRY_BUSHES.put(bush, berries);
    }

    /**
     * uses {@link #initialise(BerryBush, Item)} to add both forms of a double berry bush to the automatic registration list
     * @param smallBush the small form of the berry bush
     * @param bigBush the double form of the berry bush
     * @param berries the base form of the berries to associate
     */
    public static void initialise(GrowingBerryBush smallBush, DoubleBerryBush bigBush, Item berries) {
        BERRY_BUSHES.put(smallBush, berries);
        BERRY_BUSHES.put(bigBush, berries);
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
        initialise(Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, saskatoonBerries);
        initialise(Bushes.STRAWBERRY_BUSH, strawberry);
        initialise(Bushes.RASPBERRY_BUSH, raspberries);
        initialise(Bushes.BLACKBERRY_BUSH, blackberries);
        initialise(Bushes.CHORUS_BERRY_BUSH, chorusBerries);
        initialise(Bushes.RAINBERRY_BUSH, rainberry);
        initialise(Bushes.LINGONBERRY_BUSH, lingonberries);
        initialise(Bushes.GRAPEVINE, grapes);
        initialise(Bushes.GOJI_BERRY_BUSH, Bushes.DOUBLE_GOJI_BERRY_BUSH, gojiBerries);
        initialise(Bushes.GOOSEBERRY_BUSH, gooseberries);
        initialise(Bushes.CLOUDBERRY_BUSH, cloudberries);

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
