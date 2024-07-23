package io.ix0rai.bodacious_berries.config;

import io.ix0rai.bodacious_berries.BodaciousBerries;
import io.ix0rai.bodacious_berries.registry.Berry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.Option;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static io.ix0rai.bodacious_berries.BodaciousBerries.CONFIG;

@Environment(EnvType.CLIENT)
public class BodaciousConfigScreen extends GameOptionsScreen {
    public BodaciousConfigScreen(@Nullable Screen parent) {
        super(parent, MinecraftClient.getInstance().options, BodaciousBerries.translatableText("config.title"));
    }

    @SuppressWarnings("unchecked")
    private static Option<Boolean>[] createOptions() {
        List<Option<Boolean>> options = new ArrayList<>();
        for (Berry berry : Berry.values()) {
            options.add(createGenOption(berry.toString(),
                    value -> CONFIG.setGenerating(berry, value)
            ));
        }

        return options.toArray(Option[]::new);
    }

    private static Option<Boolean> createGenOption(String key, Consumer<Boolean> setter) {
        return Option.ofBoolean(BodaciousBerries.translatableTextKey("config." + "generate_" + key), true, setter);
    }

    @Override
    protected void method_60325() {
        this.field_51824.addEntries(createOptions());
    }
}