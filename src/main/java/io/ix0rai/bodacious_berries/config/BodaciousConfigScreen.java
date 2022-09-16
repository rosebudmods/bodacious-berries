package io.ix0rai.bodacious_berries.config;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceIntegerInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.ix0rai.bodacious_berries.BodaciousBerries.CONFIG;

/**
 * based on the configuration screen for lambdabettergrass by lambdaurora
 */
@Environment(EnvType.CLIENT)
public class BodaciousConfigScreen extends SpruceScreen {
    private final Screen parent;

    private final SpruceOption commonRarityOption;
    private final SpruceOption mediumRarityOption;
    private final SpruceOption rareRarityOption;
    private final SpruceOption ultraRareRarityOption;
    private final SpruceOption resetOption;

    private final SpruceOption generateSaskatoons;
    private final SpruceOption generateStrawberries;
    private final SpruceOption generateRaspberries;
    private final SpruceOption generateBlackberries;
    private final SpruceOption generateChorusBerries;
    private final SpruceOption generateRainberries;
    private final SpruceOption generateLingonberries;
    private final SpruceOption generateGrapes;
    private final SpruceOption generateGojiBerries;
    private final SpruceOption generateGooseberries;
    private final SpruceOption generateCloudberries;

    public BodaciousConfigScreen(@Nullable Screen parent) {
        super(BodaciousBerries.translatableText("config.title"));
        this.parent = parent;

        this.commonRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.commonRarityOption"),
                CONFIG::common,
                CONFIG::setCommon,
                null
        );

        this.mediumRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.mediumRarityOption"),
                CONFIG::medium,
                CONFIG::setMedium,
                null
        );

        this.rareRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.rareRarityOption"),
                CONFIG::rare,
                CONFIG::setRare,
                null
        );

        this.ultraRareRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.ultraRareRarityOption"),
                CONFIG::ultraRare,
                CONFIG::setUltraRare,
                null
        );

        this.generateSaskatoons = createGenOption("generateSaskatoons", CONFIG::generateSaskatoons, CONFIG::setGenerateSaskatoons);
        this.generateStrawberries = createGenOption("generateStrawberries", CONFIG::generateStrawberries, CONFIG::setGenerateStrawberries);
        this.generateRaspberries = createGenOption("generateRaspberries", CONFIG::generateRaspberries, CONFIG::setGenerateRaspberries);
        this.generateBlackberries = createGenOption("generateBlackberries", CONFIG::generateBlackberries, CONFIG::setGenerateBlackberries);
        this.generateChorusBerries = createGenOption("generateChorusBerries", CONFIG::generateChorusBerries, CONFIG::setGenerateChorusBerries);
        this.generateRainberries = createGenOption("generateRainberries", CONFIG::generateRainberries, CONFIG::setGenerateRainberries);
        this.generateLingonberries = createGenOption("generateLingonberries", CONFIG::generateLingonberries, CONFIG::setGenerateLingonberries);
        this.generateGrapes = createGenOption("generateGrapes", CONFIG::generateGrapes, CONFIG::setGenerateGrapes);
        this.generateGojiBerries = createGenOption("generateGojiBerries", CONFIG::generateGojiBerries, CONFIG::setGenerateGojiBerries);
        this.generateGooseberries = createGenOption("generateGooseberries", CONFIG::generateGooseberries, CONFIG::setGenerateGooseberries);
        this.generateCloudberries = createGenOption("generateCloudberries", CONFIG::generateCloudberries, CONFIG::setGenerateCloudberries);

        this.resetOption = SpruceSimpleActionOption.reset(btn -> {
            CONFIG.reset();
            MinecraftClient client = MinecraftClient.getInstance();
            this.init(client, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
        });
    }

    private SpruceBooleanOption createGenOption(String key, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SpruceBooleanOption(BodaciousBerries.translatableTextKey("config." + key),
                getter,
                setter,
                null
        );
    }

    @Override
    public void closeScreen() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        } else {
            super.closeScreen();
        }
    }

    @Override
    protected void init() {
        super.init();

        int buttonHeight = 20;

        SpruceOptionListWidget options = new SpruceOptionListWidget(Position.of(0, 22), this.width, this.height - (35 + 22));
        options.addOptionEntry(this.commonRarityOption, this.mediumRarityOption);
        options.addOptionEntry(this.rareRarityOption, this.ultraRareRarityOption);
        options.addOptionEntry(this.generateSaskatoons, this.generateStrawberries);
        options.addOptionEntry(this.generateRaspberries, this.generateBlackberries);
        options.addOptionEntry(this.generateChorusBerries, this.generateRainberries);
        options.addOptionEntry(this.generateLingonberries, this.generateGrapes);
        options.addOptionEntry(this.generateGojiBerries, this.generateGooseberries);
        options.addOptionEntry(this.generateCloudberries, null);

        this.addDrawableChild(options);
        this.addDrawableChild(this.resetOption.createWidget(Position.of(this, this.width / 2 - 155, this.height - 29), 150));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 155 + 160, this.height - 29), 150,
                buttonHeight, SpruceTexts.GUI_DONE,
                buttonWidget -> this.closeScreen()));
    }

    @Override
    public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }
}