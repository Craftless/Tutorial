package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SilverfishBlock extends Block {
   private final Block mimickedBlock;
   private static final Map<Block, Block> normalToInfectedMap = Maps.newIdentityHashMap();

   public SilverfishBlock(Block blockIn, AbstractBlock.Properties properties) {
      super(properties);
      this.mimickedBlock = blockIn;
      normalToInfectedMap.put(blockIn, this);
   }

   public Block getMimickedBlock() {
      return this.mimickedBlock;
   }

   public static boolean canContainSilverfish(BlockState state) {
      return normalToInfectedMap.containsKey(state.getBlock());
   }

   private void spawnSilverFish(World world, BlockPos pos) {
      SilverfishEntity silverfishentity = EntityType.SILVERFISH.create(world);
      silverfishentity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addEntity(silverfishentity);
      silverfishentity.spawnExplosionParticle();
   }

   /**
    * Perform side-effects from block dropping, such as creating silverfish
    */
   public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack) {
      super.spawnAdditionalDrops(state, worldIn, pos, stack);
      if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
         this.spawnSilverFish(worldIn, pos);
      }

   }

   /**
    * Called when this Block is destroyed by an Explosion
    */
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if (!worldIn.isRemote) {
         this.spawnSilverFish(worldIn, pos);
      }

   }

   public static BlockState infest(Block blockIn) {
      return normalToInfectedMap.get(blockIn).getDefaultState();
   }
}