package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.PlacementModifier;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * exists so that {@link BodaciousItems} is not class-loaded until after {@link BodaciousBlocks}
 */
public enum Berry {
    SASKATOON_BERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::common,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_MOUNTAIN
    ),
    STRAWBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::common,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE, BiomeTags.IS_FOREST, BiomeTags.SWAMP_HUT_HAS_STRUCTURE
    ),
    RASPBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::medium,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_MOUNTAIN

    ),
    BLACKBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::medium,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_MOUNTAIN
    ),
    CHORUS_BERRIES(
            Blocks.END_STONE,
            BodaciousBerries.CONFIG::rare,
            PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP,
            BiomeTags.END_CITY_HAS_STRUCTURE
    ),
    RAINBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::ultraRare,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE, BiomeTags.IS_FOREST, BiomeTags.SWAMP_HUT_HAS_STRUCTURE
    ),
    LINGONBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::medium,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA
    ),
    GRAPES(
            null
    ),
    GOJI_BERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::rare,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_MOUNTAIN
    ),
    GOOSEBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::medium,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_FOREST, BiomeTags.IS_MOUNTAIN
    ),
    CLOUDBERRIES(
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::rare,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_MOUNTAIN
    );

    private final Identifier identifier;
    private final GenerationSettings settings;

    @SafeVarargs
    Berry(Block placedOn, Supplier<Integer> rarity, PlacementModifier heightmap, TagKey<Biome>... genCategories) {
        this(new GenerationSettings(placedOn, rarity, heightmap, List.of(genCategories)));
    }

    Berry(GenerationSettings settings) {
        this.identifier = BodaciousBerries.id(this.toString());
        this.settings = settings;
    }

    public Identifier get() {
        return this.identifier;
    }

    public boolean usesDefaultGeneration() {
        return this.settings != null;
    }

    public Block getPlacedOn() {
        return this.settings.placedOn();
    }

    public int getRarity() {
        return this.settings.getRarity();
    }

    public PlacementModifier getHeightmap() {
        return this.settings.heightmap();
    }

    public List<TagKey<Biome>> getGenerationCategories() {
        return this.settings.genCategories();
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    private record GenerationSettings(Block placedOn, Supplier<Integer> rarity, PlacementModifier heightmap, List<TagKey<Biome>> genCategories) {
        public int getRarity() {
            return this.rarity.get();
        }
    }
}
