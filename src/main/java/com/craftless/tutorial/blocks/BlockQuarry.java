package com.craftless.tutorial.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockQuarry extends Block
{

	public BlockQuarry() 
	{
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(2, 8)
				.sound(SoundType.METAL));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}

}
