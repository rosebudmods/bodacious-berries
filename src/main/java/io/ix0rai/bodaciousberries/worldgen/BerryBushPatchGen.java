package io.ix0rai.bodaciousberries.worldgen;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BasicBerryBush;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.PlacementModifier;
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

import java.util.List;

public class BerryBushPatchGen {
    private static final int RARE_BERRY_BUSH_RARITY = 40;
    private static final int MEDIUM_BERRY_BUSH_RARITY = 32;
    private static final int COMMON_BERRY_BUSH_RARITY = 20;

    public static ConfiguredFeature<?, ?> PATCH_SASKATOON_BERRY;
    public static PlacedFeature PATCH_SASKATOON_BERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_STRAWBERRY;
    public static PlacedFeature PATCH_STRAWBERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_RASPBERRY;
    public static PlacedFeature PATCH_RASPBERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_BLACKBERRY;
    public static PlacedFeature PATCH_BLACKBERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_CHORUS_BERRY;
    public static PlacedFeature PATCH_CHORUS_BERRY_PLACED;
    public static ConfiguredFeature<?, ?> PATCH_RAINBERRY;
    public static PlacedFeature PATCH_RAINBERRY_PLACED;

    public static void registerFeatures() {
        PATCH_SASKATOON_BERRY = berryPatchConfiguredFeature("patch_saskatoon_berry", Bushes.SASKATOON_BERRY_BUSH, 3, Blocks.GRASS_BLOCK);
        PATCH_STRAWBERRY = berryPatchConfiguredFeature("patch_strawberry", Bushes.STRAWBERRY_BUSH, 3, Blocks.GRASS_BLOCK);
        PATCH_BLACKBERRY = berryPatchConfiguredFeature("patch_blackberry", Bushes.BLACKBERRY_BUSH, 4, Blocks.GRASS_BLOCK);
        PATCH_RASPBERRY = berryPatchConfiguredFeature("patch_raspberry", Bushes.RASPBERRY_BUSH, 4, Blocks.GRASS_BLOCK);
        PATCH_CHORUS_BERRY = berryPatchConfiguredFeature("patch_chorus_berry", Bushes.CHORUS_BERRY_BUSH, 3, Blocks.END_STONE);
        PATCH_RAINBERRY = berryPatchConfiguredFeature("patch_rainberry", Bushes.RAINBERRY_BUSH, 4, Blocks.GRASS_BLOCK);

        PATCH_SASKATOON_BERRY_PLACED = berryPatchPlacedFeature("patch_saskatoon_berry_placed", COMMON_BERRY_BUSH_RARITY, PATCH_SASKATOON_BERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_STRAWBERRY_PLACED = berryPatchPlacedFeature("patch_strawberry_placed", COMMON_BERRY_BUSH_RARITY, PATCH_STRAWBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_BLACKBERRY_PLACED = berryPatchPlacedFeature("patch_blackberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_BLACKBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_RASPBERRY_PLACED = berryPatchPlacedFeature("patch_raspberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_RASPBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_CHORUS_BERRY_PLACED = berryPatchPlacedFeature("patch_chorus_berry_placed", RARE_BERRY_BUSH_RARITY, PATCH_CHORUS_BERRY, PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP);
        PATCH_RAINBERRY_PLACED = berryPatchPlacedFeature("patch_rainberry_placed", RARE_BERRY_BUSH_RARITY, PATCH_RAINBERRY, PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);

        List<Biome.Category> saskatoonBerryCategories = List.of(Biome.Category.FOREST, Biome.Category.TAIGA, Biome.Category.MOUNTAIN);
        List<Biome.Category> strawberryCategories = List.of(Biome.Category.PLAINS, Biome.Category.FOREST, Biome.Category.SWAMP);

        generateBerryPatches("add_saskatoon_berry_patches", saskatoonBerryCategories, PATCH_SASKATOON_BERRY_PLACED);
        generateBerryPatches("add_strawberry_patches", strawberryCategories, PATCH_STRAWBERRY_PLACED);
        generateBerryPatches("add_raspberry_patches", saskatoonBerryCategories, PATCH_RASPBERRY_PLACED);
        generateBerryPatches("add_blackberry_patches", saskatoonBerryCategories, PATCH_BLACKBERRY_PLACED);
        generateBerryPatches("add_chorus_berry_patches", List.of(Biome.Category.THEEND), PATCH_CHORUS_BERRY_PLACED);
        generateBerryPatches("add_rainberry_patches", strawberryCategories, PATCH_RAINBERRY_PLACED);
    }

    /**
     * creates and registers a berry patch feature
     * @param name the name of the feature
     * @param bush the bush to generate
     * @param defaultAge the default age of the bush
     * @return a berry patch configured feature
     */
    public static ConfiguredFeature<RandomPatchFeatureConfig, ?> berryPatchConfiguredFeature(String name, BasicBerryBush bush, int defaultAge, Block placedOn) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Bodaciousberries.getIdentifier(name),
                Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                                new SimpleBlockFeatureConfig(BlockStateProvider.of(bush.getDefaultState().with(BasicBerryBush.AGE, defaultAge)))),
                        List.of(placedOn)
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
    public static PlacedFeature berryPatchPlacedFeature(String name, int rarity, ConfiguredFeature<?, ?> feature, PlacementModifier heightMap) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, Bodaciousberries.getIdentifier(name),
                feature.withPlacement(
                        RarityFilterPlacementModifier.of(rarity),
                        SquarePlacementModifier.of(),
                        heightMap
                )
        );
    }

    /**
     * generates the placed berry patch feature in the specified biomes
     * @param name the name of the feature
     * @param categories the biomes to generate the feature in
     * @param placedFeature the feature to place
     */
    public static void generateBerryPatches(String name, List<Biome.Category> categories, PlacedFeature placedFeature) {
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
