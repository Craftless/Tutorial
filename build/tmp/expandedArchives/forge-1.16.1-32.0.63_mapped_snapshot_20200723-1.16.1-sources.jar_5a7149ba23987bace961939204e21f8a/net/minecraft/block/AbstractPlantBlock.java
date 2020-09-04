package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public abstract class AbstractPlantBlock extends Block {
   protected final Direction growthDirection;
   protected final boolean waterloggable;
   protected final VoxelShape shape;

   protected AbstractPlantBlock(AbstractBlock.Properties p_i241178_1_, Direction p_i241178_2_, VoxelShape p_i241178_3_, boolean p_i241178_4_) {
      super(p_i241178_1_);
      this.growthDirection = p_i241178_2_;
      this.shape = p_i241178_3_;
      this.waterloggable = p_i241178_4_;
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.offset(this.growthDirection.getOpposite());
      BlockState blockstate = worldIn.getBlockState(blockpos);
      Block block = blockstate.getBlock();
      if (!this.func_230333_c_(block)) {
         return false;
      } else {
         return block == this.getTopPlantBlock() || block == this.func_230330_d_() || blockstate.isSolidSide(worldIn, blockpos, this.growthDirection);
      }
   }

   protected boolean func_230333_c_(Block block) {
      return true;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return this.shape;
   }

   protected abstract AbstractTopPlantBlock getTopPlantBlock();

   protected abstract Block func_230330_d_();
}