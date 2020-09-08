package com.craftless.tutorial.world.feature;

import java.util.Random;

import com.craftless.tutorial.init.ModBlocks;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;


public class RubyTree extends Tree
{
	public static final BaseTreeFeatureConfig RUBY_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.RUBY_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.RUBY_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build();

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
		return Feature.field_236291_c_.withConfiguration(RUBY_TREE_CONFIG);
	}
			
}
