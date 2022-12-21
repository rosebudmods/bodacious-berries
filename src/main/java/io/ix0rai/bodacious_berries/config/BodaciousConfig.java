package io.ix0rai.bodacious_berries.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.ix0rai.bodacious_berries.registry.Berry;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

/**
 * originally based on the configuration for lambdabettergrass by lambdaurora
 */
public class BodaciousConfig {
    private static final String CONFIG_FILE_NAME = "bodacious_berries.toml";
    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME);

    private final CommentedFileConfig config;

    public BodaciousConfig() {
        this.config = CommentedFileConfig.builder(CONFIG_FILE_PATH)
                .concurrent()
                .autosave()
                .preserveInsertionOrder()
                .build();
        this.config.load();
    }

    public void reset() {
        for (Berry berry : Berry.values()) {
            setGenerating(berry, true);
        }
    }

    public void setGenerating(Berry berry, boolean value) {
        config.set("generate_" + berry.toString(), value);
    }

    public boolean isGenerating(Berry berry) {
        return config.getOrElse("generate_" + berry.toString(), true);
    }
}