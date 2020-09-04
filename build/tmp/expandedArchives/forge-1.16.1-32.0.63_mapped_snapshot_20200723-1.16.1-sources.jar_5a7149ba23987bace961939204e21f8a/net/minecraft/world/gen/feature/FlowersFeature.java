package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public abstract class FlowersFeature<U extends IFeatureConfig> extends Feature<U> {
   public FlowersFeature(Codec<U> p_i231922_1_) {
      super(p_i231922_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, U p_230362_6_) {
      BlockState blockstate = this.getFlowerToPlace(p_230362_4_, p_230362_5_, p_230362_6_);
      int i = 0;

      for(int j = 0; j < this.getFlowerCount(p_230362_6_); ++j) {
         BlockPos blockpos = this.getNearbyPos(p_230362_4_, p_230362_5_, p_230362_6_);
         if (p_230362_1_.isAirBlock(blockpos) && blockpos.getY() < 255 && blockstate.isValidPosition(p_230362_1_, blockpos) && this.isValidPosition(p_230362_1_, blockpos, p_230362_6_)) {
            p_230362_1_.setBlockState(blockpos, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }

   public abstract boolean isValidPosition(IWorld world, BlockPos pos, U config);

   public abstract int getFlowerCount(U config);

   public abstract BlockPos getNearbyPos(Random rand, BlockPos pos, U config);

   public abstract BlockState getFlowerToPlace(Random rand, BlockPos pos, U confgi);
}