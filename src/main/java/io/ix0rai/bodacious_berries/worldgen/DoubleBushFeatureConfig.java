package io.ix0rai.bodacious_berries.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record DoubleBushFeatureConfig(BlockStateProvider babyForm, BlockStateProvider tallForm) implements FeatureConfig {
    public static final Codec<DoubleBushFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("baby_form").forGetter(config -> config.babyForm),
                            BlockStateProvider.TYPE_CODEC.fieldOf("tall_form").forGetter(config -> config.tallForm))
                    .apply(instance, DoubleBushFeatureConfig::new)
    );
}
