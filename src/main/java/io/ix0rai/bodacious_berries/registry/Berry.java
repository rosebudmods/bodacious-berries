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
    SASKATOON_BERRIES(BiomeTags.FOREST, BiomeTags.TAIGA, BiomeTags.MOUNTAIN),
    STRAWBERRIES(BiomeTags.HAS_VILLAGE_PLAINS_STRUCTURE, BiomeTags.FOREST, BiomeTags.HAS_SWAMP_HUT_STRUCTURE),
    RASPBERRIES(BiomeTags.FOREST, BiomeTags.TAIGA, BiomeTags.MOUNTAIN),
    BLACKBERRIES(BiomeTags.FOREST, BiomeTags.TAIGA, BiomeTags.MOUNTAIN),
    CHORUS_BERRIES(BiomeTags.HAS_END_CITY_STRUCTURE),
    RAINBERRIES(BiomeTags.HAS_VILLAGE_PLAINS_STRUCTURE, BiomeTags.FOREST, BiomeTags.HAS_SWAMP_HUT_STRUCTURE),
    LINGONBERRIES(BiomeTags.FOREST, BiomeTags.TAIGA),
    GRAPES("grapevine", "grape_juice", BiomeTags.JUNGLE),
    GOJI_BERRIES(BiomeTags.MOUNTAIN),
    GOOSEBERRIES(BiomeTags.FOREST, BiomeTags.MOUNTAIN),
    CLOUDBERRIES(BiomeTags.MOUNTAIN);

    private final Identifier berryId;
    private final Identifier bushId;
    private final Identifier doubleBushId;
    private final Identifier juiceId;
    private final Set<TagKey<Biome>> biomes;

    @SafeVarargs
    Berry(TagKey<Biome>... biomes) {
        this.berryId = BodaciousBerries.id(this.name().toLowerCase());
        String berryName = berryId.getPath().replace("berries", "berry");
        this.bushId = BodaciousBerries.id(berryName + "_bush");
        this.doubleBushId = BodaciousBerries.id("double_" + bushId.getPath());
        this.juiceId = BodaciousBerries.id(berryName + "_juice");
        this.biomes = Set.of(biomes);
    }

    @SafeVarargs
    Berry(String bushId, String juiceId, TagKey<Biome>... biomes) {
        this.berryId = BodaciousBerries.id(this.name().toLowerCase());
        this.bushId = BodaciousBerries.id(bushId);
        this.doubleBushId = BodaciousBerries.id("double_" + bushId);
        this.juiceId = BodaciousBerries.id(juiceId);
        this.biomes = Set.of(biomes);
    }

    public Identifier id() {
        return berryId;
    }

    public Identifier bushId() {
        return bushId;
    }

    public Identifier doubleBushId() {
        return doubleBushId;
    }

    public Identifier juiceId() {
        return juiceId;
    }

    public Set<TagKey<Biome>> getBiomeTags() {
        return this.biomes;
    }

    @Override
    public String toString() {
        return this.id().getPath();
    }
}
