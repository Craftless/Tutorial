package com.craftless.tutorial.entities;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEntityTypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExplosiveCactusEntity extends  DamagingProjectileEntity
{
	public ExplosiveCactusEntity(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World worldIn) {
		super(ModEntityTypes.EXPLOSIVE_CACTUS.get(), worldIn);

	}
	
	@OnlyIn(Dist.CLIENT)
	public ExplosiveCactusEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ModEntityTypes.EXPLOSIVE_CACTUS.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public ExplosiveCactusEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(ModEntityTypes.EXPLOSIVE_CACTUS.get(), shooter, accelX, accelY, accelZ, worldIn);
	}
	
	@Override
	public float getBrightness() {
		return 15;
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult p_213868_1_) {

   		super.onEntityHit(p_213868_1_);
   		if (!this.world.isRemote) {
            Entity entity = p_213868_1_.getEntity();
            Entity entity1 = this.func_234616_v_();
            entity.attackEntityFrom(DamageSource.GENERIC, 9.0F);
            if (entity1 instanceof LivingEntity) {
               this.applyEnchantments((LivingEntity)entity1, entity);
            }
            
         }
   		
	}
	
	protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.func_234616_v_());

        	this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 4, false, flag ? Mode.BREAK : Mode.NONE);
            this.remove();
        }

     }
	
	
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
	}
	
	public static float Cactus() {
	      float f = (float)2 / 20.0F;
	      f = (f * f + f * 2.0F) / 3.0F;
	      if (f > 1.0F) 
	      {
	         f = 1.0F;
	      }

	      return f;
   }
}
