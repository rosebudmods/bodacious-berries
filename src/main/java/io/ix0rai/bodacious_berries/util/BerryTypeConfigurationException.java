package io.ix0rai.bodacious_berries.util;

import net.minecraft.item.Item;

public class BerryTypeConfigurationException extends RuntimeException {
    public BerryTypeConfigurationException() {
        super("parameter berryType is null, use method setBerryType(Item) to ensure that it is set before the berry bush is registered");
    }

    public static void check(Item berryType) {
        if (berryType == null) {
            throw new BerryTypeConfigurationException();
        }
    }
}
