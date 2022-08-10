package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class BodaciousSounds {
    public static final SoundEvent BERRY_PICK = new SoundEvent(BodaciousBerries.id("block.berry_bush.pick"));

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, BERRY_PICK.getId(), BERRY_PICK);
    }
}
