package com.craftless.tutorial.client;

import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.items.JarItem;

import net.minecraftforge.client.event.ColorHandlerEvent;

public class ColorHandler 
{
	public static void registerItemColor(ColorHandlerEvent.Item e)
	{
		e.getItemColors().register(JarItem::getItemColor, ModItems.JAR.get());
	}
}
