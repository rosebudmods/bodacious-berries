package io.ix0rai.bodacious_berries.registry;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.util.Identifier;

import java.util.Locale;

/**
 * exists so that {@link BodaciousItems} is not class-loaded until after {@link BodaciousBlocks}
 */
public enum Berry {
    SASKATOON_BERRIES,
    STRAWBERRIES,
    RASPBERRIES,
    BLACKBERRIES,
    CHORUS_BERRIES,
    RAINBERRIES,
    LINGONBERRIES,
    GRAPES,
    GOJI_BERRIES,
    GOOSEBERRIES,
    CLOUDBERRIES;

    private final Identifier identifier;

    Berry() {
        this.identifier = BodaciousBerries.id(this.toString());
    }

    public Identifier get() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
