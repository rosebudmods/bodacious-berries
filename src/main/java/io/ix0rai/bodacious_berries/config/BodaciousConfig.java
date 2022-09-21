package io.ix0rai.bodacious_berries.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
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

    private static final String GENERATE_SASKATOONS_KEY = "generate_saskatoons";
    private static final String GENERATE_STRAWBERRIES_KEY = "generate_strawberries";
    private static final String GENERATE_RASPBERRIES_KEY = "generate_raspberries";
    private static final String GENERATE_BLACKBERRIES_KEY = "generate_blackberries";
    private static final String GENERATE_CHORUS_BERRIES_KEY = "generate_chorus_berries";
    private static final String GENERATE_RAINBERRIES_KEY = "generate_rainberries";
    private static final String GENERATE_LINGONBERRIES_KEY = "generate_lingonberries";
    private static final String GENERATE_GRAPES_KEY = "generate_grapes";
    private static final String GENERATE_GOJI_BERRIES_KEY = "generate_goji_berries";
    private static final String GENERATE_GOOSEBERRIES_KEY = "generate_gooseberries";
    private static final String GENERATE_CLOUDBERRIES_KEY = "generate_cloudberries";

    private static final int DEFAULT_COMMON_RARITY = 150;
    private static final int DEFAULT_MEDIUM_RARITY = 250;
    private static final int DEFAULT_RARE_RARITY = 325;
    private static final int DEFAULT_ULTRA_RARE_RARITY = 400;

    private static final boolean DEFAULT_GENERATE_SASKATOONS = true;
    private static final boolean DEFAULT_GENERATE_STRAWBERRIES = true;
    private static final boolean DEFAULT_GENERATE_RASPBERRIES = true;
    private static final boolean DEFAULT_GENERATE_BLACKBERRIES = true;
    private static final boolean DEFAULT_GENERATE_CHORUS_BERRIES = true;
    private static final boolean DEFAULT_GENERATE_RAINBERRIES = true;
    private static final boolean DEFAULT_GENERATE_LINGONBERRIES = true;
    private static final boolean DEFAULT_GENERATE_GRAPES = true;
    private static final boolean DEFAULT_GENERATE_GOJI_BERRIES = true;
    private static final boolean DEFAULT_GENERATE_GOOSEBERRIES = true;
    private static final boolean DEFAULT_GENERATE_CLOUDBERRIES = true;

    protected final CommentedFileConfig config;

    public BodaciousConfig() {
        try {
            File file = new File(CONFIG_FILE_PATH.toUri());
            if (file.getParentFile().mkdirs() || file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    String content = """
                            # bodacious berries configuration file - higher is more rare, lower is more common
                            # values only apply on game restart
                            # order may be mangled, this is not fixable until 3.0's quilt move
                                                
                            common_rarity =""" + " " + DEFAULT_COMMON_RARITY
                            + "\nmedium_rarity = " + DEFAULT_MEDIUM_RARITY
                            + "\nrare_rarity = " + DEFAULT_RARE_RARITY
                            + "\nultra_rare_rarity = " + DEFAULT_ULTRA_RARE_RARITY;
                    content = appendGranularGenerationOptions(content);

                    writer.write(content);
                }
            } else {
                String content = Files.readString(file.toPath());
                if (!content.contains(GENERATE_SASKATOONS_KEY)) {
                    content = appendGranularGenerationOptions(content);
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content);
                    }
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

    private String appendGranularGenerationOptions(String str) {
        return str + "\n\n# whether to generate berries in the world"
                + "\ngenerate_saskatoons = " + DEFAULT_GENERATE_SASKATOONS
                + "\ngenerate_strawberries = " + DEFAULT_GENERATE_STRAWBERRIES
                + "\ngenerate_raspberries = " + DEFAULT_GENERATE_RASPBERRIES
                + "\ngenerate_blackberries = " + DEFAULT_GENERATE_BLACKBERRIES
                + "\ngenerate_chorus_berries = " + DEFAULT_GENERATE_CHORUS_BERRIES
                + "\ngenerate_rainberries = " + DEFAULT_GENERATE_RAINBERRIES
                + "\ngenerate_lingonberries = " + DEFAULT_GENERATE_LINGONBERRIES
                + "\ngenerate_grapes = " + DEFAULT_GENERATE_GRAPES
                + "\ngenerate_goji_berries = " + DEFAULT_GENERATE_GOJI_BERRIES
                + "\ngenerate_gooseberries = " + DEFAULT_GENERATE_GOOSEBERRIES
                + "\ngenerate_cloudberries = " + DEFAULT_GENERATE_CLOUDBERRIES;
    }

    public void reset() {
        setCommon(DEFAULT_COMMON_RARITY);
        setMedium(DEFAULT_MEDIUM_RARITY);
        setRare(DEFAULT_RARE_RARITY);
        setUltraRare(DEFAULT_ULTRA_RARE_RARITY);
        setGenerateSaskatoons(DEFAULT_GENERATE_SASKATOONS);
        setGenerateStrawberries(DEFAULT_GENERATE_STRAWBERRIES);
        setGenerateRaspberries(DEFAULT_GENERATE_RASPBERRIES);
        setGenerateBlackberries(DEFAULT_GENERATE_BLACKBERRIES);
        setGenerateChorusBerries(DEFAULT_GENERATE_CHORUS_BERRIES);
        setGenerateRainberries(DEFAULT_GENERATE_RAINBERRIES);
        setGenerateLingonberries(DEFAULT_GENERATE_LINGONBERRIES);
        setGenerateGrapes(DEFAULT_GENERATE_GRAPES);
        setGenerateGojiBerries(DEFAULT_GENERATE_GOJI_BERRIES);
        setGenerateGooseberries(DEFAULT_GENERATE_GOOSEBERRIES);
        setGenerateCloudberries(DEFAULT_GENERATE_CLOUDBERRIES);
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

    public boolean generateSaskatoons() {
        return config.get(GENERATE_SASKATOONS_KEY);
    }

    public boolean generateStrawberries() {
        return config.get(GENERATE_STRAWBERRIES_KEY);
    }

    public boolean generateRaspberries() {
        return config.get(GENERATE_RASPBERRIES_KEY);
    }

    public boolean generateBlackberries() {
        return config.get(GENERATE_BLACKBERRIES_KEY);
    }

    public boolean generateChorusBerries() {
        return config.get(GENERATE_CHORUS_BERRIES_KEY);
    }

    public boolean generateRainberries() {
        return config.get(GENERATE_RAINBERRIES_KEY);
    }

    public boolean generateLingonberries() {
        return config.get(GENERATE_LINGONBERRIES_KEY);
    }

    public boolean generateGrapes() {
        return config.get(GENERATE_GRAPES_KEY);
    }

    public boolean generateGojiBerries() {
        return config.get(GENERATE_GOJI_BERRIES_KEY);
    }

    public boolean generateGooseberries() {
        return config.get(GENERATE_GOOSEBERRIES_KEY);
    }

    public boolean generateCloudberries() {
        return config.get(GENERATE_CLOUDBERRIES_KEY);
    }

    public void setGenerateSaskatoons(boolean value) {
        this.config.set(GENERATE_SASKATOONS_KEY, value);
    }

    public void setGenerateStrawberries(boolean value) {
        this.config.set(GENERATE_STRAWBERRIES_KEY, value);
    }

    public void setGenerateRaspberries(boolean value) {
        this.config.set(GENERATE_RASPBERRIES_KEY, value);
    }

    public void setGenerateBlackberries(boolean value) {
        this.config.set(GENERATE_BLACKBERRIES_KEY, value);
    }

    public void setGenerateChorusBerries(boolean value) {
        this.config.set(GENERATE_CHORUS_BERRIES_KEY, value);
    }

    public void setGenerateRainberries(boolean value) {
        this.config.set(GENERATE_RAINBERRIES_KEY, value);
    }

    public void setGenerateLingonberries(boolean value) {
        this.config.set(GENERATE_LINGONBERRIES_KEY, value);
    }

    public void setGenerateGrapes(boolean value) {
        this.config.set(GENERATE_GRAPES_KEY, value);
    }

    public void setGenerateGojiBerries(boolean value) {
        this.config.set(GENERATE_GOJI_BERRIES_KEY, value);
    }

    public void setGenerateGooseberries(boolean value) {
        this.config.set(GENERATE_GOOSEBERRIES_KEY, value);
    }

    public void setGenerateCloudberries(boolean value) {
        this.config.set(GENERATE_CLOUDBERRIES_KEY, value);
    }
}