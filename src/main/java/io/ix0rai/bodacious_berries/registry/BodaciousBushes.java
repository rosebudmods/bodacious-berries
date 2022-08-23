package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;
import java.util.List;

public class BodaciousBushes {
    public static final TagKey<Block> BERRY_BUSHES_TAG = TagKey.of(Registry.BLOCK_KEY, new Identifier("c:berry_bushes"));
    public static final IntProperty AGE_4 = IntProperty.of("age", 0, 4);

    private static final VoxelShape SMALL_SWEET_BERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape LARGE_SWEET_BERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape SMALL_LINGONBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    private static final VoxelShape LARGE_LINGONBERRY = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape LARGE_STRAWBERRY = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    private static final VoxelShape LARGE_RASPBERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);
    private static final VoxelShape LARGE_CLOUDBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D);

    public static final AbstractBlock.Settings BERRY_BUSH_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque();

    // ids are contained in this class to avoid loading BodaciousItems until bushes are all created
    public static final Identifier SASKATOON_ID = BodaciousBerries.id("saskatoon_berries");
    public static final Identifier STRAWBERRY_ID = BodaciousBerries.id("strawberries");
    public static final Identifier RASPBERRY_ID = BodaciousBerries.id("raspberries");
    public static final Identifier BLACKBERRY_ID = BodaciousBerries.id("blackberries");
    public static final Identifier CHORUS_BERRY_ID = BodaciousBerries.id("chorus_berries");
    public static final Identifier RAINBERRY_ID = BodaciousBerries.id("rainberries");
    public static final Identifier LINGONBERRY_ID = BodaciousBerries.id("lingonberries");
    public static final Identifier GRAPE_ID = BodaciousBerries.id("grapes");
    public static final Identifier GOJI_BERRY_ID = BodaciousBerries.id("goji_berries");
    public static final Identifier GOOSEBERRY_ID = BodaciousBerries.id("gooseberries");
    public static final Identifier CLOUDBERRY_ID = BodaciousBerries.id("cloudberries");

    public static final ChorusBerryBush CHORUS_BERRY_BUSH = new ChorusBerryBush(CHORUS_BERRY_ID,
            SMALL_LINGONBERRY, LARGE_LINGONBERRY, 2);
    public static final DoubleBerryBush DOUBLE_SASKATOON_BERRY_BUSH = new DoubleBerryBush(
            SASKATOON_ID);
    public static final GrowingBerryBush SASKATOON_BERRY_BUSH = new GrowingBerryBush(SMALL_SWEET_BERRY, LARGE_SWEET_BERRY,
            DOUBLE_SASKATOON_BERRY_BUSH);
    public static final BasicBerryBush STRAWBERRY_BUSH = new BasicBerryBush.ThreeStageBush(STRAWBERRY_ID,
            SMALL_SWEET_BERRY, LARGE_STRAWBERRY, 1);
    public static final BasicBerryBush RASPBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(RASPBERRY_ID,
            SMALL_SWEET_BERRY, LARGE_RASPBERRY, 1, 1.0f);
    public static final BasicBerryBush BLACKBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(BLACKBERRY_ID,
            SMALL_SWEET_BERRY, LARGE_RASPBERRY, 1, 1.0f);
    public static final RainberryBush RAINBERRY_BUSH = new RainberryBush(RAINBERRY_ID,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2);
    public static final BasicBerryBush LINGONBERRY_BUSH = new BasicBerryBush.FourStageBush(LINGONBERRY_ID,
            SMALL_LINGONBERRY, LARGE_LINGONBERRY, 1);
    public static final BerryVine GRAPEVINE = new BerryVine(
            GRAPE_ID);
    public static final DoubleBerryBush DOUBLE_GOJI_BERRY_BUSH = new DoubleBerryBush(
            GOJI_BERRY_ID);
    public static final GrowingBerryBush GOJI_BERRY_BUSH = new GrowingBerryBush(SMALL_SWEET_BERRY, VoxelShapes.fullCube(),
            DOUBLE_GOJI_BERRY_BUSH);
    public static final BasicBerryBush GOOSEBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(GOOSEBERRY_ID,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 1, 2.0f);
    public static final CloudberryBush CLOUDBERRY_BUSH = new CloudberryBush(CLOUDBERRY_ID,
            SMALL_SWEET_BERRY, LARGE_CLOUDBERRY, 1);

    public static final List<Block> BERRY_BUSHES = new ArrayList<>();
    public static final List<Block> COLOUR_PROVIDER_EXCLUDED = new ArrayList<>();
    
    public static void register() {
        register("double_saskatoon_berry_bush", DOUBLE_SASKATOON_BERRY_BUSH);
        register("saskatoon_berry_bush", SASKATOON_BERRY_BUSH);
        register("strawberry_bush", STRAWBERRY_BUSH);
        register("raspberry_bush", RASPBERRY_BUSH);
        register("blackberry_bush", BLACKBERRY_BUSH);
        registerWithoutColourProvider("chorus_berry_bush", CHORUS_BERRY_BUSH);
        registerWithoutColourProvider("rainberry_bush", RAINBERRY_BUSH);
        register("lingonberry_bush", LINGONBERRY_BUSH);
        register("grapevine", GRAPEVINE);
        register("goji_berry_bush", GOJI_BERRY_BUSH);
        register("double_goji_berry_bush", DOUBLE_GOJI_BERRY_BUSH);
        register("gooseberry_bush", GOOSEBERRY_BUSH);
        registerWithoutColourProvider("cloudberry_bush", CLOUDBERRY_BUSH);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, BodaciousBerries.id(name), block);
        BERRY_BUSHES.add(block);
    }
    
    private static void registerWithoutColourProvider(String name, Block block) {
        register(name, block);
        COLOUR_PROVIDER_EXCLUDED.add(block);
    }
}
