package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.InvisibleLightSourceBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class InvisibleLightSourceRemover extends Item
{

	public InvisibleLightSourceRemover() {
		super(new Item.Properties().group(Tutorial.TAB).maxStackSize(1));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		PlayerEntity player = playerIn;
		BlockPos bp = player.getPosition();
		BlockState bs = worldIn.getBlockState(bp);
		for (int x = -10; x < 10; x++)
		{
			for (int y = -10; y < 10; y++)
			{
				for (int z = -10; z < 10; z++)
				{
					bp = new BlockPos(new Vector3d(bp.getX() + x, bp.getY() + y, bp.getZ() + z));
					bs = worldIn.getBlockState(bp);
					
					if (bs.getBlock() instanceof InvisibleLightSourceBlock)
					{
						worldIn.setBlockState(bp, Blocks.AIR.getDefaultState());
					}
				}
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
