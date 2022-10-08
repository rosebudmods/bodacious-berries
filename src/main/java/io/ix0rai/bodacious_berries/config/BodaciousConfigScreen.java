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
import io.ix0rai.bodacious_berries.registry.Berry;
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

    private final SpruceOption[] generationOptions = new SpruceOption[Berry.values().length];

    public BodaciousConfigScreen(@Nullable Screen parent) {
        super(BodaciousBerries.translatableText("config.title"));
        this.parent = parent;

        this.commonRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.common_rarity_option"),
                CONFIG::common,
                CONFIG::setCommon,
                null
        );

        this.mediumRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.medium_rarity_option"),
                CONFIG::medium,
                CONFIG::setMedium,
                null
        );

        this.rareRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.rare_rarity_option"),
                CONFIG::rare,
                CONFIG::setRare,
                null
        );

        this.ultraRareRarityOption = new SpruceIntegerInputOption(BodaciousBerries.translatableTextKey("config.ultra_rare_rarity_option"),
                CONFIG::ultraRare,
                CONFIG::setUltraRare,
                null
        );

        for (Berry berry : Berry.values()) {
            generationOptions[berry.ordinal()] = createGenOption(berry.toString(),
                    () -> CONFIG.isGenerating(berry),
                    value -> CONFIG.setGenerating(berry, value)
            );
        }

        this.resetOption = SpruceSimpleActionOption.reset(btn -> {
            CONFIG.reset();
            MinecraftClient client = MinecraftClient.getInstance();
            this.init(client, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
        });
    }

    private SpruceBooleanOption createGenOption(String key, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return new SpruceBooleanOption(BodaciousBerries.translatableTextKey("config." + "generate_" + key),
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
        for (int i = 0; i < Berry.values().length; i += 2) {
            SpruceOption secondToggle = null;
            if (i + 1 < Berry.values().length) {
                secondToggle = generationOptions[i + 1];
            }
            options.addOptionEntry(generationOptions[i], secondToggle);
        }
        this.addDrawableChild(options);

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