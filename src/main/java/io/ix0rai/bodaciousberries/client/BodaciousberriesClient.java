package io.ix0rai.bodaciousberries.client;

import io.ix0rai.bodaciousberries.registry.Berries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BodaciousberriesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (var entry : Berries.BERRY_BUSHES.entrySet()) {
            //ensure bush is rendered with a cutout
            BlockRenderLayerMap.INSTANCE.putBlock(entry.getKey().getBaseState().getBlock(), RenderLayer.getCutout());
        }
    }
}
