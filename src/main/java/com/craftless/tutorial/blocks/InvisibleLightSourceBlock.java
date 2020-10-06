package com.craftless.tutorial.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InvisibleLightSourceBlock extends Block
{

	   public InvisibleLightSourceBlock(Properties properties) {
		super(properties);
	}

	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
	      return true;
	   }

	   /**
	    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
	    */
	   @Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
	   
	   @Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 15;
	}

	   @OnlyIn(Dist.CLIENT)
	   public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
	      return 1.0F;
	   }
}
