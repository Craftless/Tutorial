package com.craftless.tutorial.blocks;

import com.craftless.tutorial.entities.FiveTimesTNTEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class FiveTimesTNTBlock extends TNTBlock
{

	public FiveTimesTNTBlock(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void catchFire(BlockState state, World world, BlockPos pos, Direction face, LivingEntity igniter) {
		if (!world.isRemote) {
	         FiveTimesTNTEntity tntentity = new FiveTimesTNTEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
	         world.addEntity(tntentity);
	         world.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
	      }
	}
	
	@Override
	public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
		if (!world.isRemote) {
	         FiveTimesTNTEntity tntentity = new FiveTimesTNTEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, explosion.getExplosivePlacedBy());
	         tntentity.setFuse((short)(world.rand.nextInt(tntentity.getFuse() / 4) + tntentity.getFuse() / 8));
	         world.addEntity(tntentity);
	      }
	}

}
