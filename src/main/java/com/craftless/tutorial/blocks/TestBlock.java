package com.craftless.tutorial.blocks;

import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class TestBlock extends Block
{
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	private static final VoxelShape SHAPE_N = Stream.of(
			Block.makeCuboidShape(12, 0, 1, 15, 10, 2), 
			Block.makeCuboidShape(1, 0, 1, 4, 10, 2),
			Block.makeCuboidShape(12, 0, 14, 15, 10, 15),
			Block.makeCuboidShape(1, 0, 14, 4, 10, 15),
			Block.makeCuboidShape(14, 0, 2, 15, 10, 14),
			Block.makeCuboidShape(1, 0, 2, 2, 10, 14),
			Block.makeCuboidShape(4, 8, 1, 12, 10, 2),
			Block.makeCuboidShape(9, 7, 1, 12, 8, 2),
			Block.makeCuboidShape(4, 7, 1, 7, 8, 2),
			Block.makeCuboidShape(10, 6, 1, 12, 7, 2),
			Block.makeCuboidShape(4, 6, 1, 6, 7, 2),
			Block.makeCuboidShape(11, 5, 1, 12, 6, 2),
			Block.makeCuboidShape(4, 5, 1, 5, 6, 2),
			Block.makeCuboidShape(4, 8, 14, 12, 10, 15),
			Block.makeCuboidShape(9, 7, 14, 12, 8, 15),
			Block.makeCuboidShape(4, 7, 14, 7, 8, 15),
			Block.makeCuboidShape(4, 6, 14, 6, 7, 15),
			Block.makeCuboidShape(10, 6, 14, 12, 7, 15),
			Block.makeCuboidShape(11, 5, 14, 12, 6, 15),
			Block.makeCuboidShape(4, 5, 14, 5, 6, 15),
			Block.makeCuboidShape(2, 9, 2, 14, 10, 14),
			Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
			Block.makeCuboidShape(5, 0, 5, 10, 1, 10),
			Block.makeCuboidShape(6, 1, 7, 9, 2, 9),
			Block.makeCuboidShape(5, 1, 6, 7, 4, 7),
			Block.makeCuboidShape(7, 1, 6, 8, 5, 7),
			Block.makeCuboidShape(8, 1, 6, 9, 3, 7),
			Block.makeCuboidShape(2, 10, 2, 14, 11, 13),
			Block.makeCuboidShape(1, 10, 5, 2, 16, 10),
			Block.makeCuboidShape(-1, 15, 5, 1, 16, 10)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_E = Stream.of(
			Block.makeCuboidShape(14, 0, 12, 15, 10, 15),
			Block.makeCuboidShape(14, 0, 1, 15, 10, 4),
			Block.makeCuboidShape(1, 0, 12, 2, 10, 15),
			Block.makeCuboidShape(1, 0, 1, 2, 10, 4),
			Block.makeCuboidShape(2, 0, 14, 14, 10, 15),
			Block.makeCuboidShape(2, 0, 1, 14, 10, 2),
			Block.makeCuboidShape(14, 8, 4, 15, 10, 12),
			Block.makeCuboidShape(14, 7, 9, 15, 8, 12),
			Block.makeCuboidShape(14, 7, 4, 15, 8, 7),
			Block.makeCuboidShape(14, 6, 10, 15, 7, 12),
			Block.makeCuboidShape(14, 6, 4, 15, 7, 6),
			Block.makeCuboidShape(14, 5, 11, 15, 6, 12),
			Block.makeCuboidShape(14, 5, 4, 15, 6, 5),
			Block.makeCuboidShape(1, 8, 4, 2, 10, 12),
			Block.makeCuboidShape(1, 7, 9, 2, 8, 12),
			Block.makeCuboidShape(1, 7, 4, 2, 8, 7),
			Block.makeCuboidShape(1, 6, 4, 2, 7, 6),
			Block.makeCuboidShape(1, 6, 10, 2, 7, 12),
			Block.makeCuboidShape(1, 5, 11, 2, 6, 12),
			Block.makeCuboidShape(1, 5, 4, 2, 6, 5),
			Block.makeCuboidShape(2, 9, 2, 14, 10, 14),
			Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
			Block.makeCuboidShape(6, 0, 5, 11, 1, 10),
			Block.makeCuboidShape(7, 1, 6, 9, 2, 9),
			Block.makeCuboidShape(9, 1, 5, 10, 4, 7),
			Block.makeCuboidShape(9, 1, 7, 10, 5, 8),
			Block.makeCuboidShape(9, 1, 8, 10, 3, 9),
			Block.makeCuboidShape(3, 10, 2, 14, 11, 14),
			Block.makeCuboidShape(6, 10, 1, 11, 16, 2),
			Block.makeCuboidShape(6, 15, -1, 11, 16, 1)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_S = Stream.of(
			Block.makeCuboidShape(1, 0, 14, 4, 10, 15),
			Block.makeCuboidShape(12, 0, 14, 15, 10, 15),
			Block.makeCuboidShape(1, 0, 1, 4, 10, 2),
			Block.makeCuboidShape(12, 0, 1, 15, 10, 2),
			Block.makeCuboidShape(1, 0, 2, 2, 10, 14),
			Block.makeCuboidShape(14, 0, 2, 15, 10, 14),
			Block.makeCuboidShape(4, 8, 14, 12, 10, 15),
			Block.makeCuboidShape(4, 7, 14, 7, 8, 15),
			Block.makeCuboidShape(9, 7, 14, 12, 8, 15),
			Block.makeCuboidShape(4, 6, 14, 6, 7, 15),
			Block.makeCuboidShape(10, 6, 14, 12, 7, 15),
			Block.makeCuboidShape(4, 5, 14, 5, 6, 15),
			Block.makeCuboidShape(11, 5, 14, 12, 6, 15),
			Block.makeCuboidShape(4, 8, 1, 12, 10, 2),
			Block.makeCuboidShape(4, 7, 1, 7, 8, 2),
			Block.makeCuboidShape(9, 7, 1, 12, 8, 2),
			Block.makeCuboidShape(10, 6, 1, 12, 7, 2),
			Block.makeCuboidShape(4, 6, 1, 6, 7, 2),
			Block.makeCuboidShape(4, 5, 1, 5, 6, 2),
			Block.makeCuboidShape(11, 5, 1, 12, 6, 2),
			Block.makeCuboidShape(2, 9, 2, 14, 10, 14),
			Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
			Block.makeCuboidShape(6, 0, 6, 11, 1, 11),
			Block.makeCuboidShape(7, 1, 7, 10, 2, 9),
			Block.makeCuboidShape(9, 1, 9, 11, 4, 10),
			Block.makeCuboidShape(8, 1, 9, 9, 5, 10),
			Block.makeCuboidShape(7, 1, 9, 8, 3, 10),
			Block.makeCuboidShape(2, 10, 3, 14, 11, 14),
			Block.makeCuboidShape(14, 10, 6, 15, 16, 11),
			Block.makeCuboidShape(15, 15, 6, 17, 16, 11)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	private static final VoxelShape SHAPE_W = Stream.of(
			Block.makeCuboidShape(1, 0, 1, 2, 10, 4),
			Block.makeCuboidShape(1, 0, 12, 2, 10, 15),
			Block.makeCuboidShape(14, 0, 1, 15, 10, 4),
			Block.makeCuboidShape(14, 0, 12, 15, 10, 15),
			Block.makeCuboidShape(2, 0, 1, 14, 10, 2),
			Block.makeCuboidShape(2, 0, 14, 14, 10, 15),
			Block.makeCuboidShape(1, 8, 4, 2, 10, 12),
			Block.makeCuboidShape(1, 7, 4, 2, 8, 7),
			Block.makeCuboidShape(1, 7, 9, 2, 8, 12),
			Block.makeCuboidShape(1, 6, 4, 2, 7, 6),
			Block.makeCuboidShape(1, 6, 10, 2, 7, 12),
			Block.makeCuboidShape(1, 5, 4, 2, 6, 5),
			Block.makeCuboidShape(1, 5, 11, 2, 6, 12),
			Block.makeCuboidShape(14, 8, 4, 15, 10, 12),
			Block.makeCuboidShape(14, 7, 4, 15, 8, 7),
			Block.makeCuboidShape(14, 7, 9, 15, 8, 12),
			Block.makeCuboidShape(14, 6, 10, 15, 7, 12),
			Block.makeCuboidShape(14, 6, 4, 15, 7, 6),
			Block.makeCuboidShape(14, 5, 4, 15, 6, 5),
			Block.makeCuboidShape(14, 5, 11, 15, 6, 12),
			Block.makeCuboidShape(2, 9, 2, 14, 10, 14),
			Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
			Block.makeCuboidShape(5, 0, 6, 10, 1, 11),
			Block.makeCuboidShape(7, 1, 7, 9, 2, 10),
			Block.makeCuboidShape(6, 1, 9, 7, 4, 11),
			Block.makeCuboidShape(6, 1, 8, 7, 5, 9),
			Block.makeCuboidShape(6, 1, 7, 7, 3, 8),
			Block.makeCuboidShape(2, 10, 2, 13, 11, 14),
			Block.makeCuboidShape(5, 10, 14, 10, 16, 15),
			Block.makeCuboidShape(5, 15, 15, 10, 16, 17)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


	
	public TestBlock() 
	{
		super(AbstractBlock.Properties.create(Material.IRON)
				.hardnessAndResistance(5.0f, 6.0f)
				.sound(SoundType.ANVIL)
				.harvestLevel(1)
				.harvestTool(ToolType.PICKAXE)
				.setRequiresTool());
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING,  rot.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
	
	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch(state.get(FACING))
		{
		case NORTH:
			return SHAPE_N;
		case EAST:
			return SHAPE_E;
		case SOUTH:
			return SHAPE_S;
		case WEST:
			return SHAPE_W;
		default:
			return SHAPE_N;
		}
	}
	
	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return 0.6f;
	}
	
	
}
