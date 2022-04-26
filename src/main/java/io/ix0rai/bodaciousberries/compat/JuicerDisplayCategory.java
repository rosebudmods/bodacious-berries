package io.ix0rai.bodaciousberries.compat;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class JuicerDisplayCategory implements DisplayCategory<JuicerDisplay> {
    public static final MutableText TITLE = new TranslatableText("rei.bodaciousberries.juicer");
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(BodaciousBlocks.JUICER_BLOCK);

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Text getTitle() {
        return TITLE;
    }

    @Override
    public CategoryIdentifier<? extends JuicerDisplay> getCategoryIdentifier() {
        return JuicerDisplay.IDENTIFIER;
    }

    @Override
    public List<Widget> setupDisplay(JuicerDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 52, bounds.getCenterY() - 29);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        //add juicer UI texture as well as animated bubbles
        widgets.add(Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, delta) -> {
            RenderSystem.setShaderTexture(0, Bodaciousberries.id("textures/gui/juicer_rei.png"));
            helper.drawTexture(matrices, startPoint.x + 20, startPoint.y, 55, 17, 64, 59);
            int height = MathHelper.floor(System.currentTimeMillis() / 250d % 9d);
            helper.drawTexture(matrices, startPoint.x + 24, startPoint.y + 24 + height, 176, height, 11, 9 - height);
            helper.drawTexture(matrices, startPoint.x + 70, startPoint.y + 24 + height, 176, height, 11, 9 - height);
        }));

        //we don't talk about the slots
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 24, startPoint.y + 1)).entries(display.getInputEntries().get(0)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44, startPoint.y + 8)).entries(display.getInputEntries().get(1)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 64, startPoint.y + 1)).entries(display.getInputEntries().get(2)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 21, startPoint.y + 34)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44, startPoint.y + 41)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 67, startPoint.y + 34)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
}
