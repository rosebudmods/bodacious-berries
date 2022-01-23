package io.ix0rai.bodaciousberries.registry.items;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import static net.minecraft.world.biome.BiomeKeys.*;

public class BodaciousItems {
    private static final Settings CHORUS_JUICE_SETTINGS = new Settings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(2).saturationModifier(4F).build()).group(ItemGroup.FOOD).maxCount(16);

    public static final Item CHORUS_BERRY_JUICE = chorusBerryJuice(null);
    public static final Item CHORUS_BERRY_JUICE_JUNGLE = chorusBerryJuice(JUNGLE);
    public static final Item CHORUS_BERRY_JUICE_PLAINS = chorusBerryJuice(PLAINS);
    public static final Item CHORUS_BERRY_JUICE_SNOWY_SLOPES = chorusBerryJuice(SNOWY_SLOPES);
    public static final Item CHORUS_BERRY_JUICE_SWAMP = chorusBerryJuice(SWAMP);
    public static final Item CHORUS_BERRY_JUICE_DESERT = chorusBerryJuice(DESERT);
    public static final Item CHORUS_BERRY_JUICE_TAIGA = chorusBerryJuice(TAIGA);
    public static final Item CHORUS_BERRY_JUICE_BIRCH_FOREST = chorusBerryJuice(BIRCH_FOREST);
    public static final Item CHORUS_BERRY_JUICE_OCEAN = chorusBerryJuice(OCEAN);
    public static final Item CHORUS_BERRY_JUICE_MUSHROOM = chorusBerryJuice(MUSHROOM_FIELDS);
    public static final Item CHORUS_BERRY_JUICE_SUNFLOWER_PLAINS = chorusBerryJuice(SUNFLOWER_PLAINS);
    public static final Item CHORUS_BERRY_JUICE_FOREST = chorusBerryJuice(FOREST);
    public static final Item CHORUS_BERRY_JUICE_FLOWER_FOREST = chorusBerryJuice(FLOWER_FOREST);
    public static final Item CHORUS_BERRY_JUICE_DARK_FOREST = chorusBerryJuice(DARK_FOREST);
    public static final Item CHORUS_BERRY_JUICE_SAVANNA = chorusBerryJuice(SAVANNA);
    public static final Item CHORUS_BERRY_JUICE_BADLANDS = chorusBerryJuice(BADLANDS);
    public static final Item CHORUS_BERRY_JUICE_MEADOW = chorusBerryJuice(MEADOW);
    public static final Item CHORUS_BERRY_JUICE_GROVE = chorusBerryJuice(GROVE);
    public static final Item CHORUS_BERRY_JUICE_LUSH_CAVES = chorusBerryJuice(LUSH_CAVES);
    public static final Item CHORUS_BERRY_JUICE_DRIPSTONE_CAVES = chorusBerryJuice(DRIPSTONE_CAVES);

    public static void registerItems() {
        Berries.registerBerries();

        register("chorus_berry_juice", CHORUS_BERRY_JUICE);
        register("chorus_berry_juice_jungle", CHORUS_BERRY_JUICE_JUNGLE);
        register("chorus_berry_juice_plains", CHORUS_BERRY_JUICE_PLAINS);
        register("chorus_berry_juice_snowy_slopes", CHORUS_BERRY_JUICE_SNOWY_SLOPES);
        register("chorus_berry_juice_swamp", CHORUS_BERRY_JUICE_SWAMP);
        register("chorus_berry_juice_desert", CHORUS_BERRY_JUICE_DESERT);
        register("chorus_berry_juice_taiga", CHORUS_BERRY_JUICE_TAIGA);
        register("chorus_berry_juice_birch_forest", CHORUS_BERRY_JUICE_BIRCH_FOREST);
        register("chorus_berry_juice_ocean", CHORUS_BERRY_JUICE_OCEAN);
        register("chorus_berry_juice_mushroom", CHORUS_BERRY_JUICE_MUSHROOM);
        register("chorus_berry_juice_sunflower_plains", CHORUS_BERRY_JUICE_SUNFLOWER_PLAINS);
        register("chorus_berry_juice_forest", CHORUS_BERRY_JUICE_FOREST);
        register("chorus_berry_juice_flower_forest", CHORUS_BERRY_JUICE_FLOWER_FOREST);
        register("chorus_berry_juice_dark_forest", CHORUS_BERRY_JUICE_DARK_FOREST);
        register("chorus_berry_juice_savanna", CHORUS_BERRY_JUICE_SAVANNA);
        register("chorus_berry_juice_badlands", CHORUS_BERRY_JUICE_BADLANDS);
        register("chorus_berry_juice_meadow", CHORUS_BERRY_JUICE_MEADOW);
        register("chorus_berry_juice_grove", CHORUS_BERRY_JUICE_GROVE);
        register("chorus_berry_juice_lush_caves", CHORUS_BERRY_JUICE_LUSH_CAVES);
        register("chorus_berry_juice_dripstone_caves", CHORUS_BERRY_JUICE_DRIPSTONE_CAVES);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
    }

    private static ChorusBerryJuice chorusBerryJuice(RegistryKey<Biome> biome) {
        if (biome == null) {
            return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, null);
        }
        return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, biome.getValue());
    }
}
