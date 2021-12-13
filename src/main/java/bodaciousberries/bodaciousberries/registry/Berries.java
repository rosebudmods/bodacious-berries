package bodaciousberries.bodaciousberries.registry;

import bodaciousberries.bodaciousberries.block.plant.BerryBush;
import bodaciousberries.bodaciousberries.block.plant.DoubleBerryBush;
import net.fabricmc.fabric.impl.content.registry.CompostingChanceRegistryImpl;
import net.minecraft.item.Item;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class Berries {
    /**
     * map containing a berry bush, its associated berry, and unripe form if applicable
     * <br> should not be directly added to, use {@link #addToList(BerryBush, Item, Item)}
     */
    private static final Map<BerryBush, Pair<Item, Item>> BERRY_BUSHES = new HashMap<>();

    /**
     * the chance that a berry will raise the level of compost in a composter
     * <br> out of one: 0.4 = 40%, etc
     */
    private static final float BERRY_COMPOST_LEVEL_INCREASE_CHANCE = 0.4f;

    /**
     * makes a berry item compostable via composter
     * @param berry the berry to make compostable
     */
    private static void registerCompostableBerry(Item berry) {
        CompostingChanceRegistryImpl.INSTANCE.add(berry, BERRY_COMPOST_LEVEL_INCREASE_CHANCE);
    }

    /**
     * registers two berries to be compostable at once using {@link #registerCompostableBerry(Item)}
     * @param berry1 the first berry to be made compostable
     * @param berry2 the second berry to be made compostable
     */
    private static void registerCompostableBerries(Item berry1, Item berry2) {
        registerCompostableBerry(berry1);
        registerCompostableBerry(berry2);
    }

    /**
     * add a berry bush to the automatic registration list
     * @param bush the berry bush you wish to associate your berries with
     * @param berries the base form of the berries to associate
     * @param unripeBerries the unripe form of the berries to associate
     */
    public static void addToList(BerryBush bush, Item berries, Item unripeBerries) {
        BERRY_BUSHES.put(bush, new Pair<>(berries, unripeBerries));
    }

    /**
     * uses {@link #addToList(BerryBush, Item, Item)} to add both forms of a double berry bush to the automatic registration list
     * @param smallBush the small form of the berry bush
     * @param bigBush the double form of the berry bush
     * @param berries the base form of the berries to associate
     * @param unripeBerries the unripe form of the berries to associate
     */
    public static void addDoubleBushToList(BerryBush smallBush, DoubleBerryBush bigBush, Item berries, Item unripeBerries) {
        var berryPair = new Pair<>(berries, unripeBerries);
        BERRY_BUSHES.put(smallBush, berryPair);
        BERRY_BUSHES.put(bigBush, berryPair);
    }

    /**
     * sets the berry type of each bush in the list and registers all berries as compostable
     */
    public static void initialiseBerries() {
        for (var entry : BERRY_BUSHES.entrySet()) {
            BerryBush bush = entry.getKey();
            //set berry types
            Item berryType = entry.getValue().getA();
            bush.setBerryType(berryType);
            //honestly this probably isn't an optimisation but :hahayes:
            Item unripeBerryType = entry.getValue().getB();
            if (unripeBerryType != null) {
                bush.setUnripeBerryType(unripeBerryType);
            }

            //register as compostable
            if (unripeBerryType != null) {
                registerCompostableBerries(entry.getValue().getA(), unripeBerryType);
            } else {
                registerCompostableBerry(berryType);
            }
        }
    }
}
