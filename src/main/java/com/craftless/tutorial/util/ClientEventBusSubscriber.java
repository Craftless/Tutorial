package com.craftless.tutorial.util;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.client.renderer.HogRenderer;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.items.ModSpawnEggItem;

import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber 
{
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent e)
	{
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HOG.get(), HogRenderer::new);
	}
	
	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
	{
		ModSpawnEggItem.initSpawnEggs();
	}
}
