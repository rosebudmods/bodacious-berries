package io.ix0rai.bodaciousberries.worldgen;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
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
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class BerryBushPatchGen {
    public static ConfiguredFeature<?, ?> PATCH_SASKATOON_BERRY;
    public static PlacedFeature PATCH_SASKATOON_BERRY_PLACED;

    public static void registerFeatures() {
        PATCH_SASKATOON_BERRY = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Bodaciousberries.getIdentifier("patch_saskatoon_berry"),
                Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Bushes.SASKATOON_BERRY_BUSH.getDefaultState().with(GrowingBerryBush.BERRY_AGE, 3)))),
                        List.of(Blocks.GRASS_BLOCK)
                ))
        );

        PATCH_SASKATOON_BERRY_PLACED = Registry.register(BuiltinRegistries.PLACED_FEATURE, Bodaciousberries.getIdentifier("patch_saskatoon_berry_placed"),
                PATCH_SASKATOON_BERRY.withPlacement(
                        RarityFilterPlacementModifier.of(32),
                    SquarePlacementModifier.of(),
                    PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                    BiomePlacementModifier.of()
                )
        );

        BiomeModifications.create(Bodaciousberries.getIdentifier("add_saskatoon_berry_patches")).add(
                ModificationPhase.ADDITIONS,
                //decide if the biome should receive our feature
                context -> {
                    Biome.Category category = context.getBiome().getCategory();
                    return category != Biome.Category.NETHER && category != Biome.Category.THEEND && category != Biome.Category.NONE;
                },
                // add feature to the biome under the step vegetal decoration
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, PATCH_SASKATOON_BERRY_PLACED));

    }
}