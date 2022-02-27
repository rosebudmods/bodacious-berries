package io.ix0rai.bodaciousberries.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.ix0rai.bodaciousberries.block.DoubleBerryBush;
import io.ix0rai.bodaciousberries.block.GrowingBerryBush;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record DoubleBushFeatureConfig(BlockStateProvider tallForm, BlockStateProvider babyForm) implements FeatureConfig {
    public static final Codec<DoubleBushFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("tall_form").forGetter(config -> config.tallForm),
            BlockStateProvider.TYPE_CODEC.fieldOf("baby_form").forGetter(config -> config.babyForm))
            .apply(instance, DoubleBushFeatureConfig::new)
    );

    public DoubleBushFeatureConfig {
        try {
            if (!(tallForm.getBlockState(null, null).getBlock() instanceof DoubleBerryBush && babyForm.getBlockState(null, null).getBlock() instanceof GrowingBerryBush)) {
                throw new IllegalArgumentException("DoubleBushFeature requires a DoubleBerryBush and a GrowingBerryBush");
            }
        } catch (NullPointerException ignored) {
            //avert a game crash if the check above fails but doesn't return false
        }
    }
}
