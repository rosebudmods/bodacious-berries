package io.ix0rai.bodaciousberries.registry;

import adudecalledleo.dfubuddy.api.ModDataFixes;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import io.ix0rai.bodaciousberries.BodaciousBerries;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.fix.ItemNameFix;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;

import java.util.Objects;

public final class BodaciousDataFixers {
    /**
     * update to 1 - change mod id to bodacious_berries, make all berries plural
     */
    public static final int DATA_VERSION = 1;

    private static final String V0_MOD_ID = "bodaciousberries";

    public static void register() {
        DataFixerBuilder builder = new DataFixerBuilder(DATA_VERSION);
        builder.addSchema(0, ModDataFixes.MOD_SCHEMA);
        Schema schemaV1 = builder.addSchema(1, Schema::new);
        SchemaV1Data.initialize(builder, schemaV1);
        DataFixer fixer = builder.buildOptimized(Util.getBootstrapExecutor());
        ModDataFixes.registerFixer(BodaciousBerries.MOD_ID, DATA_VERSION, fixer);
    }

    private static final class SchemaV1Data {
        /**
         * boolean value denotes whether the fixer is an item
         * false - block
         * true - item
         * null - both
         */
        public static final Object2ReferenceOpenHashMap<String, Pair<String, Boolean>> RENAMES =
                Util.make(new Object2ReferenceOpenHashMap<>(), map -> {
                    // blocks
                    map.put("berry_harvester", new Pair<>("berry_harvester", null));
                    map.put("juicer", new Pair<>("juicer", null));
                    map.put("chorus_berry_bush", new Pair<>("chorus_berry_bush", false));
                    map.put("double_saskatoon_berry_bush", new Pair<>("double_saskatoon_berry_bush", false));
                    map.put("saskatoon_berry_bush", new Pair<>("saskatoon_berry_bush", false));
                    map.put("strawberry_bush", new Pair<>("strawberry_bush", false));
                    map.put("raspberry_bush", new Pair<>("raspberry_bush", false));
                    map.put("blackberry_bush", new Pair<>("blackberry_bush", false));
                    map.put("rainberry_bush", new Pair<>("rainberry_bush", false));
                    map.put("lingonberry_bush", new Pair<>("lingonberry_bush", false));
                    map.put("grapevine", new Pair<>("grapevine", false));
                    map.put("double_goji_berry_bush", new Pair<>("double_goji_berry_bush", false));
                    map.put("goji_berry_bush", new Pair<>("goji_berry_bush", false));
                    map.put("gooseberry_bush", new Pair<>("gooseberry_bush", false));
                    map.put("cloudberry_bush", new Pair<>("cloudberry_bush", false));

                    // berries - no change
                    map.put("saskatoon_berries", new Pair<>("saskatoon_berries", true));
                    map.put("blackberries", new Pair<>("blackberries", true));
                    map.put("raspberries", new Pair<>("raspberries", true));
                    map.put("cloudberries", new Pair<>("cloudberries", true));
                    map.put("goji_berries", new Pair<>("goji_berries", true));
                    map.put("gooseberries", new Pair<>("gooseberries", true));
                    map.put("grapes", new Pair<>("grapes", true));
                    map.put("lingonberries", new Pair<>("lingonberries", true));

                    // berries - actual renames
                    map.put("rainberry", new Pair<>("rainberries", true));
                    map.put("strawberry", new Pair<>("strawberries", true));

                    // juices
                    map.put("chorus_berry_juice", new Pair<>("chorus_berry_juice", true));
                    map.put("chorus_berry_juice_badlands", new Pair<>("chorus_berry_juice_badlands", true));
                    map.put("chorus_berry_juice_birch_forest", new Pair<>("chorus_berry_juice_birch_forest", true));
                    map.put("chorus_berry_juice_dark_forest", new Pair<>("chorus_berry_juice_dark_forest", true));
                    map.put("chorus_berry_juice_desert", new Pair<>("chorus_berry_juice_desert", true));
                    map.put("chorus_berry_juice_dripstone_caves", new Pair<>("chorus_berry_juice_dripstone_caves", true));
                    map.put("chorus_berry_juice_flower_forest", new Pair<>("chorus_berry_juice_flower_forest", true));
                    map.put("chorus_berry_juice_forest", new Pair<>("chorus_berry_juice_forest", true));
                    map.put("chorus_berry_juice_jungle", new Pair<>("chorus_berry_juice_jungle", true));
                    map.put("chorus_berry_juice_lush_caves", new Pair<>("chorus_berry_juice_lush_caves", true));
                    map.put("chorus_berry_juice_meadow", new Pair<>("chorus_berry_juice_meadow", true));
                    map.put("chorus_berry_juice_mushroom_fields", new Pair<>("chorus_berry_juice_mushroom_fields", true));
                    map.put("chorus_berry_juice_ocean", new Pair<>("chorus_berry_juice_ocean", true));
                    map.put("chorus_berry_juice_plains", new Pair<>("chorus_berry_juice_plains", true));
                    map.put("chorus_berry_juice_savanna", new Pair<>("chorus_berry_juice_savanna", true));
                    map.put("chorus_berry_juice_snowy_slopes", new Pair<>("chorus_berry_juice_snowy_slopes", true));
                    map.put("chorus_berry_juice_sunflower_plains", new Pair<>("chorus_berry_juice_sunflower_plains", true));
                    map.put("chorus_berry_juice_swamp", new Pair<>("chorus_berry_juice_swamp", true));
                    map.put("chorus_berry_juice_taiga", new Pair<>("chorus_berry_juice_taiga", true));
                    map.put("blackberry_juice", new Pair<>("blackberry_juice", true));
                    map.put("cloudberry_juice", new Pair<>("cloudberry_juice", true));
                    map.put("dubious_juice", new Pair<>("dubious_juice", true));
                    map.put("end_blend", new Pair<>("end_blend", true));
                    map.put("glow_berry_juice", new Pair<>("glow_berry_juice", true));
                    map.put("goji_berry_blend", new Pair<>("goji_berry_blend", true));
                    map.put("goji_berry_juice", new Pair<>("goji_berry_juice", true));
                    map.put("gooseberry_juice", new Pair<>("gooseberry_juice", true));
                    map.put("gooseberry_rum", new Pair<>("gooseberry_rum", true));
                    map.put("grape_juice", new Pair<>("grape_juice", true));
                    map.put("lingonberry_juice", new Pair<>("lingonberry_juice", true));
                    map.put("opposite_juice", new Pair<>("opposite_juice", true));
                    map.put("purple_delight", new Pair<>("purple_delight", true));
                    map.put("rainberry_blend", new Pair<>("rainberry_blend", true));
                    map.put("rainberry_juice", new Pair<>("rainberry_juice", true));
                    map.put("raspberry_juice", new Pair<>("raspberry_juice", true));
                    map.put("red_juice", new Pair<>("red_juice", true));
                    map.put("saskatoon_berry_juice", new Pair<>("saskatoon_berry_juice", true));
                    map.put("strawberry_juice", new Pair<>("strawberry_juice", true));
                    map.put("sweet_berry_juice", new Pair<>("sweet_berry_juice", true));
                    map.put("traffic_light_juice", new Pair<>("traffic_light_juice", true));
                    map.put("vanilla_delight", new Pair<>("vanilla_delight", true));
                });

        public static void initialize(DataFixerBuilder builder, Schema targetSchema) {
            for (Object2ReferenceMap.Entry<String, Pair<String, Boolean>> entry : RENAMES.object2ReferenceEntrySet()) {
                String oldId = V0_MOD_ID + ":" + entry.getKey();
                String newId = BodaciousBerries.MOD_ID + ":" + entry.getValue().getLeft();

                if (entry.getValue().getRight() == null || entry.getValue().getRight().equals(Boolean.FALSE)) {
                    builder.addFixer(BlockNameFix.create(targetSchema, "change mod id for block: " + oldId, inputName ->
                            Objects.equals(IdentifierNormalizingSchema.normalize(inputName), oldId) ? newId : inputName));
                }

                if (entry.getValue().getRight() == null || entry.getValue().getRight().equals(Boolean.TRUE)) {
                    builder.addFixer(ItemNameFix.create(targetSchema, "change mod id for item: " + oldId, inputName ->
                            Objects.equals(IdentifierNormalizingSchema.normalize(inputName), oldId) ? newId : inputName));
                }
            }
        }
    }
}
