package io.ix0rai.bodaciousberries.worldgen;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import io.ix0rai.bodaciousberries.registry.Bushes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacementModifier;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.function.Predicate;

public class BerryBushPatchGen {
    private static final int ULTRA_RARE_BERRY_BUSH_RARITY = 100;
    private static final int RARE_BERRY_BUSH_RARITY = 90;
    private static final int MEDIUM_BERRY_BUSH_RARITY = 80;
    private static final int COMMON_BERRY_BUSH_RARITY = 60;

    public static Feature<DefaultFeatureConfig> GRAPEVINE_FEATURE;
    public static Feature<DoubleBushFeatureConfig> DOUBLE_BUSH_FEATURE;

    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_SASKATOON_BERRY;
    public static Holder<PlacedFeature> PATCH_SASKATOON_BERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_STRAWBERRY;
    public static Holder<PlacedFeature> PATCH_STRAWBERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_RASPBERRY;
    public static Holder<PlacedFeature> PATCH_RASPBERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_BLACKBERRY;
    public static Holder<PlacedFeature> PATCH_BLACKBERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CHORUS_BERRY;
    public static Holder<PlacedFeature> PATCH_CHORUS_BERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_RAINBERRY;
    public static Holder<PlacedFeature> PATCH_RAINBERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_LINGONBERRY;
    public static Holder<PlacedFeature> PATCH_LINGONBERRY_PLACED;
    public static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> PATCH_GRAPEVINE;
    public static Holder<PlacedFeature> PATCH_GRAPEVINE_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_GOJI_BERRY;
    public static Holder<PlacedFeature> PATCH_GOJI_BERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_GOOSEBERRY;
    public static Holder<PlacedFeature> PATCH_GOOSEBERRY_PLACED;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_CLOUDBERRY;
    public static Holder<PlacedFeature> PATCH_CLOUDBERRY_PLACED;

    public static void register() {
        registerFeatures();
        registerConfiguredFeatures();
        registerPlacedFeatureUtil();
        placePatches();
    }

    private static void registerFeatures() {
        GRAPEVINE_FEATURE = Registry.register(Registry.FEATURE, Bodaciousberries.id("grapevines"), new GrapevineFeature(DefaultFeatureConfig.CODEC));
        DOUBLE_BUSH_FEATURE = Registry.register(Registry.FEATURE, Bodaciousberries.id("double_bush"), new DoubleBushFeature(DoubleBushFeatureConfig.CODEC));
    }

