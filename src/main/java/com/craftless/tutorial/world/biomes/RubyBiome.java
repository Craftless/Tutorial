package com.craftless.tutorial.world.biomes;

import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModEntityTypes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RubyBiome extends Biome
{

	public RubyBiome() 
	{
		super(new Biome.Builder()
				.category(Category.PLAINS)
				.precipitation(RainType.SNOW)
				.temperature(0.1f)
				.downfall(0.5f)
				.depth(0.12f)
				.parent(null)
				.scale(1)
				.func_235097_a_(new BiomeAmbience.Builder().setWaterColor(0xae6abc).setWaterFogColor(0xd39ce8).setFogColor(0xfffff).build())
				.surfaceBuilder(SurfaceBuilder.DEFAULT, 
						new SurfaceBuilderConfig(
								Blocks.COARSE_DIRT.getDefaultState(), 
								ModBlocks.RUBY_ORE.get().getDefaultState(), 
								Blocks.BRAIN_CORAL.getDefaultState())));
		
		
		addCarver(Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.14285715f)));
		addCarver(Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.2f)));
		DefaultBiomeFeatures.addBambooJungleVegetation(this);
		DefaultBiomeFeatures.addExtraEmeraldOre(this);
		DefaultBiomeFeatures.addOres(this);
		
		addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 8, 3, 5));
		for (SpawnListEntry entry : Biomes.PLAINS.getSpawns(EntityClassification.CREATURE))
		{
			addSpawn(EntityClassification.CREATURE, entry);
		}
	}
	
	
	
	
	
	
}
	

