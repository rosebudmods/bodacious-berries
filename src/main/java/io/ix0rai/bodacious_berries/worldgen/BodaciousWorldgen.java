package io.ix0rai.bodacious_berries.worldgen;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.registry.Berry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.function.Predicate;

public class BodaciousWorldgen {
    public static final Feature<DoubleBushFeatureConfig> DOUBLE_BUSH_FEATURE = new DoubleBushFeature(DoubleBushFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> GRAPEVINE_FEATURE = new GrapevineFeature(DefaultFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE_WORLDGEN, BodaciousBerries.id("double_bush"), DOUBLE_BUSH_FEATURE);
        Registry.register(Registries.FEATURE_WORLDGEN, BodaciousBerries.id("grapevine"), GRAPEVINE_FEATURE);

        for (Berry berry : Berry.values()) {
            if (BodaciousBerries.CONFIG.isGenerating(berry)) {
                createBiomeModification(berry);
            }
        }
    }

    private static void createBiomeModification(Berry berry) {
        Identifier id = BodaciousBerries.id("patch_" +  berry.get().getPath());
        BiomeModifications.create(id)
                .add(
                        ModificationPhase.ADDITIONS,
                        getPredicate(berry),
                        context -> context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, id))
                );
    }

    private static Predicate<BiomeSelectionContext> getPredicate(Berry berry) {
        return context -> {
            Holder<Biome> entry = context.getBiomeRegistryEntry();
            for (TagKey<Biome> tag : berry.getBiomeTags()) {
                if (entry.isIn(tag)) {
                    return true;
                }
            }

            return false;
        };
    }
}
