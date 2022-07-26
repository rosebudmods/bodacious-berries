package adudecalledleo.dfubuddy.mixin;

import adudecalledleo.dfubuddy.impl.ModDataFixesInternals;
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
        ModDataFixesInternals.addModDataVersions(out);
        cir.setReturnValue(out);
    }
}
