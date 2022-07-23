package io.ix0rai.bodaciousberries.config;

import com.electronwill.nightconfig.core.file.FileConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * originally based on the configuration for lambdabettergrass by lambdaurora
 */
public class BodaciousConfig {
    private static final String CONFIG_FILE_NAME = "bodaciousberries.toml";
    public static final Path CONFIG_FILE_PATH = Paths.get("config/" + CONFIG_FILE_NAME);

    protected final FileConfig config;

    public BodaciousConfig() {
        this.config = FileConfig.builder(CONFIG_FILE_PATH)
                .concurrent()
                .defaultResource(CONFIG_FILE_NAME)
                .autosave()
                .build();
    }

    public int common() {
        return config.get("common_rarity");
    }

    public int medium() {
        return config.get("medium_rarity");
    }

    public int rare() {
        return config.get("rare_rarity");
    }

    public int ultraRare() {
        return config.get("ultra_rare_rarity");
    }
}
