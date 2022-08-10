package io.ix0rai.dfu_bestie.impl.mixin;

import io.ix0rai.dfu_bestie.impl.DataFixesInternals;
import com.mojang.datafixers.DataFixer;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NbtHelper.class)
public class NbtHelperMixin {
    @Inject(method = "update(Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/datafixer/DataFixTypes;Lnet/minecraft/nbt/NbtCompound;I)Lnet/minecraft/nbt/NbtCompound;",
            at = @At("RETURN"), cancellable = true)
    private static void updateDataWithFixers(DataFixer fixer, DataFixTypes fixTypes, NbtCompound compound, int oldVersion, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound original = cir.getReturnValue(); // we do our fixes after vanilla
        NbtCompound finalTag = DataFixesInternals.updateWithAllFixers(fixTypes, original);
        cir.setReturnValue(finalTag);
    }
}
