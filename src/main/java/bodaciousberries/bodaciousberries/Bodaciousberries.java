package bodaciousberries.bodaciousberries;

import bodaciousberries.bodaciousberries.registry.BodaciousItems;
import bodaciousberries.bodaciousberries.registry.Bushes;
import bodaciousberries.bodaciousberries.registry.Sounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Bodaciousberries implements ModInitializer {
    public static final String MOD_ID = "bodaciousberries";

    public static Identifier getIdentifier(String path) {
        return new Identifier(MOD_ID + ":" + path);
    }

    @Override
    public void onInitialize() {
        Bushes.registerBushes();
        BodaciousItems.registerItems();
        Sounds.registerSounds();
    }
}
