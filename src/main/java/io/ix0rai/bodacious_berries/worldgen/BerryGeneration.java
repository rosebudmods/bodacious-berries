package io.ix0rai.bodacious_berries.worldgen;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.BlockPredicateFilterPlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.TreePlacedFeatures;
import net.minecraft.world.gen.feature.WeightedPlacedFeature;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class BerryGeneration {
    public static final Feature<DefaultFeatureConfig> GRAPEVINE_FEATURE = Registry.register(Registry.FEATURE, BodaciousBerries.id("grapevines"), new GrapevineFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DoubleBushFeatureConfig> DOUBLE_BUSH_FEATURE = Registry.register(Registry.FEATURE, BodaciousBerries.id("double_bush"), new DoubleBushFeature(DoubleBushFeatureConfig.CODEC));
    // todo improve appearance, custom blocks
    public static final TreeFeatureConfig ACAI_TREE_CONFIG = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG.getDefaultState()),
            new StraightTrunkPlacer(2, 2, 0),
            BlockStateProvider.of(Blocks.FLOWERING_AZALEA_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().build();

    private static Holder<PlacedFeature> placedAcaiTrees;

    public static void generate() {
        register();
        place();
        BerryBushPatchGen.register();
    }

    private static void register() {
        var configuredGrapevine = ConfiguredFeatureUtil.register(BodaciousBerries.idString("grapevine_configured_feature"), GRAPEVINE_FEATURE, DefaultFeatureConfig.INSTANCE);
        PlacedFeatureUtil.register(BodaciousBerries.idString("grapevine_placed_feature"), configuredGrapevine,
                List.of(CountPlacementModifier.create(127), HeightRangePlacementModifier.createUniform(YOffset.fixed(50), YOffset.fixed(255)), BiomePlacementModifier.getInstance(), RarityFilterPlacementModifier.create(BodaciousBerries.CONFIG.medium()))
        );

        var configuredAcaiTree = BuiltinRegistries.registerExact(
                BuiltinRegistries.CONFIGURED_FEATURE,
                BodaciousBerries.idString("acai_configured_feature"),
                new ConfiguredFeature<>(Feature.TREE, ACAI_TREE_CONFIG)
        );

        var placedAcaiTree = BuiltinRegistries.register(
                BuiltinRegistries.PLACED_FEATURE,
                BodaciousBerries.id("acai_placed_feature"),
                new PlacedFeature(Holder.upcast(configuredAcaiTree),
                        new ArrayList<>()
                )
        );

        Holder<ConfiguredFeature<RandomFeatureConfig, ?>> acaiTreesConfigured = BuiltinRegistries.registerExact(
                BuiltinRegistries.CONFIGURED_FEATURE,
                BodaciousBerries.idString("acai_trees"),
                new ConfiguredFeature<>(
                        Feature.RANDOM_SELECTOR,
                        new RandomFeatureConfig(
                                // todo: add more tree options
                                List.of(new WeightedPlacedFeature(placedAcaiTree, 1.0f)),
                                TreePlacedFeatures.OAK_CHECKED
                        )
                )
        );

        placedAcaiTrees = BuiltinRegistries.register(
                BuiltinRegistries.PLACED_FEATURE,
                BodaciousBerries.id("acai_trees_placed"),
                new PlacedFeature(Holder.upcast(acaiTreesConfigured),
                        List.of(
                                PlacedFeatureUtil.createCountExtraModifier(10, 0.1f, 1),
                                InSquarePlacementModifier.getInstance(),
                                PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP,
                                BlockPredicateFilterPlacementModifier.create(BlockPredicate.m_bmtpvlfi(Direction.DOWN.getVector(), BlockTags.DIRT))
                        )
                )
        );


    }


    private static void place() {
        // todo
        BiomeModifications.create(BodaciousBerries.id("tree")).add(
                ModificationPhase.ADDITIONS,
                // decide if the biome should receive our feature
                context -> true,
                // add feature to the biome under the step vegetal decoration
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, placedAcaiTrees.value())
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
                        if (entry.isIn(tag)) {
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

    public static void generate(BooleanSupplier checks, String name, List<TagKey<Biome>> tags, Holder<PlacedFeature> placedFeature) {
        if (placedFeature != null && checks.getAsBoolean()) {
            BerryGeneration.generateBerryPatches(name, tags, placedFeature);
        }
    }
}
