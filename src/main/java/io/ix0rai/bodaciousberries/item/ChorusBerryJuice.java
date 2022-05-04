package io.ix0rai.bodaciousberries.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

public class ChorusBerryJuice extends Juice {
    private final Identifier biome;

    public ChorusBerryJuice(AliasedBlockItem berry, Identifier biome) {
        super(berry);
        this.biome = biome;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //teleport user to biome specified in constructor
        //the biome can be null, in which case the user will not be teleported
        boolean success = false;

        if (biome != null && world.getServer() != null) {
            MinecraftServer server = world.getServer();
            //ensure we are in the overworld
            if (world.getDimension().equals(server.getRegistryManager().get(Registry.DIMENSION_TYPE_KEY).get(DimensionType.OVERWORLD_ID))) {
                //locate the biome to teleport to
                Pair<BlockPos, Boolean> pair = locateBiome(server, user.getBlockPos(), user);
                BlockPos pos = pair.getFirst();
                success = pair.getSecond();

                if (success) {
                    safeTeleport(pos, world, user);
                }
            }
        }

        //sending entity status 43 causes the player to emit some particles similar to the ones an explosion would emit, while 46 sends ender pearl particles
        world.sendEntityStatus(user, success ? (byte) 46 : (byte) 43);

        //consume item
        return super.finishUsing(stack, world, user);
    }

    private void safeTeleport(BlockPos pos, World world, LivingEntity user) {
        do {
            pos = pos.up();
        } while ((!world.getBlockState(pos).getBlock().equals(Blocks.AIR)
                && !world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
                || pos.getY() == world.getHeight());

        user.requestTeleportAndDismount(pos.getX(), pos.getY(), pos.getZ());
        SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
        user.playSound(soundEvent, 1.0F, 1.0F);
        world.playSound(null, user.getBlockPos(), soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    private Pair<BlockPos, Boolean> locateBiome(MinecraftServer server, BlockPos pos, LivingEntity user) {
        Pair<BlockPos, Holder<Biome>> pair = server.getOverworld().locateBiome(
                entry -> (entry.value().equals(Holder.createDirect(server.getRegistryManager().get(Registry.BIOME_KEY).get(biome)).value())),
                user.getBlockPos(),
                6400,
                8
        );

        if (pair != null) {
            return Pair.of(pair.getFirst(), true);
        } else {
            return Pair.of(pos, false);
        }
    }
}
