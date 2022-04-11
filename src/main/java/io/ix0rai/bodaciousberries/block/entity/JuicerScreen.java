package io.ix0rai.bodaciousberries.block.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JuicerScreen extends HandledScreen<JuicerScreenHandler> {
    private static final Identifier TEXTURE = Bodaciousberries.id("textures/gui/juicer.png");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 29, 25, 17, 11, 6, 0};
    private static final int DUBIOUS_BUBBLE_X = 188;
    private static final int DUBIOUS_PROGRESS_BAR_X = 227;
    private static final int BUBBLE_X = 176;
    private static final int PROGRESS_BAR_X = 200;
    private static final int BUBBLE_Y = 29;
    private static final int PROGRESS_BAR_Y = 0;
    private static final int BLEND_BUBBLE_Y = 59;
    private static final int BLEND_PROGRESS_BAR_Y = 31;

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

        //draw background
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        int brewTime = this.handler.getBrewTime();
        boolean dubious = this.handler.brewingDubiously();
        boolean blend = this.handler.brewingBlend();

        if (brewTime > 0) {
            //draw progress bar
            int progress = Math.round(28.0F * (1.0F - brewTime / (float) JuicerBlockEntity.TOTAL_BREW_TIME));
            if (progress > 0) {
                this.drawTexture(matrices, x + 73, y + 35, dubious ? DUBIOUS_PROGRESS_BAR_X : PROGRESS_BAR_X, blend ? BLEND_PROGRESS_BAR_Y : PROGRESS_BAR_Y, 28, progress);
            }

            //draw bubbles
            progress = BUBBLE_PROGRESS[brewTime / 2 % 7];
            if (progress > 0) {
                this.drawTexture(matrices, x + 58, y + 63 - progress, dubious ? DUBIOUS_BUBBLE_X : BUBBLE_X, (blend ? BLEND_BUBBLE_Y : BUBBLE_Y) - progress, 12, progress);
                this.drawTexture(matrices, x + 104, y + 63 - progress, dubious ? DUBIOUS_BUBBLE_X : BUBBLE_X, (blend ? BLEND_BUBBLE_Y : BUBBLE_Y) - progress, 12, progress);
            }
        }
    }
}
