package com.craftless.tutorial.entities;

import java.util.Random;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class LightningStormArrowEntity extends AbstractArrowEntity
{

	private Random random;
	
	public LightningStormArrowEntity(EntityType<? extends LightningStormArrowEntity> type, World worldIn) {
		super(type, worldIn);
		this.setDamage(this.getDamage());
		random = new Random();
	}

	public LightningStormArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.LIGHTNING_STORM_ARROW.get(), x, y, z, worldIn);
		random = new Random();
	}

	public LightningStormArrowEntity(World worldIn, LivingEntity shooter) {
	    super(ModEntityTypes.LIGHTNING_STORM_ARROW.get(), shooter, worldIn);
	    random = new Random();
	}
	
	
	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		for (int x = -5; x < 5; x++)
		{
			for (int y = -5; y < 5; y++)
			{
				LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, this.world);
				lightning.setPosition(this.getPosX() + x + this.random.nextDouble(), this.getPosY(), this.getPosZ() + y + this.random.nextDouble());
				this.world.addEntity(lightning);
			}
		}
		this.remove();
	}
	
	@Override
	protected ItemStack getArrowStack() {
		return ModItems.LIGHTNING_STORM_ARROW_ITEM.get().getDefaultInstance();
	}

}
