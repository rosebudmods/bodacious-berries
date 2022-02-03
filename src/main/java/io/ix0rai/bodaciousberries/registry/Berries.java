package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.registry.items.ChorusBerries;
import io.ix0rai.bodaciousberries.registry.items.Rainberry;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static net.fabricmc.fabric.impl.content.registry.CompostingChanceRegistryImpl.INSTANCE;

public class Berries {
    //berry items
    public static Item SASKATOON_BERRIES;
    public static Item STRAWBERRY;
    public static Item RASPBERRIES;
    public static Item BLACKBERRIES;
    public static Item CHORUS_BERRIES;
    public static Item RAINBERRY;
    public static Item LINGONBERRIES;
<<<<<<< Updated upstream:src/main/java/io/ix0rai/bodaciousberries/registry/Berries.java
    public static Item GRAPES;
=======
    public static Item GOOSEBERRIES;
>>>>>>> Stashed changes:src/main/java/io/ix0rai/bodaciousberries/registry/items/Berries.java

    public static void registerBerries() {
        //create items for each berry
        SASKATOON_BERRIES = new AliasedBlockItem(Bushes.SASKATOON_BERRY_BUSH, settings(2, 2f));
        STRAWBERRY = new AliasedBlockItem(Bushes.STRAWBERRY_BUSH, settings(3, 1.5f));
        RASPBERRIES = new AliasedBlockItem(Bushes.RASPBERRY_BUSH, settings(1, 4f));
        BLACKBERRIES = new AliasedBlockItem(Bushes.BLACKBERRY_BUSH, settings(1, 3.5f));
        CHORUS_BERRIES = new ChorusBerries(Bushes.CHORUS_BERRY_BUSH, settings(1, 2f));
        RAINBERRY = new Rainberry(Bushes.RAINBERRY_BUSH, new Item.Settings().group(ItemGroup.MATERIALS));
        LINGONBERRIES = new AliasedBlockItem(Bushes.LINGONBERRY_BUSH, settings(2, 1.5f));
<<<<<<< Updated upstream:src/main/java/io/ix0rai/bodaciousberries/registry/Berries.java
        GRAPES = new AliasedBlockItem(Bushes.GRAPEVINE, settings(1, 2f));
=======
        GOOSEBERRIES = new Item(settings(2, 1.5f));
>>>>>>> Stashed changes:src/main/java/io/ix0rai/bodaciousberries/registry/items/Berries.java

        //automatic stuffs
        Berries.addDoubleBushToList(Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, SASKATOON_BERRIES);
        Berries.addToList(Bushes.STRAWBERRY_BUSH, STRAWBERRY);
        Berries.addToList(Bushes.RASPBERRY_BUSH, RASPBERRIES);
        Berries.addToList(Bushes.BLACKBERRY_BUSH, BLACKBERRIES);
        Berries.addToList(Bushes.CHORUS_BERRY_BUSH, CHORUS_BERRIES);
        Berries.addToList(Bushes.RAINBERRY_BUSH, RAINBERRY);
        Berries.addToList(Bushes.LINGONBERRY_BUSH, LINGONBERRIES);
        Berries.addToList(Bushes.GRAPEVINE, GRAPES);


        Berries.initialiseBerries();

        //register
        register("saskatoon_berries", SASKATOON_BERRIES);
        register("strawberry", STRAWBERRY);
        register("raspberries", RASPBERRIES);
        register("blackberries", BLACKBERRIES);
        register("chorus_berries", CHORUS_BERRIES);
        register("rainberry", RAINBERRY);
        register("lingonberries", LINGONBERRIES);
<<<<<<< Updated upstream:src/main/java/io/ix0rai/bodaciousberries/registry/Berries.java
        register("grapes", GRAPES);
=======
        register("gooseberries", GOOSEBERRIES);
>>>>>>> Stashed changes:src/main/java/io/ix0rai/bodaciousberries/registry/items/Berries.java
    }

    /**
     * map containing a berry bush, its associated berry, and unripe form if applicable
     * <br> should not be directly added to, use {@link #addToList(BerryBush, Item)}
     */
    private static final Map<BerryBush, Item> BERRY_BUSHES = new HashMap<>();

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
    public static void addToList(BerryBush bush, Item berries) {
        BERRY_BUSHES.put(bush, berries);
    }

    /**
     * uses {@link #addToList(BerryBush, Item)} to add both forms of a double berry bush to the automatic registration list
     * @param smallBush the small form of the berry bush
     * @param bigBush the double form of the berry bush
     * @param berries the base form of the berries to associate
     */
    public static void addDoubleBushToList(BerryBush smallBush, DoubleBerryBush bigBush, Item berries) {
        BERRY_BUSHES.put(smallBush, berries);
        BERRY_BUSHES.put(bigBush, berries);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
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
            //set berry types
            Item berryType = entry.getValue();
            bush.setBerryType(berryType);

            //register as compostable
            registerCompostableBerry(berryType);
        }
    }
}
