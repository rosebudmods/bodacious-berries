package io.ix0rai.bodaciousberries.compat;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.registry.BodaciousBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class JuicerDisplayCategory implements DisplayCategory<JuicerDisplay> {
    public static final Text TITLE = Text.of("rei.bodaciousberries.juicer");
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(BodaciousBlocks.JUICER_BLOCK);
    private static final Identifier TEXTURE = Bodaciousberries.id("textures/gui/juicer_rei.png");

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
        Point startPoint = new Point(bounds.getX(), bounds.getY() + 4);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, delta) -> {
            // add juicer background
            RenderSystem.setShaderTexture(0, TEXTURE);
            helper.drawTexture(matrices, startPoint.x + 20, startPoint.y, 55, 17, 64, 59);

            // add progress bar
            final double time = System.currentTimeMillis() / 250d;

            int progress = MathHelper.floor(time % 16);
            helper.drawTexture(matrices, startPoint.x + 38, startPoint.y + 24, 187, 0, 28, progress);

            // add animated bubbles
            int height = MathHelper.floor(time % 9);
            helper.drawTexture(matrices, startPoint.x + 24, startPoint.y + 24 + height, 176, height, 11, 9 - height);
            helper.drawTexture(matrices, startPoint.x + 70, startPoint.y + 24 + height, 176, height, 11, 9 - height);
        }));

        // show receptacle
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 107, startPoint.y + 21)).entries(display.getReceptacleEntry()).markInput());

        // we don't talk about the slots
        widgets.add(createSlot(new Point(startPoint.x + 24, startPoint.y + 1), display.getInputEntries().get(0), true));
        widgets.add(createSlot(new Point(startPoint.x + 44, startPoint.y + 8), display.getInputEntries().get(1), true));
        widgets.add(createSlot(new Point(startPoint.x + 64, startPoint.y + 1), display.getInputEntries().get(2), true));
        widgets.add(createSlot(new Point(startPoint.x + 21, startPoint.y + 34), display.getOutputEntries().get(0), false));
        widgets.add(createSlot(new Point(startPoint.x + 44, startPoint.y + 41), display.getOutputEntries().get(0), false));
        widgets.add(createSlot(new Point(startPoint.x + 67, startPoint.y + 34), display.getOutputEntries().get(0), false));
        return widgets;
    }

    private Slot createSlot(Point point, EntryIngredient item, boolean input) {
        Slot slot = Widgets.createSlot(point).entries(item).disableBackground();
        if (input) {
            slot.markInput();
        } else {
            slot.markOutput();
        }

        return slot;
    }
}
