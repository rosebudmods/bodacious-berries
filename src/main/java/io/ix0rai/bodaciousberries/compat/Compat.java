package io.ix0rai.bodaciousberries.compat;

import io.ix0rai.bodaciousberries.Bodaciousberries;

public class Compat {
    public static void registerCompat() {
        String s = MoreBerries.init() +
                ImprovedBerries.init();
        if (!s.isEmpty()) {
            Bodaciousberries.LOGGER.info("compat loaded with mods: " + s.replace(',', ' ').stripTrailing());
        }
    }
}
