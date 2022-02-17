package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.harvester.BerryHarvesterBlock;
import io.ix0rai.bodaciousberries.block.harvester.BerryHarvesterBlockEntity;
import io.ix0rai.bodaciousberries.block.harvester.BerryHarvesterScreenHandler;
import io.ix0rai.bodaciousberries.registry.items.ChorusBerryJuice;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class BodaciousThings {
    private static final Settings CHORUS_JUICE_SETTINGS = new Settings().recipeRemainder(Items.GLASS_BOTTLE).food(new FoodComponent.Builder().hunger(2).saturationModifier(4F).build()).group(ItemGroup.FOOD).maxCount(16);

    public static final Item CHORUS_BERRY_JUICE = chorusBerryJuice(null);

    private static final Identifier HARVESTER = Bodaciousberries.getIdentifier("berry_harvester");
    public static BerryHarvesterBlock BERRY_HARVESTER;
    public static BlockEntityType<BerryHarvesterBlockEntity> BERRY_HARVESTER_ENTITY;

    public static final ScreenHandlerType<BerryHarvesterScreenHandler> BERRY_HARVESTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(HARVESTER, BerryHarvesterScreenHandler::new);

    public static void registerThings() {
        BERRY_HARVESTER = Registry.register(Registry.BLOCK, HARVESTER,
                new BerryHarvesterBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f)));

        Registry.register(Registry.ITEM, HARVESTER, new BlockItem(BERRY_HARVESTER, new Settings().group(ItemGroup.REDSTONE)));

        BERRY_HARVESTER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Bodaciousberries.getIdentifier("berry_harvester_entity"),
                FabricBlockEntityTypeBuilder.create(BerryHarvesterBlockEntity::new, BERRY_HARVESTER).build(null));

        createChorusBerryJuice(List.of(
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                LUSH_CAVES, DRIPSTONE_CAVES, JUNGLE
        ));
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), item);
    }

    private static void createChorusBerryJuice(List<RegistryKey<Biome>> biomes) {
        register("chorus_berry_juice", CHORUS_BERRY_JUICE);

        for (RegistryKey<Biome> key : biomes) {
            String name = "chorus_berry_juice_" + key.getValue().getPath();
            register(name, chorusBerryJuice(key));
        }
    }

    private static ChorusBerryJuice chorusBerryJuice(RegistryKey<Biome> biome) {
        if (biome == null) {
            return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, null);
        }
        return new ChorusBerryJuice(CHORUS_JUICE_SETTINGS, biome.getValue());
    }
}
