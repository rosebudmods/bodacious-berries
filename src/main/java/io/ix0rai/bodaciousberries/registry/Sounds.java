package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {
    public static final Identifier BERRY_PICK_1_ID = Bodaciousberries.getIdentifier("berry_pick_1");
    public static final SoundEvent BERRY_PICK_1 = new SoundEvent(BERRY_PICK_1_ID);
    public static final Identifier BERRY_PICK_2_ID = Bodaciousberries.getIdentifier("berry_pick_2");
    public static final SoundEvent BERRY_PICK_2 = new SoundEvent(BERRY_PICK_2_ID);
    public static final Identifier BERRY_PICK_3_ID = Bodaciousberries.getIdentifier("berry_pick_3");
    public static final SoundEvent BERRY_PICK_3 = new SoundEvent(BERRY_PICK_3_ID);

    public static void registerSounds() {
        register(BERRY_PICK_1_ID, BERRY_PICK_1);
        register(BERRY_PICK_2_ID, BERRY_PICK_2);
        register(BERRY_PICK_3_ID, BERRY_PICK_3);
    }

    private static void register(Identifier identifier, SoundEvent soundEvent) {
        Registry.register(Registry.SOUND_EVENT, identifier, soundEvent);
    }
}
