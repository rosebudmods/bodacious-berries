package io.ix0rai.bodacious_berries.item;

import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;

import java.security.InvalidParameterException;

public class Juice extends HoneyBottleItem {
    public Juice(Item berry) {
        this(berry, new FoodComponent.Builder());
    }

    public Juice(Item berry, FoodComponent.Builder builder) {
        super(settings(berry, builder));
    }

    public Juice(Item.Settings settings) {
        super(settings);
    }

    public Juice(FoodComponent component) {
        super(new Settings().food(component));
    }

    public static Settings settings(Item berry, FoodComponent.Builder builder) {
        FoodComponent foodComponent = berry.getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            return BodaciousJuices.JUICE_SETTINGS.food(builder.hunger(foodComponent.nutrition() * 2).saturation(foodComponent.saturation() * 1.5f).build());
        }

        throw new InvalidParameterException("item: " + berry + " does not have a food component");
    }
}
