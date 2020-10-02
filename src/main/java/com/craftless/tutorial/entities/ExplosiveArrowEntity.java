package com.craftless.tutorial.entities;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class ExplosiveArrowEntity extends AbstractArrowEntity
{

	
	
	public ExplosiveArrowEntity(EntityType<? extends ExplosiveArrowEntity> type, World worldIn) {
		super(type, worldIn);
		this.setDamage(this.getDamage());
	}

	public ExplosiveArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.EXPLOSIVE_ARROW.get(), x, y, z, worldIn);
	}

	public ExplosiveArrowEntity(World worldIn, LivingEntity shooter) {
	    super(ModEntityTypes.EXPLOSIVE_ARROW.get(), shooter, worldIn);
	}
	
	
	@Override
	protected void onImpact(RayTraceResult result) {
		this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), 4, false, Mode.BREAK);
		super.onImpact(result);
		this.remove();
	}

	@Override
	protected ItemStack getArrowStack() {
		return ModItems.EXPLOSIVE_ARROW_ITEM.get().getDefaultInstance();
	}

}
