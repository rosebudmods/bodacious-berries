package io.ix0rai.bodaciousberries.worldgen;

import io.ix0rai.bodaciousberries.BodaciousBerries;
import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import io.ix0rai.bodaciousberries.config.BodaciousConfig;
import io.ix0rai.bodaciousberries.registry.Bushes;
import me.shedaniel.autoconfig.AutoConfig;
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
    public static Feature<DefaultFeatureConfig> grapevineFeature;
    public static Feature<DoubleBushFeatureConfig> doubleBushFeature;

    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchSaskatoonBerry;
    public static Holder<PlacedFeature> patchSaskatoonBerryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchStrawberry;
    public static Holder<PlacedFeature> patchStrawberryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchRaspberry;
    public static Holder<PlacedFeature> patchRaspberryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchBlackberry;
    public static Holder<PlacedFeature> patchBlackberryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchChorusBerry;
    public static Holder<PlacedFeature> patchChorusBerryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchRainberry;
    public static Holder<PlacedFeature> patchRainberryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchLingonberry;
    public static Holder<PlacedFeature> patchLingonberryPlaced;
    public static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> patchGrapevine;
    public static Holder<PlacedFeature> patchGrapevinePlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchGojiBerry;
    public static Holder<PlacedFeature> patchGojiBerryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchGooseberry;
    public static Holder<PlacedFeature> patchGooseberryPlaced;
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> patchCloudberry;
    public static Holder<PlacedFeature> patchCloudberryPlaced;

    public static void register() {
        registerFeatures();
        registerConfiguredFeatures();
        registerPlacedFeatures();
        placePatches();
    }

    private static void registerFeatures() {
        grapevineFeature = Registry.register(Registry.FEATURE, BodaciousBerries.id("grapevines"), new GrapevineFeature(DefaultFeatureConfig.CODEC));
        doubleBushFeature = Registry.register(Registry.FEATURE, BodaciousBerries.id("double_bush"), new DoubleBushFeature(DoubleBushFeatureConfig.CODEC));
    }

    private static void registerConfiguredFeatures() {
        patchSaskatoonBerry = berryPatchConfiguredFeature("patch_saskatoon_berry", Bushes.SASKATOON_BERRY_BUSH, Bushes.DOUBLE_SASKATOON_BERRY_BUSH, Blocks.GRASS_BLOCK);
        patchStrawberry = berryPatchConfiguredFeature("patch_strawberry", Bushes.STRAWBERRY_BUSH, Blocks.GRASS_BLOCK);
        patchBlackberry = berryPatchConfiguredFeature("patch_blackberry", Bushes.BLACKBERRY_BUSH, Blocks.GRASS_BLOCK);
        patchRaspberry = berryPatchConfiguredFeature("patch_raspberry", Bushes.RASPBERRY_BUSH, Blocks.GRASS_BLOCK);
        patchChorusBerry = berryPatchConfiguredFeature("patch_chorus_berry", Bushes.CHORUS_BERRY_BUSH, Blocks.END_STONE);
        patchRainberry = berryPatchConfiguredFeature("patch_rainberry", Bushes.RAINBERRY_BUSH, Blocks.GRASS_BLOCK);
        patchLingonberry = berryPatchConfiguredFeature("patch_lingonberry", Bushes.LINGONBERRY_BUSH, Blocks.GRASS_BLOCK);
        patchGrapevine = ConfiguredFeatureUtil.register(BodaciousBerries.idString("patch_grapevine"), grapevineFeature, DefaultFeatureConfig.INSTANCE);
        patchGojiBerry = berryPatchConfiguredFeature("patch_goji_berry", Bushes.GOJI_BERRY_BUSH, Bushes.DOUBLE_GOJI_BERRY_BUSH, Blocks.GRASS_BLOCK);
        patchGooseberry = berryPatchConfiguredFeature("patch_gooseberry", Bushes.GOOSEBERRY_BUSH, Blocks.GRASS_BLOCK);
        patchCloudberry = berryPatchConfiguredFeature("patch_cloudberry", Bushes.CLOUDBERRY_BUSH, Blocks.GRASS_BLOCK);
    }

    private static void registerPlacedFeatures() {
        BodaciousConfig config = AutoConfig.getConfigHolder(BodaciousConfig.class).getConfig();

        patchSaskatoonBerryPlaced = berryPatchPlacedFeature("patch_saskatoon_berry_placed", config.commonBushRarity, patchSaskatoonBerry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchStrawberryPlaced = berryPatchPlacedFeature("patch_strawberry_placed", config.commonBushRarity, patchStrawberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchBlackberryPlaced = berryPatchPlacedFeature("patch_blackberry_placed", config.mediumBushRarity, patchBlackberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchRaspberryPlaced = berryPatchPlacedFeature("patch_raspberry_placed", config.mediumBushRarity, patchRaspberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchChorusBerryPlaced = berryPatchPlacedFeature("patch_chorus_berry_placed", config.rareBushRarity, patchChorusBerry, PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP);
        patchRainberryPlaced = berryPatchPlacedFeature("patch_rainberry_placed", config.ultraRareBushRarity, patchRainberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchLingonberryPlaced = berryPatchPlacedFeature("patch_lingonberry_placed", config.mediumBushRarity, patchLingonberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchGrapevinePlaced = PlacedFeatureUtil.register(BodaciousBerries.idString("patch_grapevine_placed"), patchGrapevine,
                List.of(CountPlacementModifier.create(127), HeightRangePlacementModifier.createUniform(YOffset.fixed(50), YOffset.fixed(255)), BiomePlacementModifier.getInstance(), RarityFilterPlacementModifier.create(config.mediumBushRarity))
        );
        patchGojiBerryPlaced = berryPatchPlacedFeature("patch_goji_berry_placed", config.rareBushRarity, patchGojiBerry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchGooseberryPlaced = berryPatchPlacedFeature("patch_gooseberry_placed", config.mediumBushRarity, patchGooseberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
        patchCloudberryPlaced = berryPatchPlacedFeature("patch_cloudberry_placed", config.rareBushRarity, patchCloudberry, PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
    }

    private static void placePatches() {
        final List<TagKey<Biome>> saskatoonBerryCategories = List.of(BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_MOUNTAIN);
        final List<TagKey<Biome>> strawberryCategories = List.of(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE, BiomeTags.IS_FOREST, BiomeTags.SWAMP_HUT_HAS_STRUCTURE);
        final List<TagKey<Biome>> lingonberryCategories = List.of(BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA);
        final List<TagKey<Biome>> gooseberryCategories = List.of(BiomeTags.IS_FOREST, BiomeTags.IS_MOUNTAIN);

        generateBerryPatches("add_saskatoon_berry_patches", saskatoonBerryCategories, patchSaskatoonBerryPlaced);
        generateBerryPatches("add_strawberry_patches", strawberryCategories, patchStrawberryPlaced);
        generateBerryPatches("add_raspberry_patches", saskatoonBerryCategories, patchRaspberryPlaced);
        generateBerryPatches("add_blackberry_patches", saskatoonBerryCategories, patchBlackberryPlaced);
        generateBerryPatches("add_chorus_berry_patches", BiomeTags.END_CITY_HAS_STRUCTURE, patchChorusBerryPlaced);
        generateBerryPatches("add_rainberry_patches", strawberryCategories, patchRainberryPlaced);
        generateBerryPatches("add_lingonberry_patches", lingonberryCategories, patchLingonberryPlaced);
        generateBerryPatches("add_grapevine_patches", BiomeTags.IS_JUNGLE, patchGrapevinePlaced);
        generateBerryPatches("add_goji_berry_patches", BiomeTags.IS_MOUNTAIN, patchGojiBerryPlaced);
        generateBerryPatches("add_gooseberry_patches", gooseberryCategories, patchGooseberryPlaced);
        // cloudberries generated below the minimum height will just die :(
        generateBerryPatches("add_cloudberry_patches", BiomeTags.IS_MOUNTAIN, patchCloudberryPlaced);
    }

    /**
     * creates and registers a berry patch feature, with the default age of the berry bush being the maximum age of said berry bush
     * @param name the name of the feature
     * @param bush the bush to generate
     * @param placedOn which block the bush should be placed on
     * @return a berry patch configured feature
     */
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, BerryBush bush, Block placedOn) {
        return ConfiguredFeatureUtil.register(BodaciousBerries.idString(name), Feature.RANDOM_PATCH,
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(((Block) bush).getDefaultState().with(bush.getAge(), bush.getMaxAge()))),
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
        return ConfiguredFeatureUtil.register(BodaciousBerries.idString(name), Feature.RANDOM_PATCH,
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(doubleBushFeature,
                        new DoubleBushFeatureConfig(BlockStateProvider.of(smallBush.getDefaultState().with(smallBush.getAge(), smallBush.getMaxAge())),
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
        return PlacedFeatureUtil.register(BodaciousBerries.idString(name), feature,
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
                // check all tags
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
        BiomeModifications.create(BodaciousBerries.id(name)).add(
                ModificationPhase.ADDITIONS,
                // decide if the biome should receive our feature
                predicate,
                // add feature to the biome under the step vegetal decoration
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, placedFeature.value())
        );
    }
}
