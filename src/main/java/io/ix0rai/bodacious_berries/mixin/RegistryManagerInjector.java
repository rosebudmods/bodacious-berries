package io.ix0rai.bodacious_berries.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.block.entity.JuicerRecipe;
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
        for (Map.Entry<Identifier, JsonElement> entry : map.entrySet()) {
            JsonElement element = entry.getValue();
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("type") && object.get("type").getAsString().equals(BodaciousBerries.idString("juicer"))) {
                    JuicerRecipe.RECIPES.add(JuicerRecipe.SERIALIZER.read(entry.getKey(), object));
                }
            }
        }
    }
}
