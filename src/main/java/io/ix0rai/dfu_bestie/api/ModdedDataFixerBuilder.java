package io.ix0rai.dfu_bestie.api;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;

import java.util.function.BiConsumer;

public class ModdedDataFixerBuilder extends DataFixerBuilder {
    public ModdedDataFixerBuilder(int dataVersion, int currentSchemaVersion) {
        super(dataVersion);
        this.addSchema(currentSchemaVersion, DataFixes.MOD_SCHEMA);
    }

    public void initialiseWithNewSchema(int dataVersion, BiConsumer<DataFixerBuilder, Schema> initializer)  {
        Schema newSchema = this.addSchema(dataVersion, Schema::new);
        initializer.accept(this, newSchema);
    }
}
