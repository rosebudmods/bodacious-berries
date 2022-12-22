package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.Set;

/**
 * exists so that {@link BodaciousItems} is not class-loaded until after {@link BodaciousBlocks}
 */
public enum Berry {
    SASKATOON_BERRIES(/*grass, common*/BiomeTags.FOREST, BiomeTags.TAIGA, BiomeTags.MOUNTAIN),
    STRAWBERRIES(/*grass, common*/BiomeTags.HAS_VILLAGE_PLAINS_STRUCTURE, BiomeTags.FOREST, BiomeTags.HAS_SWAMP_HUT_STRUCTURE),
    RASPBERRIES(/*grass, medium*/BiomeTags.FOREST, BiomeTags.TAIGA, BiomeTags.MOUNTAIN),
    BLACKBERRIES(/*grass, medium*/BiomeTags.FOREST, BiomeTags.TAIGA, BiomeTags.MOUNTAIN),
    CHORUS_BERRIES(/*end stone, rare, motion blocking*/BiomeTags.HAS_END_CITY_STRUCTURE),
    RAINBERRIES(/*grass, ultra*/BiomeTags.HAS_VILLAGE_PLAINS_STRUCTURE, BiomeTags.FOREST, BiomeTags.HAS_SWAMP_HUT_STRUCTURE),
    LINGONBERRIES(/*grass, medium*/BiomeTags.FOREST, BiomeTags.TAIGA),
    GRAPES(/*grass, medium*/BiomeTags.JUNGLE),
    GOJI_BERRIES(/*grass, rare*/BiomeTags.MOUNTAIN),
    GOOSEBERRIES(/*grass, m*/BiomeTags.FOREST, BiomeTags.MOUNTAIN),
    CLOUDBERRIES(/*grass, r*/BiomeTags.MOUNTAIN);

    private final Identifier identifier;
    private final Set<TagKey<Biome>> biomes;

    @SafeVarargs
    Berry(TagKey<Biome>... biomes) {
        this.identifier = BodaciousBerries.id(this.name().toLowerCase());
        this.biomes = Set.of(biomes);
    }

    public Identifier get() {
        return this.identifier;
    }

    public Set<TagKey<Biome>> getBiomeTags() {
        return this.biomes;
    }

    @Override
    public String toString() {
        return this.get().getPath();
    }
}
