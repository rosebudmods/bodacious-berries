package io.ix0rai.bodaciousberries.config;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceIntegerInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSimpleActionOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import io.ix0rai.bodaciousberries.BodaciousBerries;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import javax.annotation.Nullable;

import static io.ix0rai.bodaciousberries.BodaciousBerries.CONFIG;

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

        this.resetOption = SpruceSimpleActionOption.reset(btn -> {
            CONFIG.reset();
            MinecraftClient client = MinecraftClient.getInstance();
            this.init(client, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
        });
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
        int buttonOffset = 30;

        this.addDrawableChild(this.commonRarityOption.createWidget(Position.of(this.width / 2 - 205, this.height / 4 - buttonHeight + buttonOffset), 200));
        this.addDrawableChild(this.mediumRarityOption.createWidget(Position.of(this.width / 2 + 5, this.height / 4 - buttonHeight + buttonOffset), 200));
        this.addDrawableChild(this.rareRarityOption.createWidget(Position.of(this.width / 2 - 205, this.height / 4 - buttonHeight * 3 + buttonOffset), 200));
        this.addDrawableChild(this.ultraRareRarityOption.createWidget(Position.of(this.width / 2 + 5, this.height / 4 - buttonHeight * 3 + buttonOffset), 200));

        this.buildLabels();

        this.addDrawableChild(this.resetOption.createWidget(Position.of(this, this.width / 2 - 155, this.height - 29), 150));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 155 + 160, this.height - 29), 150,
                buttonHeight, SpruceTexts.GUI_DONE,
                buttonWidget -> this.closeScreen()));
    }

    private void buildLabels() {
        int y = this.height / 2;

        MutableText text = Text.literal("");
        text.append(BodaciousBerries.translatableText("config.title.info").formatted(Formatting.GREEN, Formatting.BOLD));
        text.append("\n\n");
        text.append(BodaciousBerries.translatableText("config.info.1")).append("\n");
        text.append(BodaciousBerries.translatableText("config.info.2"));
        this.addDrawableChild(new SpruceLabelWidget(Position.of(this, 0, y),
                text, this.width, true));
    }

    @Override
    public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }
}
