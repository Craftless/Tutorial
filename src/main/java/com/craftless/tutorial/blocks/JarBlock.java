package com.craftless.tutorial.blocks;

import com.craftless.tutorial.init.ModTileEntityTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class JarBlock extends Block
{

	public JarBlock() {
		super(Block.Properties.from(Blocks.COBBLESTONE));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.JAR_BLOCK.get().create();
	}

}
