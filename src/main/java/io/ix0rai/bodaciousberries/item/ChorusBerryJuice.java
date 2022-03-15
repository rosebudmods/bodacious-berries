package io.ix0rai.bodaciousberries.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

public class ChorusBerryJuice extends Juice {
    private final Identifier biome;

    public ChorusBerryJuice(Item berry, Identifier biome) {
        super(berry);
        this.biome = biome;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //teleport user to biome specified in constructor
        //the biome can be null, in which case the user will not be teleported
        boolean success = false;

        if (biome != null) {
            MinecraftServer server = world.getServer();
            if (server != null) {
                //ensure we are in the overworld
                DynamicRegistryManager registryManager = server.getRegistryManager();
                if (world.getDimension().equals(registryManager.get(Registry.DIMENSION_TYPE_KEY).get(DimensionType.OVERWORLD_ID))) {
                    ServerWorld serverWorld = server.getOverworld();
                    RegistryEntry<Biome> biomeEntry = RegistryEntry.of(server.getRegistryManager().get(Registry.BIOME_KEY).get(biome));

                    //locate the biome to teleport to
                    Pair<BlockPos, RegistryEntry<Biome>> teleportTo = serverWorld.locateBiome(
                            entry -> (entry.value().equals(biomeEntry.value())),
                            user.getBlockPos(),
                            6400,
                            8
                    );

                    if (teleportTo != null) {
                        BlockPos pos = teleportTo.getFirst();

                        user.teleport(pos.getX(), pos.getY(), pos.getZ(), true);
                        //teleport occasionally silently fails
                        if (!user.getBlockPos().equals(pos)) {
                            user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
                        }

                        //sending entity status 46 causes ender pearl particles to appear
                        world.sendEntityStatus(user, (byte) 46);

                        //play teleporty sound
                        SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                        user.playSound(soundEvent, 1.0F, 1.0F);
                        world.playSound(null, user.getBlockPos(), soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);

                        //success!
                        success = true;
                    }
                }
            }
        }

        if (!success) {
            //sending entity status 43 causes the player to emit some particles similar to the ones an explosion would emit
            world.sendEntityStatus(user, (byte) 43);
        }

        //consume item
        return super.finishUsing(stack, world, user);
    }
}
