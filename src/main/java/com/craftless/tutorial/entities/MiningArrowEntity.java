package com.craftless.tutorial.entities;

import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class MiningArrowEntity extends AbstractArrowEntity
{
	private int tick;

	
	public MiningArrowEntity(EntityType<? extends MiningArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public MiningArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.MINING_ARROW.get(), x, y, z, worldIn);
	}

	public MiningArrowEntity(World worldIn, LivingEntity shooter) {
	    super(ModEntityTypes.MINING_ARROW.get(), shooter, worldIn);
	}
	
	@Override
	public void tick() {
		tick++;
		if (tick > 20 * 5)
		{
			this.remove();
		}
		else
		{
		    this.setGlowing(true);
		    
			for (int x = -1; x <= 1 ; x++)
			{
				for (int y = -1; y <= 1 ; y++)
				{
					for (int z = -1; z <= 1 ; z++)
					{
						BlockPos bp = this.getPosition().subtract(new Vector3i(x, y, z));
						BlockState bs = this.world.getBlockState(bp);
						if (!bs.equals(Blocks.AIR.getDefaultState()) && !bs.equals(Blocks.BEDROCK.getDefaultState()))
						{
							Block.spawnDrops(bs, this.world, bp);
							this.world.setBlockState(bp, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
			super.tick();
		}
		this.addVelocity(0, 0.1, 0);
	}

	

	
	@Override
	protected ItemStack getArrowStack() {
		return ModItems.MINING_ARROW_ITEM.get().getDefaultInstance();
	}
	

}
