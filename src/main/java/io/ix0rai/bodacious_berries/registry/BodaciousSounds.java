package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class BodaciousSounds {
    public static final SoundEvent BERRY_PICK = SoundEvent.createVariableRangeEvent(BodaciousBerries.id("block.berry_bush.pick"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, BERRY_PICK.getId(), BERRY_PICK);
    }
}
