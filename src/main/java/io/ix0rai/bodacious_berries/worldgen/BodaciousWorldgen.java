package io.ix0rai.bodacious_berries.worldgen;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.registry.Berry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.Holder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

import java.util.function.Predicate;

public class BodaciousWorldgen {
    public static void register() {
        for (Berry berry : Berry.values()) {
            // todo only if generating
            createBiomeModification(berry);
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
