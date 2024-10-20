package io.ix0rai.bodacious_berries.item;

import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ConsumableComponent;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.unmapped.C_uaiigijw;

import java.security.InvalidParameterException;

public class Juice extends Item {
    public Juice(Item berry) {
        this(berry, new FoodComponent.Builder(), C_uaiigijw.method_62859());
    }

    public Juice(Item berry, FoodComponent.Builder foodBuilder, ConsumableComponent.C_jkfukdnt consumableBuilder) {
        super(settings(berry, foodBuilder, consumableBuilder));
    }

    public Juice(Item.Settings settings) {
        super(settings);
    }

    public static Settings settings(Item berry, FoodComponent.Builder foodBuilder, ConsumableComponent.C_jkfukdnt consumableBuilder) {
        FoodComponent foodComponent = berry.getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            return BodaciousJuices.JUICE_SETTINGS.food(
                    foodBuilder.hunger(foodComponent.nutrition() * 2).saturation(foodComponent.saturation() * 1.5f).build(),
                    consumableBuilder.method_62851());
        }

        throw new InvalidParameterException("item: " + berry + " does not have a food component");
    }
}
