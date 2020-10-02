package com.craftless.tutorial.entities;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class LightningBoltArrowEntity extends AbstractArrowEntity
{

	public LightningBoltArrowEntity(EntityType<? extends LightningBoltArrowEntity> type, World worldIn) {
		super(type, worldIn);
		this.setDamage(this.getDamage());
	}

	public LightningBoltArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.LIGHTNING_BOLT_ARROW.get(), x, y, z, worldIn);
	}

	public LightningBoltArrowEntity(World worldIn, LivingEntity shooter) {
	    super(ModEntityTypes.LIGHTNING_BOLT_ARROW.get(), shooter, worldIn);
	}
	
	
	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, this.world);
		lightning.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
		this.world.addEntity(lightning);
		this.remove();
	}

	@Override
	protected ItemStack getArrowStack() {
		return ModItems.LIGHTNING_BOLT_ARROW_ITEM.get().getDefaultInstance();
	}
	
}
