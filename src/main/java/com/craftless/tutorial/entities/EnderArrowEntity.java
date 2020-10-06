package com.craftless.tutorial.entities;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class EnderArrowEntity extends AbstractArrowEntity
{
	LivingEntity ent;
	public EnderArrowEntity(EntityType<? extends EnderArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public EnderArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.ENDER_ARROW.get(), x, y, z, worldIn);
	}

	public EnderArrowEntity(World worldIn, LivingEntity shooter) {
	    super(ModEntityTypes.ENDER_ARROW.get(), shooter, worldIn);
	    this.ent = shooter;
	}
	
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.getType().equals(RayTraceResult.Type.BLOCK))
		{
			if (this.ent != null)
			{
//				this.ent.attemptTeleport(this.getPosX(), this.getPosY(), this.getPosZ(), true);
				this.ent.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());

			}
		}
		
		
		super.onImpact(result);
		this.remove();
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult e) {
		Entity rEnt = e.getEntity();
		if (rEnt.isLiving())
		{
			if (this.ent != null)
			{
				LivingEntity lEnt = (LivingEntity) rEnt;
				BlockPos shooterPos = this.ent.getPosition();
//				this.ent.attemptTeleport(this.getPosX(), this.getPosY(), this.getPosZ(), false);
				this.ent.setPosition(lEnt.getPosX(), lEnt.getPosY(), lEnt.getPosZ());
//				lEnt.attemptTeleport(shooterPos.getX(), shooterPos.getY(), shooterPos.getZ(), false);
				lEnt.setPosition(shooterPos.getX(), shooterPos.getY(), shooterPos.getZ());
			}
		}
		super.onEntityHit(e);
	}
	
	

	@Override
	protected ItemStack getArrowStack() {
		return ModItems.ENDER_ARROW_ITEM.get().getDefaultInstance();
	}
}
