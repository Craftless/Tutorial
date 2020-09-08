package com.craftless.tutorial.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.craftless.tutorial.init.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RubyBiomeSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

 	public RubyBiomeSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232136_1_) {
		super(p_i232136_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
 	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
 			BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
 		Random rd = new Random();
 		int i = rd.nextInt(3);
 		if (i == 0)
 			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
 					defaultFluid, seaLevel, seed, new SurfaceBuilderConfig(ModBlocks.RUBY_ORE.get().getDefaultState(),
 							ModBlocks.RUBY_BLOCK.get().getDefaultState(), Blocks.ACACIA_PLANKS.getDefaultState()));
 		else
 			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
 					defaultFluid, seaLevel, seed,
 					new SurfaceBuilderConfig(
 							i == 1 ? Blocks.GRASS_BLOCK.getDefaultState() : ModBlocks.RUBY_ORE.get().getDefaultState(),
 									ModBlocks.RUBY_BLOCK.get().getDefaultState(), Blocks.ACACIA_PLANKS.getDefaultState()));
 	}
 }