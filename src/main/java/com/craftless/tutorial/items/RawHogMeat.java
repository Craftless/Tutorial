package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.entities.HogEntity;
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

public class RawHogMeat extends Item
{

	public RawHogMeat() 
	{
		super(new Item.Properties()
				.group(Tutorial.TAB)
				.food(new Food.Builder()
						.meat()
						.hunger(2)
						.saturation(1)
						.setAlwaysEdible()
						.effect(() -> new EffectInstance(Effects.STRENGTH, 300, 0), 0.1f)
						.build())
				
			);
	}
	
	@SubscribeEvent
	public void onEat(LivingEntityUseItemEvent.Finish e)
	{
		if (e.getItem().equals(ModItems.RAW_HOG_MEAT.get().getDefaultInstance()))
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			String msg = TextFormatting.RED + "You became a hog! You are a cannibal";
			player.sendMessage(new StringTextComponent(msg), player.getUniqueID());
			
			World world = player.getEntityWorld();
			if (!world.isRemote)
			{
				world.addEntity(ModEntityTypes.HOG.get().create(world));
			}
		}
	}

}
