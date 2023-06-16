package io.ix0rai.bodacious_berries.block.entity;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Language;

/**
 * overrides drawForeground to actually translate the title because minecraft is both stupid and stupid at the same time
 * <br>like seriously what even is this bug?
 * <br>this bothers me so much I hope I can remove it someday
 */
public abstract class BodaciousScreen<T extends ScreenHandler> extends HandledScreen<T> {
    protected BodaciousScreen(T screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Override
    protected void drawForeground(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawText(this.textRenderer, Language.getInstance().get(this.title.getString()), this.titleX, this.titleY, 4210752, false);
        graphics.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752, false);
    }
}
