package adudecalledleo.dfubuddy.mixin.client;

import adudecalledleo.dfubuddy.impl.ModDataFixesInternals;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HotbarStorage.class)
public class HotbarStorageMixin {
    @Inject(method = "save",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtIo;write(Lnet/minecraft/nbt/NbtCompound;Ljava/io/File;)V"),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void addModDataVersions(CallbackInfo ci, NbtCompound tag) {
        ModDataFixesInternals.addModDataVersions(tag);
    }
}
