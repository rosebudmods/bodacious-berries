package io.ix0rai.bodacious_berries.item;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.registry.Berry;
import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ConsumableComponent;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.unmapped.C_uaiigijw;
import net.minecraft.util.Identifier;

import java.security.InvalidParameterException;

public class Juice extends Item {
    protected final Identifier itemId;

    public Juice(Berry berry, Item item) {
        this(berry, item, new FoodComponent.Builder(), C_uaiigijw.method_62859());
    }

    public Juice(String id, Item item) {
        this(id, item, new FoodComponent.Builder(), C_uaiigijw.method_62859());
    }

    public Juice(Berry berry, Item item, FoodComponent.Builder foodBuilder, ConsumableComponent.C_jkfukdnt consumableBuilder) {
        super(settings(berry.juiceId(), item, foodBuilder, consumableBuilder));

        this.itemId = berry.juiceId();
    }

    public Juice(String id, Item item, FoodComponent.Builder foodBuilder, ConsumableComponent.C_jkfukdnt consumableBuilder) {
        super(settings(BodaciousBerries.id(id), item, foodBuilder, consumableBuilder));

        this.itemId = BodaciousBerries.id(id);
    }

    public Juice(String id, Item.Settings settings) {
        super(settings.key(RegistryKey.of(RegistryKeys.ITEM, BodaciousBerries.id(id))));

        this.itemId = BodaciousBerries.id(id);
    }

    public Identifier getJuiceId() {
        return this.itemId;
    }

    public static Settings settings(Identifier id, Item item, FoodComponent.Builder foodBuilder, ConsumableComponent.C_jkfukdnt consumableBuilder) {
        FoodComponent foodComponent = item.getComponents().get(DataComponentTypes.FOOD);
        if (foodComponent != null) {
            return BodaciousJuices.JUICE_SETTINGS.key(RegistryKey.of(RegistryKeys.ITEM, id)).food(
                    foodBuilder.hunger(foodComponent.nutrition() * 2).saturation(foodComponent.saturation() * 1.5f).build(),
                    consumableBuilder.method_62851());
        }

        throw new InvalidParameterException("item: " + item + " does not have a food component");
    }
}
