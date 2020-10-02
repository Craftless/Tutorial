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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BulletEntity extends DamagingProjectileEntity
{

	private Entity bulletShooter;
	public BulletEntity(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World worldIn) {
		super(ModEntityTypes.BULLET.get(), worldIn);

	}
	
	@OnlyIn(Dist.CLIENT)
	   public BulletEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
	      super(ModEntityTypes.BULLET.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	   }

	   public BulletEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
	      super(ModEntityTypes.BULLET.get(), shooter, accelX, accelY, accelZ, worldIn);
	      this.bulletShooter = shooter;
	   }

   	@Override
   	public float getBrightness() {
   		return 15.0F;
   	}
   	
   	@Override
   	protected void onEntityHit(EntityRayTraceResult p_213868_1_) 
   	{
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
        	
        	CompoundNBT tag = new CompoundNBT();
        	ItemStack fireworkIs = new ItemStack(Items.FIREWORK_ROCKET);
        	fireworkIs.deserializeNBT(tag);
        	FireworkRocketEntity firework = new FireworkRocketEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), ItemStack.EMPTY);
        	world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), 2, false, Explosion.Mode.NONE);
            world.addEntity(firework);
            if (this.bulletShooter != null) {
            	if (firework.getTags()!= null)
            		this.bulletShooter.sendMessage(new StringTextComponent("FIREWORKENTITY" + firework.getTags()), this.bulletShooter.getUniqueID());
            }
            Tutorial.LOGGER.debug(firework.getTags());
            this.remove();
        }

     }
   	
	

}
