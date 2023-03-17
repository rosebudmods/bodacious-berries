package io.ix0rai.bodacious_berries.block.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JuicerScreen extends BodaciousScreen<JuicerScreenHandler> {
    private static final Identifier TEXTURE = BodaciousBerries.id("textures/gui/juicer.png");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 29, 25, 17, 11, 6, 0};
    private static final int DUBIOUS_BUBBLE_UV_X = 188;
    private static final int DUBIOUS_PROGRESS_BAR_UV_X = 227;
    private static final int BUBBLE_UV_X = 176;
    private static final int PROGRESS_BAR_UV_X = 200;
    private static final int BUBBLE_UV_Y = 29;
    private static final int PROGRESS_BAR_UV_Y = 0;
    private static final int BLEND_BUBBLE_UV_Y = 59;
    private static final int BLEND_PROGRESS_BAR_UV_Y = 31;
    private static final int LEFT_BUBBLE_X = 58;
    private static final int RIGHT_BUBBLE_X = 104;

    public JuicerScreen(JuicerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // draw background
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.drawProgress(matrices);
    }

    private void drawProgress(MatrixStack matrices) {
        final int brewTime = this.handler.getBrewTime();
        final boolean dubious = this.handler.brewingDubiously();
        final boolean blend = this.handler.brewingBlend();

        if (brewTime > 0) {
            // draw progress bar
            int progress = Math.round(28.0F * (1.0F - brewTime / (float) JuicerBlockEntity.TOTAL_BREW_TIME));
            if (progress > 0) {
                drawTexture(matrices, x + 73, y + 35, dubious ? DUBIOUS_PROGRESS_BAR_UV_X : PROGRESS_BAR_UV_X, blend ? BLEND_PROGRESS_BAR_UV_Y : PROGRESS_BAR_UV_Y, 28, progress);
            }

            // draw bubbles
            progress = BUBBLE_PROGRESS[brewTime / 2 % 7];
            if (progress > 0) {
                for (int i = 0; i < 2; i++) {
                    drawTexture(matrices,  x + (i == 0 ? LEFT_BUBBLE_X : RIGHT_BUBBLE_X), y + 63 - progress, dubious ? DUBIOUS_BUBBLE_UV_X : BUBBLE_UV_X, (blend ? BLEND_BUBBLE_UV_Y : BUBBLE_UV_Y) - progress, 12, progress);
                }
            }
        }
    }
}
