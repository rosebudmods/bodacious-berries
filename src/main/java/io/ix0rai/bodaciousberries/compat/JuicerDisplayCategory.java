package io.ix0rai.bodaciousberries.compat;

import com.google.common.collect.Lists;
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
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 46, startPoint.y + 4)));

        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 85, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 85, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x - 20, startPoint.y + 5))
                .entries(display.getInputEntries().get(0))
                .markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 5))
                .entries(display.getInputEntries().get(1))
                .markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 20, startPoint.y + 5))
                .entries(display.getInputEntries().get(2))
                .markInput());

        return widgets;
    }
}
