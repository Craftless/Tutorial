package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;

import net.minecraft.item.Item;

public class ItemBase extends Item
{

	public ItemBase() 
	{
		super(new Item.Properties().group(Tutorial.TAB));
	}
	
	public ItemBase(Properties properties)
	{
		super(properties.group(Tutorial.TAB));
	}
	
}
