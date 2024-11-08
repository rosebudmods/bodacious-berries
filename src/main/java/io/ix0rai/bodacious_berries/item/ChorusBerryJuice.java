package io.ix0rai.bodacious_berries.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Holder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Optional;
import java.util.Set;

public class ChorusBerryJuice extends Juice {
    private final Identifier biome;

    public ChorusBerryJuice(BlockItem berry, Identifier biome) {
        super("chorus_berry_juice", berry);
        this.biome = biome;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // teleport user to biome specified in constructor
        // the biome can be null, in which case the user will not be teleported
        boolean success = false;

        if (biome != null && world instanceof ServerWorld serverWorld) {
            MinecraftServer server = serverWorld.getServer();
            // locate the biome to teleport to
            Optional<BlockPos> pos = locateBiome(server, serverWorld, user.getBlockPos());

            if (pos.isPresent()) {
                safeTeleport(pos.get(), serverWorld, user);
                success = true;
            }
        }

        // sending entity status 43 causes the player to emit some particles similar to the ones an explosion would emit, while 46 sends ender pearl particles
        world.sendEntityStatus(user, success ? (byte) 46 : (byte) 43);

        // consume item
        return super.finishUsing(stack, world, user);
    }

    private void safeTeleport(BlockPos pos, ServerWorld world, LivingEntity user) {
        do {
            pos = pos.up();
        } while ((!world.getBlockState(pos).getBlock().equals(Blocks.AIR)
                && !world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
                || pos.getY() == world.getHeight());

        user.teleport(world, pos.getX(), pos.getY(), pos.getZ(), Set.of(), 90, 0, true);
        SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
        user.playSound(soundEvent, 1.0F, 1.0F);
        world.playSound(null, user.getBlockPos(), soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    private Optional<BlockPos> locateBiome(MinecraftServer server, ServerWorld world, BlockPos pos) {
        Holder.Reference<Biome> biome = server.getRegistryManager().getLookup(RegistryKeys.BIOME).orElseThrow()
                .getHolder(RegistryKey.of(RegistryKeys.BIOME, this.biome)).orElseThrow();

        Pair<BlockPos, Holder<Biome>> pair = world.locateBiome(
                biome::equals,
                pos,
                6400,
                64,
                128
        );

        if (pair != null) {
            return Optional.of(pair.getFirst());
        } else {
            return Optional.empty();
        }
    }
}
