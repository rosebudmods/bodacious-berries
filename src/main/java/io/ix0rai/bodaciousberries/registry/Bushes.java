package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BasicBerryBush;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;

public class Bushes {
    private static final VoxelShape SMALL_SWEET_BERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape LARGE_SWEET_BERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    private static final VoxelShape LARGE_STRAWBERRY = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 13.0D, 10.0D, 13.0D);

    private static final AbstractBlock.Settings BERRY_BUSH_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH);

    public static final DoubleBerryBush DOUBLE_SASKATOON_BERRY_BUSH = new DoubleBerryBush(BERRY_BUSH_SETTINGS, BodaciousItems.SASKATOON_BERRIES, BodaciousItems.UNRIPE_SASKATOON_BERRIES,
            3);

    //bushes
    public static final GrowingBerryBush SASKATOON_BERRY_BUSH = new GrowingBerryBush(BERRY_BUSH_SETTINGS,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2, DOUBLE_SASKATOON_BERRY_BUSH);
    public static final BasicBerryBush STRAWBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, BodaciousItems.STRAWBERRIES,
                3, SMALL_SWEET_BERRY, LARGE_STRAWBERRY, 1);
    public static final BasicBerryBush RASPBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, BodaciousItems.RASPBERRIES,
            4, SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2);
    public static final BasicBerryBush BLACKBERRY_BUSH = new BasicBerryBush(BERRY_BUSH_SETTINGS, BodaciousItems.BLACKBERRIES,
            4, SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2);

    public static void registerBushes() {
        SASKATOON_BERRY_BUSH.setBerryType(BodaciousItems.SASKATOON_BERRIES);
        SASKATOON_BERRY_BUSH.setUnripeBerryType(BodaciousItems.UNRIPE_SASKATOON_BERRIES);
        DOUBLE_SASKATOON_BERRY_BUSH.setBerryType(BodaciousItems.SASKATOON_BERRIES);
        DOUBLE_SASKATOON_BERRY_BUSH.setUnripeBerryType(BodaciousItems.SASKATOON_BERRIES);
        STRAWBERRY_BUSH.setBerryType(BodaciousItems.STRAWBERRIES);
        RASPBERRY_BUSH.setBerryType(BodaciousItems.RASPBERRIES);
        BLACKBERRY_BUSH.setBerryType(BodaciousItems.BLACKBERRIES);

        register("double_saskatoon_berry_bush", DOUBLE_SASKATOON_BERRY_BUSH);
        register("saskatoon_berry_bush", SASKATOON_BERRY_BUSH);
        register("strawberry_bush", STRAWBERRY_BUSH);
        register("raspberry_bush", RASPBERRY_BUSH);
        register("blackberry_bush", BLACKBERRY_BUSH);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, Bodaciousberries.getIdentifier(name), block);
    }
}
