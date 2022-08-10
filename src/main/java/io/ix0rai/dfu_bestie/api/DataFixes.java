package io.ix0rai.dfu_bestie.api;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.DataFixerBuilder;
import io.ix0rai.dfu_bestie.impl.DataFixesInternals;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public final class DataFixes {
    public static final BiFunction<Integer, Schema, Schema> MOD_SCHEMA =
            (versionKey, parent) -> DataFixesInternals.getModSchema();

    public static void registerFixer(@NotNull String modId, int currentVersion, @NotNull DataFixerBuilder dataFixer) {
        DataFixesInternals.checkNotNull(modId, "modId");
        DataFixesInternals.checkNotNull(dataFixer, "dataFixer");
        Preconditions.checkArgument(currentVersion >= 0, "currentVersion must be positive");
        if (DataFixesInternals.isLocked())
            throw new IllegalStateException("Can't register data fixer after registration is locked!");
        DataFixesInternals.registerFixer(modId, currentVersion, dataFixer.build(Util.getMainWorkerExecutor()));
    }
}
