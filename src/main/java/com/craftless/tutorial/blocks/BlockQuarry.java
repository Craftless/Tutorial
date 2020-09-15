package com.craftless.tutorial.blocks;

import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.tileentities.QuarryTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

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
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.QUARRY.get().create();
	}

}
