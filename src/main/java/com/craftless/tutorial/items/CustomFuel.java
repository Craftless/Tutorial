package com.craftless.tutorial.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomFuel extends Item
{
	
	private int burnTime;
	
	public CustomFuel(Properties properties, int burnTime) 
	{
		super(properties);
		this.burnTime = burnTime;
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack) 
	{
			return burnTime;
	}
	
}
