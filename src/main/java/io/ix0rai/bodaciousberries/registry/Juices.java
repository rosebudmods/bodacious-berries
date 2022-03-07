package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import io.ix0rai.bodaciousberries.registry.items.ChorusBerryJuice;
import io.ix0rai.bodaciousberries.registry.items.Juice;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class Juices {
    private static final Item.Settings CHORUS_JUICE_SETTINGS = new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(2).saturationModifier(4F).build()).group(ItemGroup.FOOD).maxCount(16);

    public static final Juice CHORUS_BERRY_JUICE = chorusBerryJuice(null);
    private static final Juice SASKATOON_BERRY_JUICE = new Juice(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(2).saturationModifier(4F).build()).group(ItemGroup.FOOD).maxCount(16));

    public static void registerJuice() {
        register(Bodaciousberries.getIdentifier("saskatoon_berry_juice"), SASKATOON_BERRY_JUICE, Berries.SASKATOON_BERRIES);

        createChorusBerryJuice(List.of(
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                LUSH_CAVES, DRIPSTONE_CAVES, JUNGLE
        ));
    }

    private static void register(Identifier id, Juice juice, Item berry) {
        JuicerRecipes.addRecipe(berry, juice);
        Registry.register(Registry.ITEM, id, juice);
    }

    private static void createChorusBerryJuice(List<RegistryKey<Biome>> biomes) {
        register(Bodaciousberries.getIdentifier("chorus_berry_juice"), CHORUS_BERRY_JUICE, Berries.CHORUS_BERRIES);

        for (RegistryKey<Biome> key : biomes) {
            ChorusBerryJuice juice = chorusBerryJuice(key);
            JuicerRecipes.addRecipe(Berries.CHORUS_BERRIES, juice);
            String name = "chorus_berry_juice_" + key.getValue().getPath();
            register(Bodaciousberries.getIdentifier(name), juice, Berries.CHORUS_BERRIES);
        }
    }

    private static ChorusBerryJuice chorusBerryJuice(RegistryKey<Biome> biome) {
        if (biome == null) {
            return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, null);
        }
        return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, biome.getValue());
    }
}
