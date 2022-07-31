package io.ix0rai.bodaciousberries.client;

import io.ix0rai.bodaciousberries.block.BerryBush;
import io.ix0rai.bodaciousberries.block.entity.BerryHarvesterScreen;
import io.ix0rai.bodaciousberries.block.entity.JuicerScreen;
import io.ix0rai.bodaciousberries.client.particle.Particles;
import io.ix0rai.bodaciousberries.registry.Berries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class BodaciousBerriesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(BodaciousBlocks.BERRY_HARVESTER_SCREEN_HANDLER, BerryHarvesterScreen::new);
        HandledScreens.register(BodaciousBlocks.JUICER_SCREEN_HANDLER, JuicerScreen::new);

        for (Map.Entry<BerryBush, Item> entry : Berries.BERRY_BUSHES.entrySet()) {
            // ensure bush is rendered with a cutout
            Block block = (Block) entry.getKey();
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());

            if (!Berries.COLOUR_PROVIDER_EXCLUDED.contains(entry.getKey())) {
                // register colour provider for the bush
                ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                    if (view != null) {
                        return BiomeColors.getFoliageColor(view, pos);
                    } else {
                        return FoliageColors.getDefaultColor();
                    }
                }, block);
            }
        }

        Particles.registerParticles();
    }
}
