package io.ix0rai.dfu_bestie.impl.client;

import io.ix0rai.dfu_bestie.impl.DataFixesInternals;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.jetbrains.annotations.ApiStatus;

@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register((client -> DataFixesInternals.lock()));
    }
}
