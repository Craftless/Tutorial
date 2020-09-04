package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StriderEntity extends AnimalEntity implements IRideable, IEquipable {
   private static final Ingredient field_234308_bu_ = Ingredient.fromItems(Items.WARPED_FUNGUS);
   private static final Ingredient field_234309_bv_ = Ingredient.fromItems(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK);
   private static final DataParameter<Integer> field_234310_bw_ = EntityDataManager.createKey(StriderEntity.class, DataSerializers.VARINT);
   private static final DataParameter<Boolean> field_234311_bx_ = EntityDataManager.createKey(StriderEntity.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Boolean> field_234312_by_ = EntityDataManager.createKey(StriderEntity.class, DataSerializers.BOOLEAN);
   private final BoostHelper field_234313_bz_ = new BoostHelper(this.dataManager, field_234310_bw_, field_234312_by_);
   private TemptGoal field_234306_bA_;
   private PanicGoal field_234307_bB_;

   public StriderEntity(EntityType<? extends StriderEntity> p_i231562_1_, World p_i231562_2_) {
      super(p_i231562_1_, p_i231562_2_);
      this.preventEntitySpawning = true;
      this.setPathPriority(PathNodeType.WATER, -1.0F);
      this.setPathPriority(PathNodeType.LAVA, 0.0F);
      this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
      this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
   }

   public static boolean func_234314_c_(EntityType<StriderEntity> p_234314_0_, IWorld p_234314_1_, SpawnReason p_234314_2_, BlockPos p_234314_3_, Random p_234314_4_) {
      BlockPos.Mutable blockpos$mutable = p_234314_3_.func_239590_i_();

      while(true) {
         blockpos$mutable.move(Direction.UP);
         if (!p_234314_1_.getFluidState(blockpos$mutable).isTagged(FluidTags.LAVA)) {
            break;
         }
      }

      return p_234314_1_.getBlockState(blockpos$mutable).isAir();
   }

   public void notifyDataManagerChange(DataParameter<?> key) {
      if (field_234310_bw_.equals(key) && this.world.isRemote) {
         this.field_234313_bz_.func_233616_a_();
      }

      super.notifyDataManagerChange(key);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_234310_bw_, 0);
      this.dataManager.register(field_234311_bx_, false);
      this.dataManager.register(field_234312_by_, false);
   }

   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      this.field_234313_bz_.func_233618_a_(compound);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.field_234313_bz_.func_233621_b_(compound);
   }

   public boolean isHorseSaddled() {
      return this.field_234313_bz_.func_233620_b_();
   }

   public boolean func_230264_L__() {
      return this.isAlive() && !this.isChild();
   }

   public void func_230266_a_(@Nullable SoundCategory p_230266_1_) {
      this.field_234313_bz_.func_233619_a_(true);
      if (p_230266_1_ != null) {
         this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_STRIDER_SADDLE, p_230266_1_, 0.5F, 1.0F);
      }

   }

   protected void registerGoals() {
      this.field_234307_bB_ = new PanicGoal(this, 1.65D);
      this.goalSelector.addGoal(1, this.field_234307_bB_);
      this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
      this.field_234306_bA_ = new TemptGoal(this, 1.4D, false, field_234309_bv_);
      this.goalSelector.addGoal(4, this.field_234306_bA_);
      this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
      this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D, 60));
      this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
      this.goalSelector.addGoal(9, new LookAtGoal(this, StriderEntity.class, 8.0F));
   }

   public void func_234319_t_(boolean p_234319_1_) {
      this.dataManager.set(field_234311_bx_, p_234319_1_);
   }

   public boolean func_234315_eI_() {
      return this.getRidingEntity() instanceof StriderEntity ? ((StriderEntity)this.getRidingEntity()).func_234315_eI_() : this.dataManager.get(field_234311_bx_);
   }

   public boolean func_230285_a_(Fluid p_230285_1_) {
      return p_230285_1_.isIn(FluidTags.LAVA);
   }

   /**
    * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
    * pushable on contact
    */
   @Nullable
   public AxisAlignedBB getCollisionBox(Entity entityIn) {
      return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
   }

   /**
    * Returns true if this entity should push and be pushed by other entities when colliding.
    */
   public boolean canBePushed() {
      return true;
   }

   /**
    * Returns the Y offset from the entity's position for any entity riding this one.
    */
   public double getMountedYOffset() {
      float f = Math.min(0.25F, this.limbSwingAmount);
      float f1 = this.limbSwing;
      return (double)this.getHeight() - 0.2D + (double)(0.12F * MathHelper.cos(f1 * 1.5F) * 2.0F * f);
   }

   /**
    * returns true if all the conditions for steering the entity are met. For pigs
    */
   public boolean canBeSteered() {
      Entity entity = this.getControllingPassenger();
      if (!(entity instanceof PlayerEntity)) {
         return false;
      } else {
         PlayerEntity playerentity = (PlayerEntity)entity;
         return playerentity.getHeldItemMainhand().getItem() == Items.WARPED_FUNGUS_ON_A_STICK || playerentity.getHeldItemOffhand().getItem() == Items.WARPED_FUNGUS_ON_A_STICK;
      }
   }

   public boolean isNotColliding(IWorldReader worldIn) {
      return worldIn.checkNoEntityCollision(this);
   }

   /**
    * For vehicles
    */
   @Nullable
   public Entity getControllingPassenger() {
      return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
   }

   public Vector3d func_230268_c_(LivingEntity p_230268_1_) {
      Vector3d[] avector3d = new Vector3d[]{func_233559_a_((double)this.getWidth(), (double)p_230268_1_.getWidth(), p_230268_1_.rotationYaw), func_233559_a_((double)this.getWidth(), (double)p_230268_1_.getWidth(), p_230268_1_.rotationYaw - 22.5F), func_233559_a_((double)this.getWidth(), (double)p_230268_1_.getWidth(), p_230268_1_.rotationYaw + 22.5F), func_233559_a_((double)this.getWidth(), (double)p_230268_1_.getWidth(), p_230268_1_.rotationYaw - 45.0F), func_233559_a_((double)this.getWidth(), (double)p_230268_1_.getWidth(), p_230268_1_.rotationYaw + 45.0F)};
      Set<BlockPos> set = Sets.newLinkedHashSet();
      double d0 = this.getBoundingBox().maxY;
      double d1 = this.getBoundingBox().minY - 0.5D;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Vector3d vector3d : avector3d) {
         blockpos$mutable.setPos(this.getPosX() + vector3d.x, d0, this.getPosZ() + vector3d.z);

         for(double d2 = d0; d2 > d1; --d2) {
            set.add(blockpos$mutable.toImmutable());
            blockpos$mutable.move(Direction.DOWN);
         }
      }

      for(BlockPos blockpos : set) {
         if (!this.world.getFluidState(blockpos).isTagged(FluidTags.LAVA)) {
            for(Pose pose : p_230268_1_.func_230297_ef_()) {
               double d3 = this.world.func_234936_m_(blockpos);
               if (TransportationHelper.func_234630_a_(d3)) {
                  AxisAlignedBB axisalignedbb = p_230268_1_.getPoseAABB(pose);
                  Vector3d vector3d1 = Vector3d.func_237490_a_(blockpos, d3);
                  if (TransportationHelper.func_234631_a_(this.world, p_230268_1_, axisalignedbb.offset(vector3d1))) {
                     p_230268_1_.setPose(pose);
                     return vector3d1;
                  }
               }
            }
         }
      }

      return new Vector3d(this.getPosX(), this.getBoundingBox().maxY, this.getPosZ());
   }

   public void travel(Vector3d p_213352_1_) {
      this.setAIMoveSpeed(this.func_234316_eJ_());
      this.func_233622_a_(this, this.field_234313_bz_, p_213352_1_);
   }

   public float func_234316_eJ_() {
      return (float)this.func_233637_b_(Attributes.MOVEMENT_SPEED) * (this.func_234315_eI_() ? 0.66F : 1.0F);
   }

   public float func_230265_N__() {
      return (float)this.func_233637_b_(Attributes.MOVEMENT_SPEED) * (this.func_234315_eI_() ? 0.23F : 0.55F);
   }

   public void func_230267_a__(Vector3d p_230267_1_) {
      super.travel(p_230267_1_);
   }

   protected float determineNextStepDistance() {
      return this.distanceWalkedOnStepModified + 0.6F;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(this.isInLava() ? SoundEvents.ENTITY_STRIDER_STEP_LAVA : SoundEvents.ENTITY_STRIDER_STEP, 1.0F, 1.0F);
   }

   public boolean boost() {
      return this.field_234313_bz_.func_233617_a_(this.getRNG());
   }

   protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
      this.doBlockCollisions();
      if (this.isInLava()) {
         this.fallDistance = 0.0F;
      } else {
         super.updateFallState(y, onGroundIn, state, pos);
      }
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      if (this.func_241398_eP_() && this.rand.nextInt(140) == 0) {
         this.playSound(SoundEvents.ENTITY_STRIDER_HAPPY, 1.0F, this.getSoundPitch());
      } else if (this.func_241397_eO_() && this.rand.nextInt(60) == 0) {
         this.playSound(SoundEvents.ENTITY_STRIDER_RETREAT, 1.0F, this.getSoundPitch());
      }

      BlockState blockstate = this.world.getBlockState(this.func_233580_cy_());
      BlockState blockstate1 = this.func_233568_aJ_();
      boolean flag = blockstate.isIn(BlockTags.STRIDER_WARM_BLOCKS) || blockstate1.isIn(BlockTags.STRIDER_WARM_BLOCKS) || this.func_233571_b_(FluidTags.LAVA) > 0.0D;
      this.func_234319_t_(!flag);
      super.tick();
      this.func_234318_eL_();
      this.doBlockCollisions();
   }

   private boolean func_241397_eO_() {
      return this.field_234307_bB_ != null && this.field_234307_bB_.func_234044_h_();
   }

   private boolean func_241398_eP_() {
      return this.field_234306_bA_ != null && this.field_234306_bA_.isRunning();
   }

   protected boolean func_230286_q_() {
      return true;
   }

   private void func_234318_eL_() {
      if (this.isInLava()) {
         ISelectionContext iselectioncontext = ISelectionContext.forEntity(this);
         if (iselectioncontext.func_216378_a(FlowingFluidBlock.field_235510_c_, this.func_233580_cy_(), true) && !this.world.getFluidState(this.func_233580_cy_().up()).isTagged(FluidTags.LAVA)) {
            this.onGround = true;
         } else {
            this.setMotion(this.getMotion().scale(0.5D).add(0.0D, 0.05D, 0.0D));
         }
      }

   }

   public static AttributeModifierMap.MutableAttribute func_234317_eK_() {
      return MobEntity.func_233666_p_().func_233815_a_(Attributes.MOVEMENT_SPEED, (double)0.175F).func_233815_a_(Attributes.FOLLOW_RANGE, 16.0D);
   }

   protected SoundEvent getAmbientSound() {
      return !this.func_241397_eO_() && !this.func_241398_eP_() ? SoundEvents.ENTITY_STRIDER_AMBIENT : null;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_STRIDER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_STRIDER_DEATH;
   }

   protected boolean canFitPassenger(Entity passenger) {
      return this.getPassengers().isEmpty() && !this.areEyesInFluid(FluidTags.LAVA);
   }

   public boolean isWaterSensitive() {
      return true;
   }

   /**
    * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
    */
   public boolean isBurning() {
      return false;
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      return new StriderEntity.LavaPathNavigator(this, worldIn);
   }

   public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
      return worldIn.getBlockState(pos).getFluidState().isTagged(FluidTags.LAVA) ? 10.0F : 0.0F;
   }

   public StriderEntity createChild(AgeableEntity ageable) {
      return EntityType.STRIDER.create(this.world);
   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat
    */
   public boolean isBreedingItem(ItemStack stack) {
      return field_234308_bu_.test(stack);
   }

   protected void dropInventory() {
      super.dropInventory();
      if (this.isHorseSaddled()) {
         this.entityDropItem(Items.SADDLE);
      }

   }

   public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
      boolean flag = this.isBreedingItem(p_230254_1_.getHeldItem(p_230254_2_));
      if (!flag && this.isHorseSaddled() && !this.isBeingRidden()) {
         if (!this.world.isRemote) {
            p_230254_1_.startRiding(this);
         }

         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else {
         ActionResultType actionresulttype = super.func_230254_b_(p_230254_1_, p_230254_2_);
         if (!actionresulttype.isSuccessOrConsume()) {
            ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
            return itemstack.getItem() == Items.SADDLE ? itemstack.interactWithEntity(p_230254_1_, this, p_230254_2_) : ActionResultType.PASS;
         } else {
            if (flag && !this.isSilent()) {
               this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_STRIDER_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            }

            return actionresulttype;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public Vector3d func_241205_ce_() {
      return new Vector3d(0.0D, (double)(0.6F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
      ILivingEntityData ilivingentitydata = null;
      StriderEntity.AgeData.RiderType striderentity$agedata$ridertype;
      if (spawnDataIn instanceof StriderEntity.AgeData) {
         striderentity$agedata$ridertype = ((StriderEntity.AgeData)spawnDataIn).field_234320_a_;
      } else if (!this.isChild()) {
         if (this.rand.nextInt(30) == 0) {
            striderentity$agedata$ridertype = StriderEntity.AgeData.RiderType.PIGLIN_RIDER;
            ilivingentitydata = new ZombieEntity.GroupData(ZombieEntity.func_241399_a_(this.rand), false);
         } else if (this.rand.nextInt(10) == 0) {
            striderentity$agedata$ridertype = StriderEntity.AgeData.RiderType.BABY_RIDER;
         } else {
            striderentity$agedata$ridertype = StriderEntity.AgeData.RiderType.NO_RIDER;
         }

         spawnDataIn = new StriderEntity.AgeData(striderentity$agedata$ridertype);
         ((AgeableEntity.AgeableData)spawnDataIn).func_226258_a_(striderentity$agedata$ridertype == StriderEntity.AgeData.RiderType.NO_RIDER ? 0.5F : 0.0F);
      } else {
         striderentity$agedata$ridertype = StriderEntity.AgeData.RiderType.NO_RIDER;
      }

      MobEntity mobentity = null;
      if (striderentity$agedata$ridertype == StriderEntity.AgeData.RiderType.BABY_RIDER) {
         StriderEntity striderentity = EntityType.STRIDER.create(worldIn.getWorld());
         if (striderentity != null) {
            mobentity = striderentity;
            striderentity.setGrowingAge(-24000);
         }
      } else if (striderentity$agedata$ridertype == StriderEntity.AgeData.RiderType.PIGLIN_RIDER) {
         ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.ZOMBIFIED_PIGLIN.create(worldIn.getWorld());
         if (zombifiedpiglinentity != null) {
            mobentity = zombifiedpiglinentity;
            this.func_230266_a_((SoundCategory)null);
         }
      }

      if (mobentity != null) {
         mobentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
         mobentity.onInitialSpawn(worldIn, difficultyIn, SpawnReason.JOCKEY, ilivingentitydata, (CompoundNBT)null);
         mobentity.startRiding(this, true);
         worldIn.addEntity(mobentity);
      }

      return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   public static class AgeData extends AgeableEntity.AgeableData {
      public final StriderEntity.AgeData.RiderType field_234320_a_;

      public AgeData(StriderEntity.AgeData.RiderType p_i231563_1_) {
         this.field_234320_a_ = p_i231563_1_;
      }

      public static enum RiderType {
         NO_RIDER,
         BABY_RIDER,
         PIGLIN_RIDER;
      }
   }

   static class LavaPathNavigator extends GroundPathNavigator {
      LavaPathNavigator(StriderEntity p_i231565_1_, World p_i231565_2_) {
         super(p_i231565_1_, p_i231565_2_);
      }

      protected PathFinder getPathFinder(int p_179679_1_) {
         this.nodeProcessor = new WalkNodeProcessor();
         return new PathFinder(this.nodeProcessor, p_179679_1_);
      }

      protected boolean func_230287_a_(PathNodeType p_230287_1_) {
         return p_230287_1_ != PathNodeType.LAVA && p_230287_1_ != PathNodeType.DAMAGE_FIRE && p_230287_1_ != PathNodeType.DANGER_FIRE ? super.func_230287_a_(p_230287_1_) : true;
      }

      public boolean canEntityStandOnPos(BlockPos pos) {
         return this.world.getBlockState(pos).isIn(Blocks.LAVA) || super.canEntityStandOnPos(pos);
      }
   }
}