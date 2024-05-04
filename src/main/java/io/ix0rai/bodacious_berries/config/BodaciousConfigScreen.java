package io.ix0rai.bodacious_berries.config;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.registry.Berry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.ix0rai.bodacious_berries.BodaciousBerries.CONFIG;

/**
 * based on the configuration screen for lambdabettergrass by lambdaurora
 */
@Environment(EnvType.CLIENT)
public class BodaciousConfigScreen extends SimpleOptionsScreen {
    private final SpruceOption resetOption;
    private final SpruceOption[] generationOptions = new SpruceOption[Berry.values().length];

    public BodaciousConfigScreen(@Nullable Screen parent) {
        super(BodaciousBerries.translatableText("config.title"));

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
    protected void init() {
        super.init();

        int buttonHeight = 20;

        SpruceOptionListWidget options = new SpruceOptionListWidget(Position.of(0, 22), this.width, this.height - (35 + 22));
        for (int i = 0; i < Berry.values().length; i += 2) {
            SpruceOption secondToggle = null;
            if (i + 1 < Berry.values().length) {
                secondToggle = generationOptions[i + 1];
            }
            options.addOptionEntry(generationOptions[i], secondToggle);
        }
        this.addDrawableChild(options);

        // reset button
        this.addDrawableChild(this.resetOption.createWidget(Position.of(this, this.width / 2 - 155, this.height - 29), 150));
        // done button
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 155 + 160, this.height - 29), 150,
                buttonHeight, SpruceTexts.GUI_DONE,
                buttonWidget -> this.closeScreen()));
    }
}