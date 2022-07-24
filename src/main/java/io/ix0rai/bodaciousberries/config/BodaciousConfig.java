package io.ix0rai.bodaciousberries.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * originally based on the configuration for lambdabettergrass by lambdaurora
 */
public class BodaciousConfig {
    private static final String CONFIG_FILE_NAME = "bodaciousberries.toml";
    public static final Path CONFIG_FILE_PATH = Paths.get("config/" + CONFIG_FILE_NAME);
    private static final String COMMON_KEY = "common_rarity";
    private static final String MEDIUM_KEY = "medium_rarity";
    private static final String RARE_KEY = "rare_rarity";
    private static final String ULTRA_RARE_KEY = "ultra_rare_rarity";

    protected final CommentedFileConfig config;

    public BodaciousConfig() {
        this.config = CommentedFileConfig.builder(CONFIG_FILE_PATH)
                .concurrent()
                .defaultResource("/" + CONFIG_FILE_NAME)
                .autosave()
                .build();
        this.config.load();
    }

    public void reset() {
        this.config.set(COMMON_KEY, 100);
        this.config.set(MEDIUM_KEY, 130);
        this.config.set(RARE_KEY, 170);
        this.config.set(ULTRA_RARE_KEY, 200);
    }

    public int common() {
        return config.get(COMMON_KEY);
    }

    public int medium() {
        return config.get(MEDIUM_KEY);
    }

    public int rare() {
        return config.get(RARE_KEY);
    }

    public int ultraRare() {
        return config.get(ULTRA_RARE_KEY);
    }

    public void setCommon(int value) {
        this.config.set(COMMON_KEY, value);
    }

    public void setMedium(int value) {
        this.config.set(MEDIUM_KEY, value);
    }

    public void setRare(int value) {
        this.config.set(RARE_KEY, value);
    }

    public void setUltraRare(int value) {
        this.config.set(ULTRA_RARE_KEY, value);
    }
}
