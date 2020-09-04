package net.minecraft.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IExplosionContext;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BedBlock extends HorizontalBlock implements ITileEntityProvider {
   public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
   public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
   protected static final VoxelShape field_220176_c = Block.makeCuboidShape(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D);
   protected static final VoxelShape field_220177_d = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
   protected static final VoxelShape field_220178_e = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D);
   protected static final VoxelShape field_220179_f = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
   protected static final VoxelShape field_220180_g = Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D);
   protected static final VoxelShape NORTH_FACING_SHAPE = VoxelShapes.or(field_220176_c, field_220177_d, field_220179_f);
   protected static final VoxelShape SOUTH_FACING_SHAPE = VoxelShapes.or(field_220176_c, field_220178_e, field_220180_g);
   protected static final VoxelShape WEST_FACING_SHAPE = VoxelShapes.or(field_220176_c, field_220177_d, field_220178_e);
   protected static final VoxelShape EAST_FACING_SHAPE = VoxelShapes.or(field_220176_c, field_220179_f, field_220180_g);
   private final DyeColor color;

   public BedBlock(DyeColor colorIn, AbstractBlock.Properties properties) {
      super(properties);
      this.color = colorIn;
      this.setDefaultState(this.stateContainer.getBaseState().with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.valueOf(false)));
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public static Direction getBedDirection(IBlockReader reader, BlockPos pos) {
      BlockState blockstate = reader.getBlockState(pos);
      return blockstate.getBlock() instanceof BedBlock ? blockstate.get(HORIZONTAL_FACING) : null;
   }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
      if (worldIn.isRemote) {
         return ActionResultType.CONSUME;
      } else {
         if (state.get(PART) != BedPart.HEAD) {
            pos = pos.offset(state.get(HORIZONTAL_FACING));
            state = worldIn.getBlockState(pos);
            if (!state.isIn(this)) {
               return ActionResultType.CONSUME;
            }
         }

         if (!func_235330_a_(worldIn)) {
            worldIn.removeBlock(pos, false);
            BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
            if (worldIn.getBlockState(blockpos).isIn(this)) {
               worldIn.removeBlock(blockpos, false);
            }

            worldIn.createExplosion((Entity)null, DamageSource.func_233546_a_(), (IExplosionContext)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
            return ActionResultType.SUCCESS;
         } else if (state.get(OCCUPIED)) {
            if (!this.func_226861_a_(worldIn, pos)) {
               player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
            }

            return ActionResultType.SUCCESS;
         } else {
            player.trySleep(pos).ifLeft((p_220173_1_) -> {
               if (p_220173_1_ != null) {
                  player.sendStatusMessage(p_220173_1_.getMessage(), true);
               }

            });
            return ActionResultType.SUCCESS;
         }
      }
   }

   public static boolean func_235330_a_(World world) {
      return world.func_230315_m_().func_241510_j_();
   }

   private boolean func_226861_a_(World world, BlockPos pos) {
      List<VillagerEntity> list = world.getEntitiesWithinAABB(VillagerEntity.class, new AxisAlignedBB(pos), LivingEntity::isSleeping);
      if (list.isEmpty()) {
         return false;
      } else {
         list.get(0).wakeUp();
         return true;
      }
   }

   /**
    * Block's chance to react to a living entity falling on it.
    */
   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
      super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
   }

   /**
    * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
    * on its own
    */
   public void onLanded(IBlockReader worldIn, Entity entityIn) {
      if (entityIn.isSuppressingBounce()) {
         super.onLanded(worldIn, entityIn);
      } else {
         this.func_226860_a_(entityIn);
      }

   }

   private void func_226860_a_(Entity entity) {
      Vector3d vector3d = entity.getMotion();
      if (vector3d.y < 0.0D) {
         double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
         entity.setMotion(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
      }

   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == getDirectionToOther(stateIn.get(PART), stateIn.get(HORIZONTAL_FACING))) {
         return facingState.isIn(this) && facingState.get(PART) != stateIn.get(PART) ? stateIn.with(OCCUPIED, facingState.get(OCCUPIED)) : Blocks.AIR.getDefaultState();
      } else {
         return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      }
   }

   /**
    * Given a bed part and the direction it's facing, find the direction to move to get the other bed part
    */
   private static Direction getDirectionToOther(BedPart p_208070_0_, Direction direction) {
      return p_208070_0_ == BedPart.FOOT ? direction : direction.getOpposite();
   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!worldIn.isRemote && player.isCreative()) {
         BedPart bedpart = state.get(PART);
         if (bedpart == BedPart.FOOT) {
            BlockPos blockpos = pos.offset(getDirectionToOther(bedpart, state.get(HORIZONTAL_FACING)));
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (blockstate.getBlock() == this && blockstate.get(PART) == BedPart.HEAD) {
               worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
               worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
         }
      }

      super.onBlockHarvested(worldIn, pos, state, player);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      Direction direction = context.getPlacementHorizontalFacing();
      BlockPos blockpos = context.getPos();
      BlockPos blockpos1 = blockpos.offset(direction);
      return context.getWorld().getBlockState(blockpos1).isReplaceable(context) ? this.getDefaultState().with(HORIZONTAL_FACING, direction) : null;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      Direction direction = func_226862_h_(state).getOpposite();
      switch(direction) {
      case NORTH:
         return NORTH_FACING_SHAPE;
      case SOUTH:
         return SOUTH_FACING_SHAPE;
      case WEST:
         return WEST_FACING_SHAPE;
      default:
         return EAST_FACING_SHAPE;
      }
   }

   public static Direction func_226862_h_(BlockState state) {
      Direction direction = state.get(HORIZONTAL_FACING);
      return state.get(PART) == BedPart.HEAD ? direction.getOpposite() : direction;
   }

   @OnlyIn(Dist.CLIENT)
   public static TileEntityMerger.Type getMergeType(BlockState state) {
      BedPart bedpart = state.get(PART);
      return bedpart == BedPart.HEAD ? TileEntityMerger.Type.FIRST : TileEntityMerger.Type.SECOND;
   }

   public static Optional<Vector3d> getBedSpawnPosition(EntityType<?> entity, IWorldReader reader, BlockPos pos, int p_220172_3_) {
      Direction direction = reader.getBlockState(pos).get(HORIZONTAL_FACING);
      int i = pos.getX();
      int j = pos.getY();
      int k = pos.getZ();

      for(int l = 0; l <= 1; ++l) {
         int i1 = i - direction.getXOffset() * l - 1;
         int j1 = k - direction.getZOffset() * l - 1;
         int k1 = i1 + 2;
         int l1 = j1 + 2;

         for(int i2 = i1; i2 <= k1; ++i2) {
            for(int j2 = j1; j2 <= l1; ++j2) {
               BlockPos blockpos = new BlockPos(i2, j, j2);
               Optional<Vector3d> optional = getWakeUpDisplacement(entity, reader, blockpos);
               if (optional.isPresent()) {
                  if (p_220172_3_ <= 0) {
                     return optional;
                  }

                  --p_220172_3_;
               }
            }
         }
      }

      return Optional.empty();
   }

   public static Optional<Vector3d> getWakeUpDisplacement(EntityType<?> entity, IWorldReader reader, BlockPos pos) {
      VoxelShape voxelshape = reader.getBlockState(pos).getCollisionShape(reader, pos);
      if (voxelshape.getEnd(Direction.Axis.Y) > 0.4375D) {
         return Optional.empty();
      } else {
         BlockPos.Mutable blockpos$mutable = pos.toMutable();

         while(blockpos$mutable.getY() >= 0 && pos.getY() - blockpos$mutable.getY() <= 2 && reader.getBlockState(blockpos$mutable).getCollisionShape(reader, blockpos$mutable).isEmpty()) {
            blockpos$mutable.move(Direction.DOWN);
         }

         VoxelShape voxelshape1 = reader.getBlockState(blockpos$mutable).getCollisionShape(reader, blockpos$mutable);
         if (voxelshape1.isEmpty()) {
            return Optional.empty();
         } else {
            double d0 = (double)blockpos$mutable.getY() + voxelshape1.getEnd(Direction.Axis.Y) + 2.0E-7D;
            if ((double)pos.getY() - d0 > 2.0D) {
               return Optional.empty();
            } else {
               Vector3d vector3d = new Vector3d((double)blockpos$mutable.getX() + 0.5D, d0, (double)blockpos$mutable.getZ() + 0.5D);
               AxisAlignedBB axisalignedbb = entity.getBoundingBoxWithSizeApplied(vector3d.x, vector3d.y, vector3d.z);
               return reader.hasNoCollisions(axisalignedbb) && reader.func_234853_a_(axisalignedbb.expand(0.0D, (double)-0.2F, 0.0D)).noneMatch(entity::func_233597_a_) ? Optional.of(vector3d) : Optional.empty();
            }
         }
      }
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   public PushReaction getPushReaction(BlockState state) {
      return PushReaction.DESTROY;
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(HORIZONTAL_FACING, PART, OCCUPIED);
   }

   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new BedTileEntity(this.color);
   }

   /**
    * Called by ItemBlocks after a block is set in the world, to allow post-place logic
    */
   public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      if (!worldIn.isRemote) {
         BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING));
         worldIn.setBlockState(blockpos, state.with(PART, BedPart.HEAD), 3);
         worldIn.func_230547_a_(pos, Blocks.AIR);
         state.updateNeighbours(worldIn, pos, 3);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public DyeColor getColor() {
      return this.color;
   }

   /**
    * Return a random long to be passed to {@link IBakedModel#getQuads}, used for random model rotations
    */
   @OnlyIn(Dist.CLIENT)
   public long getPositionRandom(BlockState state, BlockPos pos) {
      BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING), state.get(PART) == BedPart.HEAD ? 0 : 1);
      return MathHelper.getCoordinateRandom(blockpos.getX(), pos.getY(), blockpos.getZ());
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }
}