package io.ix0rai.bodaciousberries.registry.items;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

import static net.fabricmc.fabric.impl.content.registry.CompostingChanceRegistryImpl.INSTANCE;

public class Berries {
    //berry items
    public static Item SASKATOON_BERRIES;
    public static Item UNRIPE_SASKATOON_BERRIES;
    public static Item STRAWBERRY;
    public static Item UNRIPE_STRAWBERRY;
    public static Item RASPBERRIES;
    public static Item BLACKBERRIES;
    public static Item CHORUS_BERRIES;
    public static Item WHITE_RAINBOWBERRIES;
    public static Item ORANGE_RAINBOWBERRIES;
    public static Item MAGENTA_RAINBOWBERRIES;
    public static Item LIGHT_BLUE_RAINBOWBERRIES;
    public static Item YELLOW_RAINBOWBERRIES;
    public static Item LIME_RAINBOWBERRIES;
    public static Item PINK_RAINBOWBERRIES;
    public static Item GRAY_RAINBOWBERRIES;
    public static Item LIGHT_GRAY_RAINBOWBERRIES;
    public static Item CYAN_RAINBOWBERRIES;
    public static Item PURPLE_RAINBOWBERRIES;
    public static Item BLUE_RAINBOWBERRIES;
    public static Item BROWN_RAINBOWBERRIES;
    public static Item GREEN_RAINBOWBERRIES;
    public static Item RED_RAINBOWBERRIES;
    public static Item BLACK_RAINBOWBERRIES;

    public static void registerBerries() {
        //create items for each berry
        SASKATOON_BERRIES = new AliasedBlockItem(Bushes.SASKATOON_BERRY_BUSH, settings(2, 2f));
        UNRIPE_SASKATOON_BERRIES = new Item(settings(1, 2f));
        STRAWBERRY = new AliasedBlockItem(Bushes.STRAWBERRY_BUSH, settings(3, 1.5f));
        UNRIPE_STRAWBERRY = new Item(settings(1, 1.5f));
        RASPBERRIES = new AliasedBlockItem(Bushes.RASPBERRY_BUSH, settings(1, 4f));
        BLACKBERRIES = new AliasedBlockItem(Bushes.BLACKBERRY_BUSH, settings(1, 3.5f));
        CHORUS_BERRIES = new ChorusBerries(Bushes.CHORUS_BERRY_BUSH, settings(1, 2f));
        WHITE_RAINBOWBERRIES = rainbowberries();
        ORANGE_RAINBOWBERRIES = rainbowberries();
        MAGENTA_RAINBOWBERRIES = rainbowberries();
        LIGHT_BLUE_RAINBOWBERRIES = rainbowberries();
        YELLOW_RAINBOWBERRIES = rainbowberries();
        LIME_RAINBOWBERRIES = rainbowberries();
        PINK_RAINBOWBERRIES = rainbowberries();
        GRAY_RAINBOWBERRIES = rainbowberries();
        LIGHT_GRAY_RAINBOWBERRIES = rainbowberries();
        CYAN_RAINBOWBERRIES = rainbowberries();
        PURPLE_RAINBOWBERRIES = rainbowberries();
        BLUE_RAINBOWBERRIES = rainbowberries();
        BROWN_RAINBOWBERRIES = rainbowberries();
        GREEN_RAINBOWBERRIES = rainbowberries();
        RED_RAINBOWBERRIES = rainbowberries();
        BLACK_RAINBOWBERRIES = rainbowberries();

        //automatic stuffs
        Berries.addDoubleBushToList(Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, SASKATOON_BERRIES, UNRIPE_SASKATOON_BERRIES);
        Berries.addToList(Bushes.STRAWBERRY_BUSH, STRAWBERRY, UNRIPE_STRAWBERRY);
        Berries.addToList(Bushes.RASPBERRY_BUSH, RASPBERRIES);
        Berries.addToList(Bushes.BLACKBERRY_BUSH, BLACKBERRIES);
        Berries.addDoubleBushToList(Bushes.CHORUS_BERRY_BUSH, Bushes.DOUBLE_CHORUS_BERRY_BUSH, CHORUS_BERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, WHITE_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, ORANGE_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, MAGENTA_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, LIGHT_BLUE_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, YELLOW_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, LIME_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, PINK_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, GRAY_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, LIGHT_GRAY_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, CYAN_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, PURPLE_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, BLUE_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, BROWN_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, GREEN_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, RED_RAINBOWBERRIES);
        Berries.addToList(Bushes.RAINBOWBERRY_BUSH, BLACK_RAINBOWBERRIES);

        Berries.initialiseBerries();

        //register
        register("saskatoon_berries", SASKATOON_BERRIES);
        register("unripe_saskatoon_berries", UNRIPE_SASKATOON_BERRIES);
        register("strawberry", STRAWBERRY);
        register("unripe_strawberry", UNRIPE_STRAWBERRY);
        register("raspberries", RASPBERRIES);
        register("blackberries", BLACKBERRIES);
        register("chorus_berries", CHORUS_BERRIES);
        register("white_rainbowberries", WHITE_RAINBOWBERRIES);
        register("orange_rainbowberries", ORANGE_RAINBOWBERRIES);
        register("magenta_rainbowberries", MAGENTA_RAINBOWBERRIES);
        register("light_blue_rainbowberries", LIGHT_BLUE_RAINBOWBERRIES);
        register("yellow_rainbowberries", YELLOW_RAINBOWBERRIES);
        register("lime_rainbowberries", LIME_RAINBOWBERRIES);
        register("pink_rainbowberries", PINK_RAINBOWBERRIES);
        register("gray_rainbowberries", GRAY_RAINBOWBERRIES);
        register("light_gray_rainbowberries", LIGHT_GRAY_RAINBOWBERRIES);
        register("cyan_rainbowberries", CYAN_RAINBOWBERRIES);
        register("purple_rainbowberries", PURPLE_RAINBOWBERRIES);
        register("blue_rainbowberries", BLUE_RAINBOWBERRIES);
        register("brown_rainbowberries", BROWN_RAINBOWBERRIES);
        register("green_rainbowberries", GREEN_RAINBOWBERRIES);
        register("red_rainbowberries", RED_RAINBOWBERRIES);
        register("black_rainbowberries", BLACK_RAINBOWBERRIES);
    }

