package adudecalledleo.dfubuddy.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::lockDataFixer);
    }

    private void lockDataFixer(MinecraftServer server) {
        ModDataFixesInternals.lock();
    }
}