    private static void registerConfiguredFeatures() {
        PATCH_SASKATOON_BERRY = berryPatchConfiguredFeature("patch_saskatoon_berry", Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_STRAWBERRY = berryPatchConfiguredFeature("patch_strawberry", Bushes.STRAWBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_BLACKBERRY = berryPatchConfiguredFeature("patch_blackberry", Bushes.BLACKBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_RASPBERRY = berryPatchConfiguredFeature("patch_raspberry", Bushes.RASPBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_CHORUS_BERRY = berryPatchConfiguredFeature("patch_chorus_berry", Bushes.CHORUS_BERRY_BUSH, Blocks.END_STONE);
        PATCH_RAINBERRY = berryPatchConfiguredFeature("patch_rainberry", Bushes.RAINBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_LINGONBERRY = berryPatchConfiguredFeature("patch_lingonberry", Bushes.LINGONBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_GRAPEVINE = ConfiguredFeatureUtil.register(Bodaciousberries.idString("patch_grapevine"), GRAPEVINE_FEATURE, DefaultFeatureConfig.INSTANCE);
        PATCH_GOJI_BERRY = berryPatchConfiguredFeature("patch_goji_berry", Bushes.GOJI_BERRY_BUSH, Bushes.DOUBLE_GOJI_BERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_GOOSEBERRY = berryPatchConfiguredFeature("patch_gooseberry", Bushes.GOOSEBERRY_BUSH, Blocks.GRASS_BLOCK);
        PATCH_CLOUDBERRY = berryPatchConfiguredFeature("patch_cloudberry", Bushes.CLOUDBERRY_BUSH, Blocks.GRASS_BLOCK);
    }

    private static void registerPlacedFeatureUtil() {
        PATCH_SASKATOON_BERRY_PLACED = berryPatchPlacedFeature("patch_saskatoon_berry_placed", COMMON_BERRY_BUSH_RARITY, PATCH_SASKATOON_BERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_STRAWBERRY_PLACED = berryPatchPlacedFeature("patch_strawberry_placed", COMMON_BERRY_BUSH_RARITY, PATCH_STRAWBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_BLACKBERRY_PLACED = berryPatchPlacedFeature("patch_blackberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_BLACKBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_RASPBERRY_PLACED = berryPatchPlacedFeature("patch_raspberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_RASPBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_CHORUS_BERRY_PLACED = berryPatchPlacedFeature("patch_chorus_berry_placed", RARE_BERRY_BUSH_RARITY, PATCH_CHORUS_BERRY, PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP);
        PATCH_RAINBERRY_PLACED = berryPatchPlacedFeature("patch_rainberry_placed", ULTRA_RARE_BERRY_BUSH_RARITY, PATCH_RAINBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_LINGONBERRY_PLACED = berryPatchPlacedFeature("patch_lingonberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_LINGONBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_GRAPEVINE_PLACED = PlacedFeatureUtil.register(Bodaciousberries.idString("patch_grapevine_placed"), PATCH_GRAPEVINE,
                List.of(CountPlacementModifier.create(127), HeightRangePlacementModifier.createUniform(YOffset.fixed(50), YOffset.fixed(255)), BiomePlacementModifier.getInstance(), RarityFilterPlacementModifier.create(MEDIUM_BERRY_BUSH_RARITY))
        );
        PATCH_GOJI_BERRY_PLACED = berryPatchPlacedFeature("patch_goji_berry_placed", RARE_BERRY_BUSH_RARITY, PATCH_GOJI_BERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_GOOSEBERRY_PLACED = berryPatchPlacedFeature("patch_gooseberry_placed", MEDIUM_BERRY_BUSH_RARITY, PATCH_GOOSEBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        PATCH_CLOUDBERRY_PLACED = berryPatchPlacedFeature("patch_cloudberry_placed", RARE_BERRY_BUSH_RARITY, PATCH_CLOUDBERRY, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
    }

    private static void placePatches() {
        final List<TagKey<Biome>> saskatoonBerryCategories = List.of(BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_MOUNTAIN);
        final List<TagKey<Biome>> strawberryCategories = List.of(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE, BiomeTags.IS_FOREST, BiomeTags.SWAMP_HUT_HAS_STRUCTURE);
        final List<TagKey<Biome>> lingonberryCategories = List.of(BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA);
        final List<TagKey<Biome>> gooseberryCategories = List.of(BiomeTags.IS_FOREST, BiomeTags.IS_MOUNTAIN);

        generateBerryPatches("add_saskatoon_berry_patches", saskatoonBerryCategories, PATCH_SASKATOON_BERRY_PLACED);
        generateBerryPatches("add_strawberry_patches", strawberryCategories, PATCH_STRAWBERRY_PLACED);
        generateBerryPatches("add_raspberry_patches", saskatoonBerryCategories, PATCH_RASPBERRY_PLACED);
        generateBerryPatches("add_blackberry_patches", saskatoonBerryCategories, PATCH_BLACKBERRY_PLACED);
        generateBerryPatches("add_chorus_berry_patches", BiomeTags.END_CITY_HAS_STRUCTURE, PATCH_CHORUS_BERRY_PLACED);
        generateBerryPatches("add_rainberry_patches", strawberryCategories, PATCH_RAINBERRY_PLACED);
        generateBerryPatches("add_lingonberry_patches", lingonberryCategories, PATCH_LINGONBERRY_PLACED);
        generateBerryPatches("add_grapevine_patches", BiomeTags.IS_JUNGLE, PATCH_GRAPEVINE_PLACED);
        generateBerryPatches("add_goji_berry_patches", BiomeTags.IS_MOUNTAIN, PATCH_GOJI_BERRY_PLACED);
        generateBerryPatches("add_gooseberry_patches", gooseberryCategories, PATCH_GOOSEBERRY_PLACED);
        //cloudberries generated below the minimum height will just die :(
        generateBerryPatches("add_cloudberry_patches", BiomeTags.IS_MOUNTAIN, PATCH_CLOUDBERRY_PLACED);
    }

    /**
     * creates and registers a berry patch feature, with the default age of the berry bush being the maximum age of said berry bush
     * @param name the name of the feature
     * @param bush the bush to generate
     * @param placedOn which block the bush should be placed on
     * @return a berry patch configured feature
     */
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, BerryBush bush, Block placedOn) {
        return ConfiguredFeatureUtil.register(Bodaciousberries.idString(name), Feature.RANDOM_PATCH,
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(bush.getBaseState().with(bush.getAge(), bush.getMaxAge()))),
                        List.of(placedOn)
                )
        );
    }

    /**
     * creates and registers a berry patch feature, with the default age of the berry bush being the maximum age of said berry bush
     * <br>also generates the double version of said berry bush alongside it
     * @param name the name of the feature
     * @param smallBush the bush to generate
     * @param tallBush the tall version of the bush
     * @param placedOn which block the bush should be placed on
     * @return a berry patch configured feature
     */
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, GrowingBerryBush smallBush, DoubleBerryBush tallBush, Block placedOn) {
        return ConfiguredFeatureUtil.register(Bodaciousberries.idString(name), Feature.RANDOM_PATCH,
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(DOUBLE_BUSH_FEATURE,
                        new DoubleBushFeatureConfig(BlockStateProvider.of(smallBush.getBaseState().with(smallBush.getAge(), smallBush.getMaxAge())),
                                BlockStateProvider.of(tallBush.getDefaultState().with(DoubleBerryBush.AGE, tallBush.getMaxAge()))),
                        List.of(placedOn)
                )
        );
    }

    /**
     * creates and registers a placed berry patch feature
     * @param name the name of the feature
     * @param rarity the rarity of the feature: lower is more common
     * @param feature the feature to place
     * @return a placed berry patch feature
     */
    public static Holder<PlacedFeature> berryPatchPlacedFeature(String name, int rarity, Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> feature, PlacementModifier heightMap) {
        return PlacedFeatureUtil.register(Bodaciousberries.idString(name), feature,
                List.of(
                        RarityFilterPlacementModifier.create(rarity),
                        InSquarePlacementModifier.getInstance(),
                        heightMap
                )
        );
    }

    /**
     * generates the placed berry patch feature in the specified biomes
     * @param name the name of the feature
     * @param tag the biome tag to generate the feature in
     * @param placedFeature the feature to place
     */
    public static void generateBerryPatches(String name, TagKey<Biome> tag, Holder<PlacedFeature> placedFeature) {
        generateBerryPatches(name, context -> context.getBiomeRegistryEntry().hasTag(tag), placedFeature);
    }

    /**
     * generates the placed berry patch feature in the specified biomes
     * @param name the name of the feature
     * @param tags the biome tags to generate the feature in
     * @param placedFeature the feature to place
     */
    public static void generateBerryPatches(String name, List<TagKey<Biome>> tags, Holder<PlacedFeature> placedFeature) {
        generateBerryPatches(
                name,
                //check all tags
                context -> {
                    Holder<Biome> entry = context.getBiomeRegistryEntry();
                    for (TagKey<Biome> tag : tags) {
                        if (entry.hasTag(tag)) {
                            return true;
                        }
                    }

                    return false;
                },
                placedFeature
        );
    }

    private static void generateBerryPatches(String name, Predicate<BiomeSelectionContext> predicate, Holder<PlacedFeature> placedFeature) {
        BiomeModifications.create(Bodaciousberries.id(name)).add(
                ModificationPhase.ADDITIONS,
                //decide if the biome should receive our feature
                predicate,
                //add feature to the biome under the step vegetal decoration
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, placedFeature.value())
        );
    }
}
