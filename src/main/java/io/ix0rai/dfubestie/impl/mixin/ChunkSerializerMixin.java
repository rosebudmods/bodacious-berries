package io.ix0rai.dfubestie.impl.mixin;

import io.ix0rai.dfubestie.impl.DataFixesInternals;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Inject(method = "serialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putInt(Ljava/lang/String;I)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void addModDataVersions(ServerWorld world, Chunk chunk, CallbackInfoReturnable<NbtCompound> cir, ChunkPos chunkPos, NbtCompound compoundTag) {
        DataFixesInternals.addModDataVersions(compoundTag);
    }
}
