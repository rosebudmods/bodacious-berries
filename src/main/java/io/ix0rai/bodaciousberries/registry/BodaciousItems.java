package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BodaciousItems {
    //berry items
    public static Item SASKATOON_BERRIES;
    public static Item UNRIPE_SASKATOON_BERRIES;
    public static Item STRAWBERRIES;
    public static Item RASPBERRIES;
    public static Item BLACKBERRIES;

    public static void registerItems() {
        //create items for each berry
        SASKATOON_BERRIES = new AliasedBlockItem(Bushes.SASKATOON_BERRY_BUSH, new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(2).saturationModifier(2f).snack().build()));
        UNRIPE_SASKATOON_BERRIES = new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(2f).snack().build()));
        STRAWBERRIES = new AliasedBlockItem(Bushes.STRAWBERRY_BUSH, new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(1.5f).snack().build()));
        RASPBERRIES = new AliasedBlockItem(Bushes.RASPBERRY_BUSH, new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(4f).snack().build()));
        BLACKBERRIES = new AliasedBlockItem(Bushes.BLACKBERRY_BUSH, new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(3.5f).snack().build()));

        //automatic stuffs
        Berries.addDoubleBushToList(Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, SASKATOON_BERRIES, UNRIPE_SASKATOON_BERRIES);
        Berries.addToList(Bushes.STRAWBERRY_BUSH, STRAWBERRIES, null);
        Berries.addToList(Bushes.RASPBERRY_BUSH, RASPBERRIES, null);
        Berries.addToList(Bushes.BLACKBERRY_BUSH, BLACKBERRIES, null);
        Berries.initialiseBerries();

        //register
        register("saskatoon_berries", SASKATOON_BERRIES);
        register("unripe_saskatoon_berries", UNRIPE_SASKATOON_BERRIES);
        register("strawberries", STRAWBERRIES);
        register("raspberries", RASPBERRIES);
        register("blackberries", BLACKBERRIES);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
    }
}
