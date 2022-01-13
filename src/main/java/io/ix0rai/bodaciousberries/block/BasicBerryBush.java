package io.ix0rai.bodaciousberries.block;

import io.ix0rai.bodaciousberries.registry.Sounds;
import io.ix0rai.bodaciousberries.util.ImproperConfigurationException;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BasicBerryBush extends PlantBlock implements BerryBush {
    private static final Vec3d BERRY_BUSH_SLOWING_VECTOR = new Vec3d(0.5D, 0.25D, 0.5D);
    //chance to grow is one in growChance
    private static final int GROW_CHANCE = 5;
    protected static final int MAX_BERRY_AMOUNT = 3;
    private static final Random RANDOM = new Random();

    protected Item berryType;
    protected Item unripeBerryType;
    public static final IntProperty AGE = IntProperty.of("age", 0 ,10);
    protected final int maxAge;
    protected final VoxelShape smallShape;
    protected final VoxelShape largeShape;
    protected final int sizeChangeAge;

    //animals that can move through bushes without being slowed
    public static final List<EntityType<?>> SMALL_ENTITIES = Arrays.asList(new EntityType<?>[]{
            EntityType.FOX,
            EntityType.BEE,
            EntityType.RABBIT,
            EntityType.CAT,
            EntityType.ENDERMITE,
            EntityType.BAT,
            EntityType.SILVERFISH,
            EntityType.OCELOT,
            EntityType.PARROT
    });

    /**
     * default berry bush constructor
     * @param settings block settings for this berry bush
     * @param berryType which berries will be given when this bush is picked from
     * @param maxAge maximum age bush can grow to
     * @param smallShape small voxel shape for the bush
     * @param largeShape large voxel shape for the bush
     * @param sizeChangeAge the age when the bush switches from smallShape to largeShape, this will also be the age it resets to when berries are picked
     */
    public BasicBerryBush(Settings settings, Item berryType, int maxAge, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        this(settings, berryType, null, maxAge, smallShape, largeShape, sizeChangeAge);
    }

    /**
     * secondary berry bush constructor
     * @param settings block settings for this berry bush
     * @param berryType which berries will be given when this bush is picked from
     * @param unripeBerryType which type of berries will be given when this bush is picked from, but not yet fully grown
     * @param maxAge maximum age bush can grow to
     * @param smallShape small voxel shape for the bush
     * @param largeShape large voxel shape for the bush
     * @param sizeChangeAge the age when the bush switches from smallShape to largeShape, this will also be the age it resets to when berries are picked
     */
    public BasicBerryBush(Settings settings, Item berryType, Item unripeBerryType, int maxAge, VoxelShape smallShape, VoxelShape largeShape, int sizeChangeAge) {
        //add nonOpaque to settings to ensure that the bush isn't considered a solid block when rendering
        super(settings.nonOpaque());
        this.berryType = berryType;
        this.maxAge = maxAge;
        this.smallShape = smallShape;
        this.largeShape = largeShape;
        this.unripeBerryType = unripeBerryType;
        this.sizeChangeAge = sizeChangeAge;
        //set default age to 0
        this.setDefaultState((this.stateManager.getDefaultState()).with(AGE, 0));
        //ensure cutout texture is rendered
        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
    }

    /**
     * sets the berry type
     * @param berryType the item to use
     */
    @Override
    public void setBerryType(Item berryType) {
        this.berryType = berryType;
    }

    /**
     * sets the unripe berry type
     * @param unripeBerryType the item to use
     */
    @Override
    public void setUnripeBerryType(Item unripeBerryType) {
        this.unripeBerryType = unripeBerryType;
    }

    /**
     * used for the pick block key
     * @return what kind of berries this block grows
     */
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(berryType);
    }

    /**
     * get the shape of this bush
     * @return a {@link VoxelShape} corresponding to its current age
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(AGE) < sizeChangeAge ? smallShape : largeShape;
    }

    /**
     * determines whether this block still needs to be random ticked - i.e. whether it can still grow or not
     */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < maxAge;
    }

    /**
     * runs when this bush is ticked
     * grows the bush if it can, a random throw is met, and light level is high enough
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(AGE);
        //if the age isn't maximum and the light level is high enough grow the bush
        if (age <= maxAge && random.nextInt(GROW_CHANCE) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            world.setBlockState(pos, state.with(AGE, age + 1), 2);
        }
    }

    /**
     * handles entity collision with our bush
     * <br> if the entity isn't on our list of small entities, slow it
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !SMALL_ENTITIES.contains(entity.getType())) {
            entity.slowMovement(state, BERRY_BUSH_SLOWING_VECTOR);
        }
    }

    /**
     * get a random berry pick sound
     */
    public static SoundEvent selectPickSound() {
        return switch (RANDOM.nextInt(3)) {
            case 1 -> Sounds.BERRY_PICK_2;
            case 2 -> Sounds.BERRY_PICK_3;
            default -> Sounds.BERRY_PICK_1;
        };
    }

    /**
     * handles when our berry bush is right-clicked
     * <br> if the player clicking has bone meal, grow the plant if possible or pick berries if fully grown
     * <br> otherwise, pick berries if possible
     * @return whether the action fails or passes
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (berryType == null) {
            throw new ImproperConfigurationException("parameter berryType is null, use method setBerryType(Item) to ensure that it is set before the berry bush is registered");
        }

        final int currentAge = state.get(AGE);
        //if bone meal is allowed to be used, grow plant and pass action
        if (hasRandomTicks(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (currentAge > 1) {
            //otherwise, give berries/unripe berries
            return pickBerries(pos, world, state, berryType, unripeBerryType, MAX_BERRY_AMOUNT, maxAge, sizeChangeAge, AGE);
        } else {
            //otherwise, do default use action from superclass
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    /**
     * handles berries being picked
     * <br> this method is static so that it can be used in {@link DoubleBerryBush}
     */
    public static ActionResult pickBerries(BlockPos pos, World world, BlockState state, Item berryType, Item unripeBerryType, int maxBerryAmount, int maxAge, int sizeChangeAge, IntProperty berryAge) {
        //pick berry amount
        //up to three berries
        int berryAmount = world.random.nextInt(maxBerryAmount + 1);
        final int currentAge = state.get(berryAge);

        //if growing not finished give unripe berries
        if (currentAge < maxAge) {
            //if we have an unripe berry type, provide unripe berries and maybe one berry
            //if we don't, ensure a berry
            boolean giveRipeBerry;
            if (unripeBerryType != null) {
                dropStack(world, pos, new ItemStack(unripeBerryType, berryAmount));
                berryAmount = 1;
                giveRipeBerry = world.random.nextInt(2) == 0;
            } else {
                return ActionResult.FAIL;
            }

            //if age is one under maximum, have a chance of getting a ripe berry
            if (currentAge == maxAge - 1 && giveRipeBerry) {
                dropStack(world, pos, new ItemStack(berryType, berryAmount));
            }
        } else {
            //guarantee two berries
            berryAmount += 2;
            dropStack(world, pos, new ItemStack(berryType, berryAmount));
        }

        //play randomly picked sound
        world.playSound(null, pos, selectPickSound(), SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);

        //reset berry growth; they were just picked
        world.setBlockState(pos, state.with(berryAge, sizeChangeAge), 2);
        return ActionResult.success(world.isClient);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * check that the plant is bone meal-able, also known as whether the plant can grow
     * @return true if the plant can grow, false if it can't
     */
    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        //hasRandomTicks checks the same thing as this method
        return hasRandomTicks(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        //hasRandomTicks checks the same thing as this method
        return hasRandomTicks(state);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int newAge = Math.min(maxAge, state.get(AGE) + 1);
        grow(world, random, pos, state, newAge);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, Integer newAge) {
        world.setBlockState(pos, state.with(AGE, newAge), 2);
    }

    @Override
    public Item getBerryType() {
        return berryType;
    }

    @Override
    public Item getUnripeBerryType() {
        return unripeBerryType;
    }

    @Override
    public IntProperty getAge() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public VoxelShape getSmallShape() {
        return smallShape;
    }

    @Override
    public VoxelShape getLargeShape() {
        return largeShape;
    }

    public int getSizeChangeAge() {
        return sizeChangeAge;
    }
}
