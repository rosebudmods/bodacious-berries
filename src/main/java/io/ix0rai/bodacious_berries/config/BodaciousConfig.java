package io.ix0rai.bodacious_berries.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.ix0rai.bodacious_berries.registry.Berry;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * originally based on the configuration for lambdabettergrass by lambdaurora
 */
public class BodaciousConfig {
    private static final String CONFIG_FILE_NAME = "bodacious_berries.toml";
    private static final Path CONFIG_FILE_PATH = Paths.get(FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME).toUri());
    private static final String COMMON_KEY = "common_rarity";
    private static final String MEDIUM_KEY = "medium_rarity";
    private static final String RARE_KEY = "rare_rarity";
    private static final String ULTRA_RARE_KEY = "ultra_rare_rarity";

    private static final int DEFAULT_COMMON_RARITY = 150;
    private static final int DEFAULT_MEDIUM_RARITY = 250;
    private static final int DEFAULT_RARE_RARITY = 325;
    private static final int DEFAULT_ULTRA_RARE_RARITY = 400;

    protected final CommentedFileConfig config;

    public BodaciousConfig() {
        this.config = CommentedFileConfig.builder(CONFIG_FILE_PATH)
                .concurrent()
                .autosave()
                .preserveInsertionOrder()
                .build();
        this.config.load();
    }

    public void reset() {
        setCommon(DEFAULT_COMMON_RARITY);
        setMedium(DEFAULT_MEDIUM_RARITY);
        setRare(DEFAULT_RARE_RARITY);
        setUltraRare(DEFAULT_ULTRA_RARE_RARITY);
        for (Berry berry : Berry.values()) {
            setGenerating(berry, true);
        }
    }

    public int common() {
        return config.getOrElse(COMMON_KEY, DEFAULT_COMMON_RARITY);
    }

    public int medium() {
        return config.getOrElse(MEDIUM_KEY, DEFAULT_MEDIUM_RARITY);
    }

    public int rare() {
        return config.getOrElse(RARE_KEY, DEFAULT_RARE_RARITY);
    }

    public int ultraRare() {
        return config.getOrElse(ULTRA_RARE_KEY, DEFAULT_ULTRA_RARE_RARITY);
    }

    public void setCommon(int value) {
        setRarity(COMMON_KEY, value);
    }

    public void setMedium(int value) {
        setRarity(MEDIUM_KEY, value);
    }

    public void setRare(int value) {
        setRarity(RARE_KEY, value);
    }

    public void setUltraRare(int value) {
        setRarity(ULTRA_RARE_KEY, value);
    }

    private void setRarity(String key, int value) {
        if (value > 0) {
            this.config.set(key, value);
        } else {
            this.config.set(key, 1);
        }
    }

    public void setGenerating(Berry berry, boolean value) {
        config.set("generate_" + berry.toString(), value);
    }

    public boolean isGenerating(Berry berry) {
        return config.getOrElse("generate_" + berry.toString(), true);
    }
}