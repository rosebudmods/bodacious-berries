package io.ix0rai.dfu_bestie.impl.mixin;

import io.ix0rai.dfu_bestie.impl.DataFixesInternals;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.piece.StructurePieceSerializationContext;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureStart.class)
public class StructureMixin {
    @Inject(method = "toNbt", at = @At("TAIL"), cancellable = true)
    private void addModDataVersions(StructurePieceSerializationContext context, ChunkPos chunkPos, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound out = cir.getReturnValue();
        DataFixesInternals.addModDataVersions(out);
        cir.setReturnValue(out);
    }
}
