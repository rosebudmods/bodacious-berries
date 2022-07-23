package io.ix0rai.bodaciousberries.config;

import io.ix0rai.bodaciousberries.BodaciousBerries;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = BodaciousBerries.MOD_ID)
public class BodaciousConfig implements ConfigData {
    public int ultraRareBushRarity = 200;
    public int rareBushRarity = 160;
    public int mediumBushRarity = 130;
    public int commonBushRarity = 100;
}
