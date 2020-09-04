package com.craftless.tutorial.events;

import com.craftless.tutorial.Tutorial;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents 
{
	@SubscribeEvent
	public static void onJump(LivingJumpEvent e)
	{
		if (e.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			if (player.getHeldItemMainhand().getItem() == Items.STICK)
			{
				Tutorial.LOGGER.info("Player tried to jump with stick");
				
			}
		}
	}
}
