package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BasicBerryBush;
import io.ix0rai.bodaciousberries.block.BerryVine;
import io.ix0rai.bodaciousberries.block.ChorusBerryBush;
import io.ix0rai.bodaciousberries.block.CloudberryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import io.ix0rai.bodaciousberries.block.RainberryBush;
import io.ix0rai.bodaciousberries.block.SpikedBerryBush;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class Bushes {
    public static final IntProperty AGE_4 = IntProperty.of("age", 0, 4);

    private static final VoxelShape SMALL_SWEET_BERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape LARGE_SWEET_BERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape SMALL_LINGONBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    private static final VoxelShape LARGE_LINGONBERRY = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape LARGE_STRAWBERRY = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    private static final VoxelShape LARGE_RASPBERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);
    private static final VoxelShape LARGE_CLOUDBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D);

    public static final AbstractBlock.Settings BERRY_BUSH_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque();

    //bushes
    public static final ChorusBerryBush CHORUS_BERRY_BUSH = new ChorusBerryBush(Berries.CHORUS_BERRIES,
            SMALL_LINGONBERRY, LARGE_LINGONBERRY, 2);
    public static final DoubleBerryBush DOUBLE_SASKATOON_BERRY_BUSH = new DoubleBerryBush(
            Berries.SASKATOON_BERRIES);
    public static final GrowingBerryBush SASKATOON_BERRY_BUSH = new GrowingBerryBush(SMALL_SWEET_BERRY, LARGE_SWEET_BERRY,
            DOUBLE_SASKATOON_BERRY_BUSH);
    public static final BasicBerryBush STRAWBERRY_BUSH = new BasicBerryBush.ThreeStageBush(Berries.STRAWBERRY,
            SMALL_SWEET_BERRY, LARGE_STRAWBERRY, 1);
    public static final BasicBerryBush RASPBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(Berries.RASPBERRIES,
            SMALL_SWEET_BERRY, LARGE_RASPBERRY, 1, 1.0f);
    public static final BasicBerryBush BLACKBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(Berries.BLACKBERRIES,
            SMALL_SWEET_BERRY, LARGE_RASPBERRY, 1, 1.0f);
    public static final RainberryBush RAINBERRY_BUSH = new RainberryBush(Berries.RAINBERRY,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2);
    public static final BasicBerryBush LINGONBERRY_BUSH = new BasicBerryBush.FourStageBush(Berries.LINGONBERRIES,
            SMALL_LINGONBERRY, LARGE_LINGONBERRY, 1);
    public static final BerryVine GRAPEVINE = new BerryVine(
            Berries.GRAPES);
    public static final DoubleBerryBush DOUBLE_GOJI_BERRY_BUSH = new DoubleBerryBush(
            Berries.GOJI_BERRIES);
    public static final GrowingBerryBush GOJI_BERRY_BUSH = new GrowingBerryBush(SMALL_SWEET_BERRY, VoxelShapes.fullCube(),
            DOUBLE_GOJI_BERRY_BUSH);
    public static final BasicBerryBush GOOSEBERRY_BUSH = new SpikedBerryBush.SpikyThreeStageBush(Berries.GOOSEBERRIES,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 1, 2.0f);
    public static final CloudberryBush CLOUDBERRY_BUSH = new CloudberryBush(Berries.CLOUDBERRIES,
            SMALL_SWEET_BERRY, LARGE_CLOUDBERRY, 1);

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
        register("cloudberry_bush", CLOUDBERRY_BUSH);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, Bodaciousberries.id(name), block);
    }
}
