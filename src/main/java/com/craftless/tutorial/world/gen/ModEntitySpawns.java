package com.craftless.tutorial.world.gen;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEntityTypes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitySpawns 
{
	@SubscribeEvent
	public static void SpawnEntities(FMLLoadCompleteEvent e)
	{
		for (Biome biome : ForgeRegistries.BIOMES)
		{
			if (!biome.getCategory().equals(Biome.Category.NETHER) && !biome.getCategory().equals(Biome.Category.THEEND)) 
			{
				if (!biome.getCategory().equals(Biome.Category.OCEAN)) 
				{
					biome.getSpawns(EntityClassification.CREATURE)
							.add(new Biome.SpawnListEntry(ModEntityTypes.HOG.get(), 8, 3, 5));
				}
			}
		}
	}
}
