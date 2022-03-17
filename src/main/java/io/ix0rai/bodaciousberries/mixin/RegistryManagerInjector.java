package io.ix0rai.bodaciousberries.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ix0rai.bodaciousberries.block.entity.JuicerRecipes;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RegistryManagerInjector {
    @Inject(method = "apply*", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for (JsonObject jsonObject : JuicerRecipes.JUICER_RECIPES) {
            map.put(new Identifier(jsonObject.get("result").getAsString().split(":")[1]), jsonObject);
        }
    }
}
