package com.craftless.tutorial.blocks;

import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.tileentities.ItemPedestalTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemPedestalBlock extends Block
{

	public ItemPedestalBlock(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return ModTileEntityTypes.ITEM_PEDESTAL.get().create();
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote)
		{
			final TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof ItemPedestalTileEntity)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, (ItemPedestalTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof ItemPedestalTileEntity)
			{
				InventoryHelper.dropItems(worldIn, pos, ((ItemPedestalTileEntity)tile).getItems());
			}
		}
	}

}
