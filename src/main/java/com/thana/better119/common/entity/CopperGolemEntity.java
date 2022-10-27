package com.thana.better119.common.entity;

import com.thana.better119.common.entity.goals.FindButtonGoal;
import com.thana.better119.common.entity.goals.TryPressButtonGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class CopperGolemEntity extends PathfinderMob implements IAnimatable {

    private static final EntityDataAccessor<Boolean> WAXED = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> OXIDIZATION_STATE = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);

    private static final List<Item> AXE = List.of(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE);

    private final AnimationFactory factory = new AnimationFactory(this);
    private BlockPos targetPos;

    public CopperGolemEntity(EntityType<CopperGolemEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateOxidationState();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.55F));
        this.goalSelector.addGoal(2, new TemptGoal(this, 0.5F, Ingredient.of(Items.COPPER_INGOT), false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FindButtonGoal(this));
        this.goalSelector.addGoal(7, new TryPressButtonGoal(this));
        super.registerGoals();
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(WAXED, false);
        this.entityData.define(OXIDIZATION_STATE, 0);
        super.defineSynchedData();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("waxed", this.getIsWaxed());
        tag.putInt("oxidation_state", this.getOxidizationState().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setWaxed(tag.getBoolean("waxed"));
        this.setOxidizationState(OxidizationState.values()[tag.getInt("oxidation_state")]);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }
        ItemStack itemStack = player.getMainHandItem();
        if (AXE.contains(itemStack.getItem())) {
            if (this.getIsWaxed()) {
                this.setWaxed(false);
                this.addParticle(ParticleTypes.WAX_OFF);
                this.playSound(SoundEvents.AXE_WAX_OFF);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
            else if (this.getOxidizationState() != OxidizationState.UNAFFECTED) {
                this.setOxidizationState(OxidizationState.values()[this.getOxidizationState().ordinal() - 1]);
                this.updateOxidationState();
                this.addParticle(ParticleTypes.SCRAPE);
                this.playSound(SoundEvents.AXE_SCRAPE);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
            else {
                return InteractionResult.PASS;
            }
        }
        else if (itemStack.is(Items.HONEYCOMB)) {
            this.setWaxed(true);
            this.addParticle(ParticleTypes.WAX_ON);
            this.playSound(SoundEvents.HONEYCOMB_WAX_ON);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.MOVEMENT_SPEED, 0.4F);
    }

    private void updateOxidationState() {
        OxidizationState state = this.getOxidizationState();
        if (!this.getIsWaxed() && state != OxidizationState.OXIDIZED) {
            if (this.tickCount % 393216 == 0) {
                int ordinal = state.ordinal() + 1;
                this.setOxidizationState(OxidizationState.values()[ordinal]);
            }
        }

        // Oxidized AI
        if (state == OxidizationState.OXIDIZED && !this.isNoAi()) {
            this.setNoAi(true);
            this.removeFreeWill();
        }
        else if (state != OxidizationState.OXIDIZED && this.isNoAi()) {
            this.setNoAi(false);
            this.registerGoals();
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.copper_golem.walk", true));
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.copper_golem.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 2.0F;
    }

    public BlockPos getTargetPos() {
        return this.targetPos;
    }

    public void setTargetPos(BlockPos pos) {
        this.targetPos = pos;
    }

    public void resetTargetPos() {
        this.setTargetPos(null);
    }

    public void setWaxed(boolean waxed) {
        this.entityData.set(WAXED, waxed);
    }

    public boolean getIsWaxed() {
        return this.entityData.get(WAXED);
    }

    public void setOxidizationState(OxidizationState state) {
        this.entityData.set(OXIDIZATION_STATE, state.ordinal());
    }

    public OxidizationState getOxidizationState() {
        return OxidizationState.values()[this.entityData.get(OXIDIZATION_STATE)];
    }

    private void addParticle(ParticleOptions particle) {
        if (!this.level.isClientSide) {
            ((ServerLevel) this.level).sendParticles(particle, this.getX(), this.getY() + 1.0D, this.getZ(), 20, 0.2D, 0.3D, 0.2D, 0.002D);
        }
    }

    public enum OxidizationState {
        UNAFFECTED(0.4F),
        EXPOSED(0.315F),
        WEATHERED(0.15F),
        OXIDIZED(0.0F);

        private final float walkSpeed;

        OxidizationState(float walkSpeed) {
            this.walkSpeed = walkSpeed;
        }

        public float getWalkSpeed() {
            return this.walkSpeed;
        }
    }
}
