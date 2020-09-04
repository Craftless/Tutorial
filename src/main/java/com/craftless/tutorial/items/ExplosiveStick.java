package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveStick extends Item
{

	public ExplosiveStick() 
	{
		super(new Item.Properties()
				.group(Tutorial.TAB)
				.rarity(Rarity.RARE)
		);
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		Tutorial.LOGGER.info("Player right clicked with explosive stick");
		World world = playerIn.getEntityWorld();
		if (!world.isRemote)
		{
			playerIn.getEntityWorld().createExplosion(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), 3, Explosion.Mode.BREAK);
		}		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) 
	{
		entity.setGlowing(true);
		return super.onEntityItemUpdate(stack, entity);
	}
	
}
