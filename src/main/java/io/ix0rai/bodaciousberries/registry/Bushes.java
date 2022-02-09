package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BasicBerryBush;
import io.ix0rai.bodaciousberries.block.BerryVine;
import io.ix0rai.bodaciousberries.block.ChorusBerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import io.ix0rai.bodaciousberries.block.RainberryBush;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;

public class Bushes {
    private static final VoxelShape SMALL_SWEET_BERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape LARGE_SWEET_BERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape SMALL_LINGONBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    private static final VoxelShape LARGE_LINGONBERRY = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape LARGE_STRAWBERRY = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);

    private static final AbstractBlock.Settings BERRY_BUSH_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH);

    //bushes
    public static final ChorusBerryBush CHORUS_BERRY_BUSH = new ChorusBerryBush(BERRY_BUSH_SETTINGS, Berries.CHORUS_BERRIES,
            4, SMALL_LINGONBERRY, LARGE_LINGONBERRY, 2);
    public static final DoubleBerryBush DOUBLE_SASKATOON_BERRY_BUSH = new DoubleBerryBush(BERRY_BUSH_SETTINGS,
            Berries.SASKATOON_BERRIES);
    public static final GrowingBerryBush SASKATOON_BERRY_BUSH = new GrowingBerryBush(BERRY_BUSH_SETTINGS,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2, DOUBLE_SASKATOON_BERRY_BUSH);
    public static final BasicBerryBush STRAWBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, Berries.STRAWBERRY,
            3, SMALL_SWEET_BERRY, LARGE_STRAWBERRY, 1);
    public static final BasicBerryBush RASPBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, Berries.RASPBERRIES,
            4, SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 1);
    public static final BasicBerryBush BLACKBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, Berries.BLACKBERRIES,
            4, SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 1);
    public static final RainberryBush RAINBERRY_BUSH = new RainberryBush(BERRY_BUSH_SETTINGS, Berries.RAINBERRY,
            4, SMALL_SWEET_BERRY, LARGE_STRAWBERRY, 2);
    public static final BasicBerryBush LINGONBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, Berries.LINGONBERRIES,
            4, SMALL_LINGONBERRY, LARGE_LINGONBERRY, 1);
    public static final BerryVine GRAPEVINE = new BerryVine(BERRY_BUSH_SETTINGS,
            Berries.GRAPES);
    public static final DoubleBerryBush DOUBLE_GOJI_BERRY_BUSH = new DoubleBerryBush(BERRY_BUSH_SETTINGS,
            Berries.GOJI_BERRIES);
    public static final GrowingBerryBush GOJI_BERRY_BUSH = new GrowingBerryBush(BERRY_BUSH_SETTINGS,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2, DOUBLE_GOJI_BERRY_BUSH);
    public static final BasicBerryBush GOOSEBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, Berries.GOOSEBERRIES, 3,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 1);

    public static void registerBushes() {
        register("double_saskatoon_berry_bush", DOUBLE_SASKATOON_BERRY_BUSH);
        register("saskatoon_berry_bush", SASKATOON_BERRY_BUSH);
        register("strawberry_bush", STRAWBERRY_BUSH);
        register("raspberry_bush", RASPBERRY_BUSH);
        register("blackberry_bush", BLACKBERRY_BUSH);
        register("chorus_berry_bush", CHORUS_BERRY_BUSH);
        register("rainberry_bush", RAINBERRY_BUSH);
        register("lingonberry_bush", LINGONBERRY_BUSH);
        register("grapevine", GRAPEVINE);
        register("goji_berry_bush", GOJI_BERRY_BUSH);
        register("double_goji_berry_bush", DOUBLE_GOJI_BERRY_BUSH);
        register("gooseberry_bush", GOOSEBERRY_BUSH);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, Bodaciousberries.getIdentifier(name), block);
    }
}
