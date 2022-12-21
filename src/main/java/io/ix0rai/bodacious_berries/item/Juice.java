package io.ix0rai.bodacious_berries.item;

import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.List;

public class Juice extends HoneyBottleItem {
    private final Item berry;
    private final MutableText hintTooltip;

    public Juice(Item berry) {
        this(berry, new FoodComponent.Builder(), null);
    }

    public Juice(Item berry, MutableText hintTooltip) {
        this(berry, new FoodComponent.Builder(), hintTooltip);
    }

    public Juice(Item berry, FoodComponent.Builder builder) {
        this(berry, builder, null);
    }

    public Juice(Item berry, FoodComponent.Builder builder, MutableText hintTooltip) {
        super(settings(berry, builder));
        this.berry = berry;
        this.hintTooltip = hintTooltip;
    }

    public Juice(Item.Settings settings) {
        super(settings);
        this.berry = null;
        this.hintTooltip = null;
    }

    public static Settings settings(Item berry, FoodComponent.Builder builder) {
        FoodComponent foodComponent = berry.getFoodComponent();
        if (foodComponent != null) {
            return BodaciousJuices.JUICE_SETTINGS.food(builder.hunger(foodComponent.getHunger() * 2).saturationModifier(foodComponent.getSaturationModifier() * 1.5f).build());
        }

        throw new InvalidParameterException("item: " + berry + " does not have a food component");
    }

    public Item getBerry() {
        if (berry == null) {
            throw new IllegalStateException("Illegal getBerry() call on a berry blend juice item! Please report this.");
        }
        return berry;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<net.minecraft.text.Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (hintTooltip != null) {
            tooltip.add(hintTooltip.formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        }
    }
}
