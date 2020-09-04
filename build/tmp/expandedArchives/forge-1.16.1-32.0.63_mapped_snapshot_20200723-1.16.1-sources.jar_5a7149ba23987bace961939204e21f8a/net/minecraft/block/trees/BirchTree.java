package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class BirchTree extends Tree {
   /**
    * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
    */
   @Nullable
   protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
      return Feature.field_236291_c_.withConfiguration(largeHive ? DefaultBiomeFeatures.BIRCH_TREE_WITH_MANY_BEEHIVES_CONFIG : DefaultBiomeFeatures.BIRCH_TREE_CONFIG);
   }
}