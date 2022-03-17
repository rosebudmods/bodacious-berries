package io.ix0rai.bodaciousberries.item;

import io.ix0rai.bodaciousberries.Bodaciousberries;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.util.Identifier;

public class Berry extends AliasedBlockItem {
    private final Identifier id;
    public Berry(Block block, String name, Settings settings) {
        super(block, settings);
        this.id = Bodaciousberries.getIdentifier(name);
    }

    public Identifier getId() {
        return id;
    }
}
