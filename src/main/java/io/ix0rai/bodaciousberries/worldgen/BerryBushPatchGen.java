package io.ix0rai.bodaciousberries.worldgen;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BasicBerryBush;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Arrays;
import java.util.List;

public class BerryBushPatchGen {
    public static ConfiguredFeature<?, ?> PATCH_SASKATOON_BERRY;
    public static PlacedFeature PATCH_SASKATOON_BERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_STRAWBERRY;
    public static PlacedFeature PATCH_STRAWBERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_RASPBERRY;
    public static PlacedFeature PATCH_RASPBERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_BLACKBERRY;
    public static PlacedFeature PATCH_BLACKBERRY_PLACED;

    public static void registerFeatures() {
        PATCH_SASKATOON_BERRY = berryPatchConfiguredFeature("patch_saskatoon_berry", Bushes.SASKATOON_BERRY_BUSH, 3);
        PATCH_STRAWBERRY = berryPatchConfiguredFeature("patch_strawberry", Bushes.STRAWBERRY_BUSH, 3);
        PATCH_BLACKBERRY = berryPatchConfiguredFeature("patch_blackberry", Bushes.BLACKBERRY_BUSH, 4);
        PATCH_RASPBERRY = berryPatchConfiguredFeature("patch_raspberry", Bushes.RASPBERRY_BUSH, 4);

        PATCH_SASKATOON_BERRY_PLACED = berryPatchPlacedFeature("patch_saskatoon_berry_placed", 20, PATCH_SASKATOON_BERRY);
        PATCH_STRAWBERRY_PLACED = berryPatchPlacedFeature("patch_strawberry_placed", 15, PATCH_STRAWBERRY);
        PATCH_BLACKBERRY_PLACED = berryPatchPlacedFeature("patch_blackberry_placed", 32, PATCH_BLACKBERRY);
        PATCH_RASPBERRY_PLACED = berryPatchPlacedFeature("patch_raspberry_placed", 32, PATCH_RASPBERRY);

        Biome.Category[] saskatoonBerryCategories = new Biome.Category[]{Biome.Category.FOREST, Biome.Category.TAIGA, Biome.Category.MOUNTAIN};
        Biome.Category[] strawberryCategories = new Biome.Category[]{Biome.Category.PLAINS, Biome.Category.FOREST, Biome.Category.SWAMP};

        generateBerryPatches("add_saskatoon_berry_patches", saskatoonBerryCategories, PATCH_SASKATOON_BERRY_PLACED);
        generateBerryPatches("add_strawberry_patches", strawberryCategories, PATCH_STRAWBERRY_PLACED);
        generateBerryPatches("add_raspberry_patches", saskatoonBerryCategories, PATCH_RASPBERRY_PLACED);
        generateBerryPatches("add_blackberry_patches", saskatoonBerryCategories, PATCH_BLACKBERRY_PLACED);
    }

    /**
     * creates and registers a berry patch feature
     * @param name the name of the feature
     * @param bush the bush to generate
     * @param defaultAge the default age of the bush
     * @return a berry patch configured feature
     */
    public static ConfiguredFeature<RandomPatchFeatureConfig, ?> berryPatchConfiguredFeature(String name, BasicBerryBush bush, int defaultAge) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Bodaciousberries.getIdentifier(name),
                Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                                new SimpleBlockFeatureConfig(BlockStateProvider.of(bush.getDefaultState().with(BasicBerryBush.BERRY_AGE, defaultAge)))),
                        List.of(Blocks.GRASS_BLOCK)
                ))
        );
    }

    /**
     * creates and registers a placed berry patch feature
     * @param name the name of the feature
     * @param rarity the rarity of the feature: lower is more common
     * @param feature the feature to place
     * @return a placed berry patch feature
     */
    public static PlacedFeature berryPatchPlacedFeature(String name, int rarity, ConfiguredFeature<?, ?> feature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, Bodaciousberries.getIdentifier(name),
                feature.withPlacement(
                        RarityFilterPlacementModifier.of(rarity),
                        SquarePlacementModifier.of(),
                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                        BiomePlacementModifier.of()
                )
        );
    }

    /**
     * generates the placed berry patch feature in the specified biomes
     * @param name the name of the feature
     * @param biomeCategories the biomes to generate the feature in
     * @param placedFeature the feature to place
     */
    public static void generateBerryPatches(String name, Biome.Category[] biomeCategories, PlacedFeature placedFeature) {
        List<Biome.Category> categories = Arrays.asList(biomeCategories);

        BiomeModifications.create(Bodaciousberries.getIdentifier(name)).add(
                ModificationPhase.ADDITIONS,
                //decide if the biome should receive our feature
                context -> {
                    Biome.Category category = context.getBiome().getCategory();
                    return categories.contains(category) && category != Biome.Category.NONE;
                },
                // add feature to the biome under the step vegetal decoration
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, placedFeature)
        );
    }
}