package com.craftless.tutorial.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.world.World;

public class BridgeEggEntity extends EggEntity
{

	public BridgeEggEntity(World worldIn, LivingEntity throwerIn) {
		super(worldIn, throwerIn);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		world.setBlockState(this.getPosition().down(), Blocks.WHITE_WOOL.getDefaultState());
	}
	
	@Override
	public void baseTick() {
		super.baseTick();
		world.setBlockState(this.getPosition().down(), Blocks.BLACK_WOOL.getDefaultState());
	}

}
