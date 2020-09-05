package com.craftless.tutorial.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuarry extends Block
{

	public BlockQuarry() 
	{
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(2, 8));
	}

}
