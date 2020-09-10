package com.craftless.tutorial.entities;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.init.ModSounds;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HogEntity extends AnimalEntity
{

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT, ModItems.RUBY.get().asItem());
	
	private EatGrassGoal eatGrassGoal;
	private int hogTimer;
	
	public HogEntity(EntityType<? extends AnimalEntity> type, World worldIn) 
	{
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes()
	{
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 12.8D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override 
	protected void registerGoals()
	{
		super.registerGoals();
		this.eatGrassGoal = new EatGrassGoal(this);
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, this.eatGrassGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

	}
	
	@Override
	protected int getExperiencePoints(PlayerEntity player) { return 1 + this.world.rand.nextInt(4); }
	
	@Override
	protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }
	
	@Override
	protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIG_DEATH; }
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_PIG_HURT; }
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f); }
	
	@Override
	public AgeableEntity createChild(AgeableEntity arg0) { return ModEntityTypes.HOG.get().create(this.world); }

	@Override
	protected void updateAITasks() 
	{
		this.hogTimer = this.eatGrassGoal.getEatingGrassTimer();
		super.updateAITasks();
	}

	@Override
	public void livingTick() 
	{
		if (this.world.isRemote)
		{
			this.hogTimer = Math.max(0,  this.hogTimer - 1);
		}
		super.livingTick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 10)
		{
			this.hogTimer = 40;
		}
		else
		{
			super.handleStatusUpdate(id);
		}
	}
	
	@Override
	public void playAmbientSound() {
		SoundEvent soundevent = ModSounds.AMBIENT.get();
		if (soundevent != null) {
		   this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
		}
	}
	
}
