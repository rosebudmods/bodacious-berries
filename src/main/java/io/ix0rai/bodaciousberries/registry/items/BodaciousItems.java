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

import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class BodaciousItems {
    private static final Settings CHORUS_JUICE_SETTINGS = new Settings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(2).saturationModifier(4F).build()).group(ItemGroup.FOOD).maxCount(16);

    public static final Item CHORUS_BERRY_JUICE = chorusBerryJuice(null);

    public static void registerItems() {
        Berries.registerBerries();

        List<RegistryKey<Biome>> biomes = List.of(JUNGLE,
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                GROVE, LUSH_CAVES, DRIPSTONE_CAVES
        );
        register("chorus_berry_juice", CHORUS_BERRY_JUICE);
        createChorusBerryJuice(biomes);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
    }

    private static void createChorusBerryJuice(List<RegistryKey<Biome>> biomes) {
        for (RegistryKey<Biome> key : biomes) {
            String name = "chorus_berry_juice_" + key.getValue().getPath();
            register(name, chorusBerryJuice(key));
        }
    }

    private static ChorusBerryJuice chorusBerryJuice(RegistryKey<Biome> biome) {
        if (biome == null) {
            return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, null);
        }
        return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, biome.getValue());
    }
}
