package io.ix0rai.bodaciousberries.registry;

import com.google.gson.JsonObject;
import io.ix0rai.bodaciousberries.Bodaciousberries;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipe;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import io.ix0rai.bodaciousberries.item.ChorusBerryJuice;
import io.ix0rai.bodaciousberries.item.Juice;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

@SuppressWarnings("deprecation")
public class Juices {
    public static final Item RECEPTACLE = Items.GLASS_BOTTLE;
    public static final Item.Settings JUICE_SETTINGS = new Item.Settings().recipeRemainder(RECEPTACLE).group(ItemGroup.FOOD).maxCount(16);
    public static final List<JsonObject> RECIPES = new ArrayList<>();

    public static void registerJuice() {
        Registry.register(Registry.RECIPE_SERIALIZER, JuicerRecipe.JuicerRecipeSerializer.ID, JuicerRecipe.JuicerRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, JuicerRecipe.Type.ID, JuicerRecipe.Type.INSTANCE);

        register("saskatoon_berry_juice", new Juice(Berries.SASKATOON_BERRIES));
        register("strawberry_juice", new Juice(Berries.STRAWBERRY));
        register("raspberry_juice", new Juice(Berries.RASPBERRIES));
        register("blackberry_juice", new Juice(Berries.BLACKBERRIES));
        register("rainberry_juice", new Juice(Berries.RAINBERRY));
        register("lingonberry_juice", new Juice(Berries.LINGONBERRIES));
        register("grape_juice", new Juice(Berries.GRAPES));
        register("goji_berry_juice", new Juice(Berries.GOJI_BERRIES));
        register("gooseberry_juice", new Juice(Berries.GOOSEBERRIES));
        register("glow_berry_juice", new Juice(Items.GLOW_BERRIES, new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 90, 1), 1.0F)));
        register("sweet_berry_juice", new Juice(Items.SWEET_BERRIES, new FoodComponent.Builder()));
        register("chorus_berry_juice", new ChorusBerryJuice(Berries.CHORUS_BERRIES, null));

        List<RegistryKey<Biome>> biomes = List.of(
                PLAINS, SNOWY_SLOPES, SWAMP,
                DESERT, TAIGA, BIRCH_FOREST,
                OCEAN, MUSHROOM_FIELDS, SUNFLOWER_PLAINS,
                FOREST, FLOWER_FOREST, DARK_FOREST,
                SAVANNA, BADLANDS, MEADOW,
                LUSH_CAVES, DRIPSTONE_CAVES, JUNGLE
        );

        List<Item> biomeItems = List.of(
                Items.CORNFLOWER, Items.SNOWBALL, Items.CLAY_BALL,
                Items.SAND, Items.SWEET_BERRIES, Items.BIRCH_SAPLING,
                Items.COD, Items.RED_MUSHROOM, Items.SUNFLOWER,
                Items.OAK_SAPLING, Items.PEONY, Items.DARK_OAK_SAPLING,
                Items.ACACIA_SAPLING, Items.RED_SAND, Items.POPPY,
                Items.GLOW_BERRIES, Items.POINTED_DRIPSTONE, Items.COCOA_BEANS
        );

        for (int i = 0; i < biomes.size(); i++) {
            RegistryKey<Biome> key = biomes.get(i);
            ChorusBerryJuice juice = new ChorusBerryJuice(Berries.CHORUS_BERRIES, key.getValue());
            Identifier id = Bodaciousberries.getIdentifier("chorus_berry_juice_" + key.getValue().getPath());

            RECIPES.add(JuicerRecipes.createShapelessJson(biomeItems.get(i).getRegistryEntry().registryKey().getValue(), id));

            Registry.register(Registry.ITEM, id, juice);
        }
    }

    public static void register(String name, Juice juice) {
        Identifier id = Bodaciousberries.getIdentifier(name);
        JuicerRecipes.addRecipe(juice.getBerry().getRegistryEntry().registryKey().getValue(), id);
        Registry.register(Registry.ITEM, Bodaciousberries.getIdentifier(name), juice);
    }
}
