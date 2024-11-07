package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.BasicBerryBush;
import io.ix0rai.bodacious_berries.block.BerryBush;
import io.ix0rai.bodacious_berries.block.BerryVine;
import io.ix0rai.bodacious_berries.block.ChorusBerryBush;
import io.ix0rai.bodacious_berries.block.CloudberryBush;
import io.ix0rai.bodacious_berries.block.DoubleBerryBush;
import io.ix0rai.bodacious_berries.block.GrowingBerryBush;
import io.ix0rai.bodacious_berries.block.RainberryBush;
import io.ix0rai.bodacious_berries.block.SpikedBerryBush;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodaciousBushes {
    public static final TagKey<Block> BERRY_BUSHES_TAG = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "berry_bushes"));
    public static final IntProperty AGE_4 = IntProperty.of("age", 0, 4);
    private static final Map<Berry, BerryBush> BY_BERRY = new HashMap<>();
    private static final Map<Berry, DoubleBerryBush> DOUBLE_BUSHES = new HashMap<>();

    private static final VoxelShape SMALL_SWEET_BERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape LARGE_SWEET_BERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape SMALL_LINGONBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    private static final VoxelShape LARGE_LINGONBERRY = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape LARGE_STRAWBERRY = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    private static final VoxelShape LARGE_RASPBERRY = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);
    private static final VoxelShape LARGE_CLOUDBERRY = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D);

    private static final AbstractBlock.Settings BERRY_BUSH_SETTINGS = AbstractBlock.Settings.create().ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque();

    public static final ChorusBerryBush CHORUS_BERRY_BUSH = new ChorusBerryBush(Berry.CHORUS_BERRIES,
            SMALL_LINGONBERRY, LARGE_LINGONBERRY, 2);
    public static final DoubleBerryBush DOUBLE_SASKATOON_BERRY_BUSH = new DoubleBerryBush(Berry.SASKATOON_BERRIES);
    public static final GrowingBerryBush SASKATOON_BERRY_BUSH = new GrowingBerryBush(SMALL_SWEET_BERRY, LARGE_SWEET_BERRY,
            DOUBLE_SASKATOON_BERRY_BUSH);
    public static final BasicBerryBush STRAWBERRY_BUSH = new BasicBerryBush.ThreeStageBush(Berry.STRAWBERRIES,
            SMALL_SWEET_BERRY, LARGE_STRAWBERRY, 1);
    public static final BasicBerryBush RASPBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(Berry.RASPBERRIES,
            SMALL_SWEET_BERRY, LARGE_RASPBERRY, 1, 1.0f);
    public static final BasicBerryBush BLACKBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(Berry.BLACKBERRIES,
            SMALL_SWEET_BERRY, LARGE_RASPBERRY, 1, 1.0f);
    public static final RainberryBush RAINBERRY_BUSH = new RainberryBush(Berry.RAINBERRIES,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 2);
    public static final BasicBerryBush LINGONBERRY_BUSH = new BasicBerryBush.FourStageBush(Berry.LINGONBERRIES,
            SMALL_LINGONBERRY, LARGE_LINGONBERRY, 1);
    public static final BerryVine GRAPEVINE = new BerryVine(Berry.GRAPES);
    public static final DoubleBerryBush DOUBLE_GOJI_BERRY_BUSH = new DoubleBerryBush(Berry.GOJI_BERRIES);
    public static final GrowingBerryBush GOJI_BERRY_BUSH = new GrowingBerryBush(SMALL_SWEET_BERRY, VoxelShapes.fullCube(),
            DOUBLE_GOJI_BERRY_BUSH);
    public static final BasicBerryBush GOOSEBERRY_BUSH = new SpikedBerryBush.SpikyFourStageBush(Berry.GOOSEBERRIES,
            SMALL_SWEET_BERRY, LARGE_SWEET_BERRY, 1, 2.0f);
    public static final CloudberryBush CLOUDBERRY_BUSH = new CloudberryBush(Berry.CLOUDBERRIES,
            SMALL_SWEET_BERRY, LARGE_CLOUDBERRY, 1);

    public static final List<Block> COLOUR_PROVIDER_EXCLUDED = new ArrayList<>();

    public static AbstractBlock.Settings berryBushSettings(Identifier id) {
        return BERRY_BUSH_SETTINGS.key(RegistryKey.of(RegistryKeys.BLOCK, id));
    }
    
    public static void register() {
        register(SASKATOON_BERRY_BUSH);
        register(DOUBLE_SASKATOON_BERRY_BUSH);
        register(STRAWBERRY_BUSH);
        register(RASPBERRY_BUSH);
        register(BLACKBERRY_BUSH);
        registerWithoutColourProvider(CHORUS_BERRY_BUSH);
        registerWithoutColourProvider(RAINBERRY_BUSH);
        register(LINGONBERRY_BUSH);
        register(GRAPEVINE);
        register(GOJI_BERRY_BUSH);
        register(DOUBLE_GOJI_BERRY_BUSH);
        register(GOOSEBERRY_BUSH);
        registerWithoutColourProvider(CLOUDBERRY_BUSH);
    }

    private static <T extends Block & BerryBush> void register(T block) {
        Berry berry = block.getBerry();
        Registry.register(Registries.BLOCK, block.getId(), block);
        if (BY_BERRY.containsKey(berry)) {
            DOUBLE_BUSHES.put(berry, (DoubleBerryBush) block);
        } else {
            BY_BERRY.put(berry, block);
        }
    }

    private static <T extends Block & BerryBush> void registerWithoutColourProvider(T block) {
        register(block);
        COLOUR_PROVIDER_EXCLUDED.add(block);
    }

    public static List<BerryBush> getBushes() {
        List<BerryBush> bushes = new ArrayList<>(BY_BERRY.values());
        bushes.addAll(DOUBLE_BUSHES.values());
        return bushes;
    }
}
