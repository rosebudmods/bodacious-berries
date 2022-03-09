package io.ix0rai.bodaciousberries.block.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JuicerScreen extends HandledScreen<JuicerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("bodaciousberries:textures/gui/juicer.png");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 29, 25, 17, 11, 6, 0};

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
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        int brewTime = this.handler.getBrewTime();
        if (brewTime > 0) {
            int progress = Math.round(28.0F * (1.0F - brewTime / 400.0F));
            if (progress > 0) {
                this.drawTexture(matrices, x + 73, y + 34, 197, 0, 28, progress);
            }

            progress = BUBBLE_PROGRESS[brewTime / 2 % 7];
            if (progress > 0) {
                this.drawTexture(matrices, x + 58, y + 63 - progress, 185, 29 - progress, 12, progress);
                this.drawTexture(matrices, x + 104, y + 63 - progress, 185, 29 - progress, 12, progress);
            }
        }
    }
}
