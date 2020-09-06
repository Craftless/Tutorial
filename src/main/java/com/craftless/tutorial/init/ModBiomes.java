package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.world.biomes.RubyBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes 
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Tutorial.MOD_ID);
	
	public static final RegistryObject<Biome> RUBY_BIOME = BIOMES.register("ruby_biome", RubyBiome::new);
	
	
	
	
	public static void RegisterBiomes()
	{
		RegisterBiome(RUBY_BIOME.get(), Type.OVERWORLD, Type.PLAINS);
	}
	
	private static void RegisterBiome(Biome biome, Type... types)
	{
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addSpawnBiome(biome);
	}
	
	
	
}
