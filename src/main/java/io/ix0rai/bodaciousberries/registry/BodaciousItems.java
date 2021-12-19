package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.registry.berries.Berries;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BodaciousItems {
    //berry items
    public static Item SASKATOON_BERRIES;
    public static Item UNRIPE_SASKATOON_BERRIES;
    public static Item STRAWBERRY;
    public static Item UNRIPE_STRAWBERRY;
    public static Item RASPBERRIES;
    public static Item BLACKBERRIES;

    public static void registerItems() {
        //create items for each berry
        SASKATOON_BERRIES = new AliasedBlockItem(Bushes.SASKATOON_BERRY_BUSH, settings(2, 2f));
        UNRIPE_SASKATOON_BERRIES = new Item(settings(1, 2f));
        STRAWBERRY = new AliasedBlockItem(Bushes.STRAWBERRY_BUSH, settings(3, 1.5f));
        UNRIPE_STRAWBERRY = new Item(settings(1, 1.5f));
        RASPBERRIES = new AliasedBlockItem(Bushes.RASPBERRY_BUSH, settings(1, 4f));
        BLACKBERRIES = new AliasedBlockItem(Bushes.BLACKBERRY_BUSH, settings(1, 3.5f));

        //automatic stuffs
        Berries.addDoubleBushToList(Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, SASKATOON_BERRIES, UNRIPE_SASKATOON_BERRIES);
        Berries.addToList(Bushes.STRAWBERRY_BUSH, STRAWBERRY, UNRIPE_STRAWBERRY);
        Berries.addToList(Bushes.RASPBERRY_BUSH, RASPBERRIES);
        Berries.addToList(Bushes.BLACKBERRY_BUSH, BLACKBERRIES);
        Berries.initialiseBerries();

        //register
        register("saskatoon_berries", SASKATOON_BERRIES);
        register("unripe_saskatoon_berries", UNRIPE_SASKATOON_BERRIES);
        register("strawberry", STRAWBERRY);
        register("unripe_strawberry", UNRIPE_STRAWBERRY);
        register("raspberries", RASPBERRIES);
        register("blackberries", BLACKBERRIES);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
    }

    private static Item.Settings settings(int hunger, float saturation) {
        return new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).snack().build());
    }
}
