package io.ix0rai.bodacious_berries.registry;

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

public class BodaciousItems {
    public static final TagKey<Item> BERRY_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "berries"));

    private static final Item.Settings berrySettings = new Item.Settings().group(ItemGroup.FOOD);

    // berry items
    public static final AliasedBlockItem SASKATOON_BERRIES = new AliasedBlockItem(BodaciousBushes.SASKATOON_BERRY_BUSH, settings(3, 0.5f));
    public static final AliasedBlockItem STRAWBERRIES = new AliasedBlockItem(BodaciousBushes.STRAWBERRY_BUSH, settings(3, 1f));
    public static final AliasedBlockItem RASPBERRIES = new AliasedBlockItem(BodaciousBushes.RASPBERRY_BUSH, settings(3, 0.5f));
    public static final AliasedBlockItem BLACKBERRIES = new AliasedBlockItem(BodaciousBushes.BLACKBERRY_BUSH, settings(1, 1.5f));
    public static final AliasedBlockItem CHORUS_BERRIES = new ChorusBerries(BodaciousBushes.CHORUS_BERRY_BUSH, settings(2, 1.6f));
    public static final AliasedBlockItem RAINBERRIES = new Rainberries(BodaciousBushes.RAINBERRY_BUSH, new Item.Settings().group(ItemGroup.MATERIALS));
    public static final AliasedBlockItem LINGONBERRIES = new AliasedBlockItem(BodaciousBushes.LINGONBERRY_BUSH, settings(2, 0.5f));
    public static final AliasedBlockItem GRAPES = new AliasedBlockItem(BodaciousBushes.GRAPEVINE, settings(2, 1f));
    public static final AliasedBlockItem GOJI_BERRIES = new GojiBerries(BodaciousBushes.GOJI_BERRY_BUSH, settings(1, 2.2f));
    public static final AliasedBlockItem GOOSEBERRIES = new AliasedBlockItem(BodaciousBushes.GOOSEBERRY_BUSH, settings(2, 0.5f));
    public static final AliasedBlockItem CLOUDBERRIES = new AliasedBlockItem(BodaciousBushes.CLOUDBERRY_BUSH, berrySettings.food(new FoodComponent.Builder().hunger(2).saturationModifier(1f).statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, 1), 1).snack().build()));

    public static void register() {
        register(SASKATOON_BERRIES, BodaciousBushes.SASKATOON_ID);
        register(STRAWBERRIES, BodaciousBushes.STRAWBERRY_ID);
        register(RASPBERRIES, BodaciousBushes.RASPBERRY_ID);
        register(BLACKBERRIES, BodaciousBushes.BLACKBERRY_ID);
        register(CHORUS_BERRIES, BodaciousBushes.CHORUS_BERRY_ID);
        register(RAINBERRIES, BodaciousBushes.RAINBERRY_ID);
        register(LINGONBERRIES, BodaciousBushes.LINGONBERRY_ID);
        register(GRAPES, BodaciousBushes.GRAPE_ID);
        register(GOJI_BERRIES, BodaciousBushes.GOJI_BERRY_ID);
        register(GOOSEBERRIES, BodaciousBushes.GOOSEBERRY_ID);
        register(CLOUDBERRIES, BodaciousBushes.CLOUDBERRY_ID);
    }

    /**
     * the chance that a berry will raise the level of compost in a composter
     * <br> out of one: 0.4 = 40%, etc
     */
    private static final float BERRY_COMPOST_LEVEL_INCREASE_CHANCE = 0.4f;

    private static void register(Item berry, Identifier id) {
        Registry.register(Registry.ITEM, id, berry);
        CompostingChanceRegistry.INSTANCE.add(berry, BERRY_COMPOST_LEVEL_INCREASE_CHANCE);
    }

    private static Item.Settings settings(int hunger, float saturation) {
        return new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).snack().build());
    }
}