    /**
     * map containing a berry bush, its associated berry, and unripe form if applicable
     * <br> should not be directly added to, use {@link #addToList(BerryBush, Item, Item)}
     */
    private static final Map<BerryBush, Pair<Item, Item>> BERRY_BUSHES = new HashMap<>();

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
     * registers two berries to be compostable at once using {@link #registerCompostableBerry(Item)}
     * @param berry1 the first berry to be made compostable
     * @param berry2 the second berry to be made compostable
     */
    private static void registerCompostableBerries(Item berry1, Item berry2) {
        registerCompostableBerry(berry1);
        registerCompostableBerry(berry2);
    }

    /**
     * add a berry bush to the automatic registration list
     * @param bush the berry bush you wish to associate your berries with
     * @param berries the base form of the berries to associate
     * @param unripeBerries the unripe form of the berries to associate
     */
    public static void addToList(BerryBush bush, Item berries, Item unripeBerries) {
        BERRY_BUSHES.put(bush, new Pair<>(berries, unripeBerries));
    }

    /**
     * add a berry bush to the automatic registration list
     * @param bush the berry bush you wish to associate your berries with
     * @param berries the base form of the berries to associate
     */
    public static void addToList(BerryBush bush, Item berries) {
        addToList(bush, berries, null);
    }

    /**
     * uses {@link #addToList(BerryBush, Item, Item)} to add both forms of a double berry bush to the automatic registration list
     * @param smallBush the small form of the berry bush
     * @param bigBush the double form of the berry bush
     * @param berries the base form of the berries to associate
     * @param unripeBerries the unripe form of the berries to associate
     */
    public static void addDoubleBushToList(BerryBush smallBush, DoubleBerryBush bigBush, Item berries, Item unripeBerries) {
        var berryPair = new Pair<>(berries, unripeBerries);
        BERRY_BUSHES.put(smallBush, berryPair);
        BERRY_BUSHES.put(bigBush, berryPair);
    }

    /**
     * uses {@link #addDoubleBushToList(BerryBush, DoubleBerryBush, Item, Item)} to add both forms of a double berry bush to the automatic registration list
     * @param smallBush the small form of the berry bush
     * @param bigBush the double form of the berry bush
     * @param berries the base form of the berries to associate
     */
    public static void addDoubleBushToList(BerryBush smallBush, DoubleBerryBush bigBush, Item berries) {
        addDoubleBushToList(smallBush, bigBush, berries, null);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
    }

    private static Item.Settings settings(int hunger, float saturation) {
        return new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).snack().build());
    }

    private static Item rainbowberries() {
        return new AliasedBlockItem(Bushes.RAINBOWBERRY_BUSH, settings(2, 1.5f));
    }

    /**
     * sets the berry type of each bush in the list and registers all berries as compostable
     */
    public static void initialiseBerries() {
        for (var entry : BERRY_BUSHES.entrySet()) {
            BerryBush bush = entry.getKey();
            //set berry types
            Item berryType = entry.getValue().getA();
            bush.setBerryType(berryType);
            //honestly this probably isn't an optimisation but :hahayes:
            Item unripeBerryType = entry.getValue().getB();
            if (unripeBerryType != null) {
                bush.setUnripeBerryType(unripeBerryType);
            }

            //register as compostable
            if (unripeBerryType != null) {
                registerCompostableBerries(entry.getValue().getA(), unripeBerryType);
            } else {
                registerCompostableBerry(berryType);
            }
        }
    }
}
