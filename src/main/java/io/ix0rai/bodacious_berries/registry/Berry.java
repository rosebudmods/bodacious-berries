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
            Blocks.GRASS_BLOCK,
            BodaciousBerries.CONFIG::medium,
            PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP,
            BiomeTags.IS_JUNGLE
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
    private final Block placedOn;
    private final Supplier<Integer> rarity;
    private final PlacementModifier heightmap;
    private final List<TagKey<Biome>> genCategories;

    @SafeVarargs
    Berry(Block placedOn, Supplier<Integer> rarity, PlacementModifier heightmap, TagKey<Biome>... genCategories) {
        this.identifier = BodaciousBerries.id(this.toString());
        this.placedOn = placedOn;
        this.rarity = rarity;
        this.heightmap = heightmap;
        this.genCategories = List.of(genCategories);
    }

    public Identifier get() {
        return this.identifier;
    }

    public Block getPlacedOn() {
        return this.placedOn;
    }

    public int getRarity() {
        return this.rarity.get();
    }

    public PlacementModifier getHeightmap() {
        return this.heightmap;
    }

    public List<TagKey<Biome>> getGenerationCategories() {
        return this.genCategories;
    }

    public String langKey() {
        StringBuilder lowercase = new StringBuilder(this.toString());

        for (int i = 0; i < lowercase.length(); i++) {
            if (lowercase.charAt(i) == '_') {
                lowercase.setCharAt(i + 1, Character.toUpperCase(lowercase.charAt(i + 1)));
                lowercase.deleteCharAt(i);
            }
        }

        return lowercase.toString();
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
