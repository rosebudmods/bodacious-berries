package io.ix0rai.bodaciousberries.registry;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class Sounds {
    private static final HashMap<Identifier, SoundEvent> SOUND_EVENTS = new HashMap<>();

    public static final SoundEvent BERRY_PICK_1 = create(Bodaciousberries.getIdentifier("block.berry_bush.pick_1"));
    public static final SoundEvent BERRY_PICK_2 = create(Bodaciousberries.getIdentifier("block.berry_bush.pick_2"));
    public static final SoundEvent BERRY_PICK_3 = create(Bodaciousberries.getIdentifier("block.berry_bush.pick_3"));

    private static SoundEvent create(Identifier id) {
        SoundEvent event = new SoundEvent(id);
        SOUND_EVENTS.put(id, event);
        return event;
    }

    public static void registerSounds() {
        for (Map.Entry<Identifier, SoundEvent> entry : SOUND_EVENTS.entrySet()) {
            Registry.register(Registry.SOUND_EVENT, entry.getKey(), entry.getValue());
        }
    }
}
