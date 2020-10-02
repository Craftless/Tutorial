package com.craftless.tutorial.entities;

import java.util.Random;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class TNTArrowEntity extends AbstractArrowEntity
{
	
	public TNTArrowEntity(EntityType<? extends TNTArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public TNTArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.TNT_ARROW.get(), x, y, z, worldIn);
	}

	public TNTArrowEntity(World worldIn, LivingEntity shooter) {
	    super(ModEntityTypes.TNT_ARROW.get(), shooter, worldIn);
	}
	
	
	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		TNTEntity tnt = new TNTEntity(EntityType.TNT, this.world);
		tnt.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
		tnt.setFuse(20 * 2);
		this.world.addEntity(tnt);
		this.remove();
	}

	@Override
	protected ItemStack getArrowStack() {
		return ModItems.TNT_ARROW_ITEM.get().getDefaultInstance();
	}

}
