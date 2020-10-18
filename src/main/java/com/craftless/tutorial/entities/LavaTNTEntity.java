package com.craftless.tutorial.entities;

import javax.annotation.Nullable;

import com.craftless.tutorial.init.ModEntityTypes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class LavaTNTEntity extends TNTEntity
{

	@Nullable
    private LivingEntity tntPlacedBy;
	
	public LavaTNTEntity(EntityType<? extends TNTEntity> type, World worldIn) {
		super(type, worldIn);
		// TODO Auto-generated constructor stub
	}

	public LavaTNTEntity(World worldIn, double x, double y, double z, LivingEntity igniter) {
		this(ModEntityTypes.FIVE_TIMES_TNT_ENTITY.get(), worldIn);
	      this.setPosition(x, y, z);
	      double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
	      this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
	      this.setFuse(20);
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
		
		for (int x = -5; x <= 5; x++)
		{
			for (int y = -5; y <= 5; y++)
			{
				for (int z = -5; z <= 5; z++)
				{
					BlockPos bp = new BlockPos(this.getPosX() + x, this.getPosY() + y, this.getPosZ() + z);
					world.setBlockState(bp, Blocks.LAVA.getDefaultState());
					
					LightningBoltEntity lb = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, this.world);
					lb.setPosition(this.getPosXRandom(10), this.getPosYRandom(), this.getPosZRandom(5));
					world.addEntity(lb);
				}
			}
		}
	}

}
