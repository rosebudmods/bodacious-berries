package io.ix0rai.bodaciousberries.mixin.accessors;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Biome.class)
public interface BiomeCategoryAccessor {
    @Accessor
    Biome.Category getCategory();
}
