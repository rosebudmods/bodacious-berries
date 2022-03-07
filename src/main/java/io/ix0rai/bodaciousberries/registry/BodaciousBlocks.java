package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.BerryHarvesterBlock;
import io.ix0rai.bodaciousberries.block.JuicerBlock;
import io.ix0rai.bodaciousberries.block.entity.BerryHarvesterBlockEntity;
import io.ix0rai.bodaciousberries.block.entity.BerryHarvesterScreenHandler;
import io.ix0rai.bodaciousberries.block.entity.JuicerBlockEntity;
import io.ix0rai.bodaciousberries.block.entity.JuicerScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BodaciousBlocks {
    public static final Identifier BERRY_HARVESTER = Bodaciousberries.getIdentifier("berry_harvester");
    public static final BerryHarvesterBlock BERRY_HARVESTER_BLOCK = new BerryHarvesterBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BlockEntityType<BerryHarvesterBlockEntity> BERRY_HARVESTER_ENTITY = FabricBlockEntityTypeBuilder.create(BerryHarvesterBlockEntity::new, BERRY_HARVESTER_BLOCK).build(null);
    public static ScreenHandlerType<BerryHarvesterScreenHandler> BERRY_HARVESTER_SCREEN_HANDLER;

    public static final Identifier JUICER = Bodaciousberries.getIdentifier("juicer");
    public static final JuicerBlock JUICER_BLOCK = new JuicerBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BlockEntityType<JuicerBlockEntity> JUICER_ENTITY = FabricBlockEntityTypeBuilder.create(JuicerBlockEntity::new, JUICER_BLOCK).build(null);
    public static ScreenHandlerType<JuicerScreenHandler> JUICER_SCREEN_HANDLER;

    public static void registerBlocks() {
        BERRY_HARVESTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(BERRY_HARVESTER, BerryHarvesterScreenHandler::new);
        registerBlockEntity(BERRY_HARVESTER, BERRY_HARVESTER_BLOCK, BERRY_HARVESTER_ENTITY, ItemGroup.REDSTONE);

        JUICER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(JUICER, JuicerScreenHandler::new);
        registerBlockEntity(JUICER, JUICER_BLOCK, JUICER_ENTITY, ItemGroup.BREWING);
    }

    private static void registerBlockEntity(Identifier id, Block block, BlockEntityType<?> entity, ItemGroup group) {
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Settings().group(group)));
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Bodaciousberries.getIdentifier(id.getPath() + "_entity"), entity);
    }
}
