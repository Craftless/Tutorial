package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CookedHogMeat extends Item
{

	public CookedHogMeat() 
	{
		super(new Item.Properties()
				.group(Tutorial.TAB)
				.food(new Food.Builder()
						.meat()
						.hunger(8)
						.saturation(6)
						.setAlwaysEdible()
						.effect(() -> new EffectInstance(Effects.STRENGTH, 12000, 1), 1f)
						.build())
				
			);
	}
	

}
