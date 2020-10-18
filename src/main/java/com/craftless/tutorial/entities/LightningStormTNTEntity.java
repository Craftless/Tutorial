package com.craftless.tutorial.entities;

import javax.annotation.Nullable;

import com.craftless.tutorial.init.ModEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class LightningStormTNTEntity extends TNTEntity
{
	@Nullable
    private LivingEntity tntPlacedBy;
	
	public LightningStormTNTEntity(EntityType<? extends TNTEntity> type, World worldIn) {
		super(type, worldIn);
		// TODO Auto-generated constructor stub
	}

	public LightningStormTNTEntity(World worldIn, double x, double y, double z, LivingEntity igniter) {
		this(ModEntityTypes.FIVE_TIMES_TNT_ENTITY.get(), worldIn);
	      this.setPosition(x, y, z);
	      double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
	      this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
	      this.setFuse(40);
	      this.prevPosX = x;
	      this.prevPosY = y;
	      this.prevPosZ = z;
	      this.tntPlacedBy = igniter;
	}

	@Override
	public LivingEntity getTntPlacedBy() {
		return this.tntPlacedBy;
	}
	
	@Override
	protected void explode() {
		float f = 4.0F;
		this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 4.0F, Explosion.Mode.BREAK);
		LightningStormArrowEntity ls = new LightningStormArrowEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
		world.addEntity(ls);
	}
}
