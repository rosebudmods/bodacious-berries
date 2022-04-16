package io.ix0rai.bodaciousberries.item;

import net.minecraft.item.Item;

public class Blend extends Juice {
    private final Item ingredient0;
    private final Item ingredient1;
    private final Item ingredient2;

    public Blend(Settings settings, Item ingredient0, Item ingredient1, Item ingredient2) {
        super(settings);
        this.ingredient0 = ingredient0;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
    }

    public Item getIngredient0() {
        return ingredient0;
    }

    public Item getIngredient1() {
        return ingredient1;
    }

    public Item getIngredient2() {
        return ingredient2;
    }
}
