package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.entities.BulletEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Gun extends Item
{

	public Gun() 
	{
		super(new Item.Properties().group(Tutorial.TAB).maxStackSize(1));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		if (!worldIn.isRemote())
		{
			BulletEntity bullet = new BulletEntity(worldIn, playerIn, playerIn.getLookVec().getX() * playerIn.getLookVec().getX() , playerIn.getLookVec().getY() * playerIn.getLookVec().getY(), playerIn.getLookVec().getZ() * playerIn.getLookVec().getZ());
			bullet.setPosition(playerIn.getPosX(), playerIn.getPosYEye(), playerIn.getPosZ());
			worldIn.addEntity(bullet);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
