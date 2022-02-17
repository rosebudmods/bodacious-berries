package io.ix0rai.bodaciousberries.client;

import io.ix0rai.bodaciousberries.block.harvester.BerryHarvesterScreen;
import io.ix0rai.bodaciousberries.registry.BodaciousThings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class BodaciousberriesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(BodaciousThings.BERRY_HARVESTER_SCREEN_HANDLER, BerryHarvesterScreen::new);
    }
}
