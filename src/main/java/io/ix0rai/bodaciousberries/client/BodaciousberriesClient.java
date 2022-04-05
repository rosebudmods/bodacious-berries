package io.ix0rai.bodaciousberries.client;

import io.ix0rai.bodaciousberries.block.entity.BerryHarvesterScreen;
import io.ix0rai.bodaciousberries.block.entity.JuicerScreen;
import io.ix0rai.bodaciousberries.particle.Particles;
import io.ix0rai.bodaciousberries.registry.Berries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BodaciousberriesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(BodaciousBlocks.BERRY_HARVESTER_SCREEN_HANDLER, BerryHarvesterScreen::new);
        ScreenRegistry.register(BodaciousBlocks.JUICER_SCREEN_HANDLER, JuicerScreen::new);

        for (var entry : Berries.BERRY_BUSHES.entrySet()) {
            //ensure bush is rendered with a cutout
            BlockRenderLayerMap.INSTANCE.putBlock(entry.getKey().getBaseState().getBlock(), RenderLayer.getCutout());
        }

        Particles.registerParticles();
    }
}
