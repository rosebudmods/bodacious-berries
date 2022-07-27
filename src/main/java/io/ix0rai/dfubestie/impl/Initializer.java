package io.ix0rai.dfubestie.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register((server -> DataFixesInternals.lock()));
    }
}
