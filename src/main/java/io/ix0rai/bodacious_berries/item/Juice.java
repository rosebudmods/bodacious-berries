package io.ix0rai.bodacious_berries.item;

import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
            return BodaciousJuices.JUICE_SETTINGS.food(builder.hunger(foodComponent.getHunger() * 2).saturationModifier(foodComponent.getSaturationModifier() * 2f).build());
        }

        throw new InvalidParameterException("item: " + berry + " does not have a food component");
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // we handle changing the stack
        ItemStack stack1 = stack.copy();
        super.finishUsing(stack, world, user);
        stack = stack1;

        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
        }

        // return empty bottle, or throw it away if it does not fit
        // also decrement the stack size if it will not be entirely consumed
        if (stack.isEmpty()) {
            return new ItemStack(BodaciousJuices.JUICE_RECEPTACLE);
        } else {
            if (user instanceof PlayerEntity playerEntity && !playerEntity.getAbilities().creativeMode) {
                ItemStack glassBottle = new ItemStack(BodaciousJuices.JUICE_RECEPTACLE);
                if (!playerEntity.getInventory().insertStack(glassBottle)) {
                    playerEntity.dropItem(glassBottle, false);
                }

                if (stack.getCount() == 1) {
                    return glassBottle;
                } else {
                    stack.decrement(1);
                    return stack;
                }
            }

            return stack;
        }
    }

    public Item getBerry() {
        if (berry == null) {
            throw new IllegalStateException("this juice is a berry blend. shut up.");
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
