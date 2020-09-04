package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;

public class JungleTree extends BigTree {
   /**
    * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
    */
   @Nullable
   protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
      return (new TreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG)).withConfiguration(DefaultBiomeFeatures.JUNGLE_SAPLING_TREE_CONFIG);
   }

   /**
    * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of the huge variant of this tree
    */
   @Nullable
   protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random rand) {
      return Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.MEGA_JUNGLE_TREE_CONFIG);
   }
}