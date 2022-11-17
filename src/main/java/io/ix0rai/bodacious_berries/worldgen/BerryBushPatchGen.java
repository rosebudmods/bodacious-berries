package io.ix0rai.bodacious_berries.worldgen;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.BerryBush;
import io.ix0rai.bodacious_berries.block.DoubleBerryBush;
import io.ix0rai.bodacious_berries.block.GrowingBerryBush;
import io.ix0rai.bodacious_berries.registry.Berry;
import io.ix0rai.bodacious_berries.registry.BodaciousBushes;
import net.minecraft.block.Block;
import net.minecraft.util.Holder;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacementModifier;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

import static io.ix0rai.bodacious_berries.BodaciousBerries.CONFIG;

// note: all casting done in this class is safe, but it has to be performed due to array creation limitations
@SuppressWarnings("unchecked")
public class BerryBushPatchGen {
    public static Holder<?>[] berryPatches = new Holder[Berry.values().length];
    public static Holder<?>[] placedBerryPatches = new Holder[Berry.values().length];

    public static void register() {
        registerConfiguredFeatures();
        registerPlacedFeatures();
        placePatches();
    }

    private static void registerConfiguredFeatures() {
        for (Berry berry : Berry.values()) {
            if (berry.usesDefaultGeneration()) {
                String featureName = "patch_" + berry;
                // grapevine needs special treatment as it's not random patch
                var configuredFeature = berryPatchConfiguredFeature(
                        featureName,
                        BodaciousBushes.getBushFor(berry),
                        BodaciousBushes.getDoubleBushFor(berry),
                        berry.getPlacedOn()
                );

                berryPatches[berry.ordinal()] = configuredFeature;
            }
        }
    }

    private static void registerPlacedFeatures() {
        for (Berry berry : Berry.values()) {
            if (berry.usesDefaultGeneration()) {
                String featureName = "patch_" + berry + "_placed";
                Holder<ConfiguredFeature<?, ?>> feature = (Holder<ConfiguredFeature<?, ?>>) berryPatches[berry.ordinal()];

                // again, grapevine needs special treatment as it's not random patch
                var placedFeature = berryPatchPlacedFeature(
                        featureName,
                        berry.getRarity(),
                        feature,
                        berry.getHeightmap()
                );

                placedBerryPatches[berry.ordinal()] = placedFeature;
            }
        }
    }

    private static void placePatches() {
        for (Berry berry : Berry.values()) {
            if (berry.usesDefaultGeneration()) {
                String featureName = "place_" + berry + "_patches";
                Holder<PlacedFeature> placedFeature = (Holder<PlacedFeature>) placedBerryPatches[berry.ordinal()];

                BerryGeneration.generate(
                        () -> CONFIG.isGenerating(berry),
                        featureName,
                        berry.getGenerationCategories(),
                        placedFeature
                );
            }
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
    private static Holder<ConfiguredFeature<RandomPatchFeatureConfig, ?>> berryPatchConfiguredFeature(String name, BerryBush smallBush, BerryBush tallBush, Block placedOn) {
        RandomPatchFeatureConfig feature = tallBush instanceof DoubleBerryBush && smallBush instanceof GrowingBerryBush ?
                ConfiguredFeatureUtil.createRandomPatchFeatureConfig(BerryGeneration.DOUBLE_BUSH_FEATURE,
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
    private static Holder<PlacedFeature> berryPatchPlacedFeature(String name, int rarity, Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier heightMap) {
        return rarity == 0 ? null : PlacedFeatureUtil.register(BodaciousBerries.idString(name), feature,
                List.of(
                        RarityFilterPlacementModifier.create(rarity),
                        InSquarePlacementModifier.getInstance(),
                        heightMap
                )
        );
    }
}
