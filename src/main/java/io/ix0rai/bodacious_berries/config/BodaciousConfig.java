package io.ix0rai.bodacious_berries.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * originally based on the configuration for lambdabettergrass by lambdaurora
 */
public class BodaciousConfig {
    private static final String CONFIG_FILE_NAME = "bodacious_berries.toml";
    public static final Path CONFIG_FILE_PATH = Paths.get("config/" + CONFIG_FILE_NAME);
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
        try {
            File file = new File(CONFIG_FILE_PATH.toUri());
            if (file.getParentFile().mkdirs() || file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("""
                            # bodacious berries configuration file - higher is more rare, lower is more common
                            # values only apply on game restart
                                                
                            common_rarity =""" + " " + DEFAULT_COMMON_RARITY
                            + "\nmedium_rarity = " + DEFAULT_MEDIUM_RARITY
                            + "\nrare_rarity = " + DEFAULT_RARE_RARITY
                            + "\nultra_rare_rarity = " + DEFAULT_ULTRA_RARE_RARITY
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.config = CommentedFileConfig.builder(CONFIG_FILE_PATH)
                .concurrent()
                .autosave()
                .preserveInsertionOrder()
                .build();
        this.config.load();
    }

    public void reset() {
        this.config.set(COMMON_KEY, DEFAULT_COMMON_RARITY);
        this.config.set(MEDIUM_KEY, DEFAULT_MEDIUM_RARITY);
        this.config.set(RARE_KEY, DEFAULT_RARE_RARITY);
        this.config.set(ULTRA_RARE_KEY, DEFAULT_ULTRA_RARE_RARITY);
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
