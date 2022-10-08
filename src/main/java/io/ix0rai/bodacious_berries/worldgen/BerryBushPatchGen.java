package io.ix0rai.bodacious_berries.worldgen;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.BerryBush;
import io.ix0rai.bodacious_berries.block.DoubleBerryBush;
import io.ix0rai.bodacious_berries.block.GrowingBerryBush;
import io.ix0rai.bodacious_berries.registry.Berry;
import io.ix0rai.bodacious_berries.registry.BodaciousBushes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Block;
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
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import static io.ix0rai.bodacious_berries.BodaciousBerries.CONFIG;

// note: all casting done in this class is safe, but it has to be performed due to array creation limitations
@SuppressWarnings("unchecked")
public class BerryBushPatchGen {
    public static Feature<DefaultFeatureConfig> grapevineFeature;
    public static Feature<DoubleBushFeatureConfig> doubleBushFeature;

    public static Holder<?>[] berryPatches = new Holder[Berry.values().length];
    public static Holder<?>[] placedBerryPatches = new Holder[Berry.values().length];

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
        for (Berry berry : Berry.values()) {
            String featureName = "patch_" + berry.toString();
            // grapevine needs special treatment as it's not random patch
            var configuredFeature = berry == Berry.GRAPES ?
                    ConfiguredFeatureUtil.register(BodaciousBerries.idString(featureName), grapevineFeature, DefaultFeatureConfig.INSTANCE) :
                    berryPatchConfiguredFeature(
                            featureName,
                            BodaciousBushes.getBushFor(berry),
                            BodaciousBushes.getDoubleBushFor(berry),
                            berry.getPlacedOn()
                    );
            berryPatches[berry.ordinal()] = configuredFeature;
        }
    }

    private static void registerPlacedFeatures() {
        for (Berry berry : Berry.values()) {
            String featureName = "patch_" + berry.toString() + "_placed";
            Holder<ConfiguredFeature<?, ?>> feature = (Holder<ConfiguredFeature<?, ?>>) berryPatches[berry.ordinal()];

            // again, grapevine needs special treatment as it's not random patch
            var placedFeature = berry == Berry.GRAPES ?
                    PlacedFeatureUtil.register(BodaciousBerries.idString("patch_grapevine_placed"), feature,
                            List.of(CountPlacementModifier.create(127), HeightRangePlacementModifier.createUniform(YOffset.fixed(50), YOffset.fixed(255)), BiomePlacementModifier.getInstance(), RarityFilterPlacementModifier.create(CONFIG.medium()))
                    ) :
                    berryPatchPlacedFeature(
                            featureName,
                            berry.getRarity(),
                            feature,
                            berry.getHeightmap()
                    );
            placedBerryPatches[berry.ordinal()] = placedFeature;
        }
    }

    private static void placePatches() {
        for (Berry berry : Berry.values()) {
            String featureName = "place_" + berry.toString() + "_patches";
            Holder<PlacedFeature> placedFeature = (Holder<PlacedFeature>) placedBerryPatches[berry.ordinal()];

            // no specialness for grapevines this time >:)
            generate(
                    () -> CONFIG.isGenerating(berry),
                    featureName,
                    berry.getGenerationCategories(),
                    placedFeature
            );
        }
    }

    private static void generate(BooleanSupplier checks, String name, List<TagKey<Biome>> tags, Holder<PlacedFeature> placedFeature) {
        if (placedFeature != null && checks.getAsBoolean()) {
            generateBerryPatches(name, tags, placedFeature);
        }
    }

    /**
     * creates and registers a berry patch feature, with the default age of the berry bush being the maximum age of said berry bush
     * <br>also generates the double version of said berry bush alongside it if the berry bush has a double version
     * @param name the name of the feature
     * @param smallBush the bush to generate
     * @param tallBush the tall version of the bush
     * @param placedOn which block the bush should be placed on
     * @return a berry patch configured feature
     */
    public static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, BerryBush smallBush, BerryBush tallBush, Block placedOn) {
        RandomPatchFeatureConfig feature = tallBush instanceof DoubleBerryBush && smallBush instanceof GrowingBerryBush ?
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(doubleBushFeature,
                        new DoubleBushFeatureConfig(BlockStateProvider.of(((Block) smallBush).getDefaultState().with(smallBush.getAge(), smallBush.getMaxAge())),
                                BlockStateProvider.of(((Block) tallBush).getDefaultState().with(DoubleBerryBush.AGE, tallBush.getMaxAge()))),
                        List.of(placedOn)
                ) :
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(((Block) smallBush).getDefaultState().with(smallBush.getAge(), smallBush.getMaxAge()))),
                        List.of(placedOn)
                );

        return ConfiguredFeatureUtil.register(BodaciousBerries.idString(name), Feature.RANDOM_PATCH, feature);
    }

    /**
     * creates and registers a placed berry patch feature
     * @param name the name of the feature
     * @param rarity the rarity of the feature: lower is more common
     * @param feature the feature to place
     * @return a placed berry patch feature
     */
    public static Holder<PlacedFeature> berryPatchPlacedFeature(String name, int rarity, Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier heightMap) {
        return rarity == 0 ? null : PlacedFeatureUtil.register(BodaciousBerries.idString(name), feature,
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
