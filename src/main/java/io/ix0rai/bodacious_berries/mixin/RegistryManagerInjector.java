package io.ix0rai.bodacious_berries.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.registry.BodaciousJuices;
import io.ix0rai.bodacious_berries.util.JuicerRecipeUtil;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * reason: add juicer recipes to the recipe manager
 * @author ix0rai
 */
@Mixin(RecipeManager.class)
public class RegistryManagerInjector {
    @Inject(method = "apply*", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for (JsonObject jsonObject : JuicerRecipeUtil.JUICER_RECIPES) {
            map.put(BodaciousBerries.id(jsonObject.get("result").getAsString().split(":")[1]), jsonObject);
        }

        for (JsonObject jsonObject : BodaciousJuices.RECIPES) {
            map.put(BodaciousBerries.id(jsonObject.get("result").getAsJsonObject().get("item").getAsString().split(":")[1]), jsonObject);
        }
    }
}
