package io.ix0rai.bodaciousberries.registry.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChorusBerryJuice extends Item {
    private final Identifier biome;

    public ChorusBerryJuice(Settings settings, Identifier biome) {
        super(settings);
        this.biome = biome;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //teleport user to biome specified in constructor
        if (biome != null) {
            MinecraftServer server = world.getServer();
            if (server != null) {
                ServerWorld serverWorld = server.getOverworld();
                //locate the biome to teleport to
                BlockPos teleportTo = serverWorld.locateBiome(
                        server.getRegistryManager().get(Registry.BIOME_KEY).getOrEmpty(biome).orElseThrow(),
                        user.getBlockPos(),
                        5000,
                        25
                );
                if (teleportTo != null) {
                    user.teleport(teleportTo.getX(), teleportTo.getY(), teleportTo.getZ(), true);
                    //teleport occasionally silently fails
                    if (!user.getBlockPos().equals(teleportTo)) {
                        user.requestTeleport(teleportTo.getX(), teleportTo.getY(), teleportTo.getZ());
                    }

                    //sending entity status 46 causes ender pearl particles to appear
                    world.sendEntityStatus(user, (byte) 46);
                }
            }
        }

        //consume item
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
        }

        //return empty bottle
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (user instanceof PlayerEntity playerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
                ItemStack serverPlayerEntity = new ItemStack(Items.GLASS_BOTTLE);
                if (!playerEntity.getInventory().insertStack(serverPlayerEntity)) {
                    playerEntity.dropItem(serverPlayerEntity, false);
                }
            }

            return stack;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
