package com.craftless.tutorial.fluids;

import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModFluids;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class MilkFluid extends FlowingFluid
{

	@Override
	public Fluid getFlowingFluid() {
		return ModFluids.MILK_FLOWING.get();
	}

	@Override
	public Fluid getStillFluid() {
		return ModFluids.MILK_FLUID.get();
	}

	@Override
	protected boolean canSourcesMultiply() {
		return true;
	}

	@Override
	protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
		
	}

	@Override
	protected int getSlopeFindDistance(IWorldReader worldIn) {
		return 4;
	}

	@Override
	protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
		return 1;
	}

	@Override
	public Item getFilledBucket() {
		return null;
	}

	@Override
	protected boolean canDisplace(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid,
			Direction direction) {
		return fluidState.getActualHeight(blockReader, pos) >= 0.44444445F && fluid.isIn(FluidTags.WATER);
	}

	@Override
	public int getTickRate(IWorldReader p_205569_1_) {
		return 5;
	}

	@Override
	protected float getExplosionResistance() {
		return 100.0F;
	}

	@Override
	protected BlockState getBlockState(FluidState state) {
		return ModBlocks.RUBY_BLOCK.get().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
	}

	@Override
	public boolean isSource(FluidState state) {
		return false;
	}

	@Override
	public int getLevel(FluidState p_207192_1_) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class Flowing extends MilkFluid
	{

		@Override
		protected void fillStateContainer(Builder<Fluid, FluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}
		
		@Override
		public int getLevel(FluidState p_207192_1_) {
			return p_207192_1_.get(LEVEL_1_8);
		}
		
		@Override
		public boolean isSource(FluidState state) {
			return false;
		}
	}
	
	public static class Source extends MilkFluid
	{
		@Override
		public int getLevel(FluidState p_207192_1_) {
			return 8;
		}
		
		@Override
		public boolean isSource(FluidState state) {
			return true;
		}
	}

}
