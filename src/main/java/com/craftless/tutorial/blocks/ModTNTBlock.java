package com.craftless.tutorial.blocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.craftless.tutorial.entities.FiveTimesTNTEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ModTNTBlock extends TNTBlock
{
	
	Class<? extends TNTEntity> entityClass;

	public ModTNTBlock(Properties properties, Class<? extends TNTEntity> inputClass) {
		super(properties);
		this.entityClass = inputClass;
	}
	
	@Override
	public void catchFire(BlockState state, World world, BlockPos pos, Direction face, LivingEntity igniter) {
		if (!world.isRemote) {
			try {
			    final Constructor<? extends TNTEntity> ctor = this.entityClass.getDeclaredConstructor(World.class, double.class, double.class, double.class, LivingEntity.class);
			    ctor.setAccessible(true);
			    final TNTEntity tntentity = (TNTEntity)ctor.newInstance(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
			    world.addEntity(tntentity);
		         world.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			catch (NoSuchMethodException e) {
			    e.printStackTrace();
			}
			catch (SecurityException e2) {
			    e2.printStackTrace();
			}
			catch (InstantiationException e3) {
			    e3.printStackTrace();
			}
			catch (IllegalAccessException e4) {
			    e4.printStackTrace();
			}
			catch (IllegalArgumentException e5) {
			    e5.printStackTrace();
			}
			catch (InvocationTargetException e6) {
			    e6.printStackTrace();
			}
//	         FiveTimesTNTEntity tntentity = new FiveTimesTNTEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
	        
	      }
	}
	
	@Override
	public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
		if (!world.isRemote) {
			try {
			    final Constructor<? extends TNTEntity> ctor = this.entityClass.getDeclaredConstructor(World.class, double.class, double.class, double.class, LivingEntity.class);
			    ctor.setAccessible(true);
			    final TNTEntity tntentity = (TNTEntity)ctor.newInstance(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, explosion.getExplosivePlacedBy());
			    world.addEntity(tntentity);
		         world.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			catch (NoSuchMethodException e) {
			    e.printStackTrace();
			}
			catch (SecurityException e2) {
			    e2.printStackTrace();
			}
			catch (InstantiationException e3) {
			    e3.printStackTrace();
			}
			catch (IllegalAccessException e4) {
			    e4.printStackTrace();
			}
			catch (IllegalArgumentException e5) {
			    e5.printStackTrace();
			}
			catch (InvocationTargetException e6) {
			    e6.printStackTrace();
			}
	      }
	}

}
