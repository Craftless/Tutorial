package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveStick extends Item
{

	public ExplosiveStick() 
	{
		super(new Item.Properties()
				.group(Tutorial.TAB)
				.rarity(Rarity.RARE)
				.maxStackSize(1)
		);
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		Tutorial.LOGGER.info("Player right clicked with explosive stick");
		World world = playerIn.getEntityWorld();
		if (!world.isRemote)
		{
			
			
			//RayTraceResult hitInfo = rayTrace(worldIn, playerIn, FluidMode.NONE);
//			RayTraceResult hitInfo2 = worldIn.rayTraceBlocks(playerIn.getEyeHeight(), playerIn.getForward().normalize().mul(new Vector3d(0, 0, 30)), , shape, state)
			BlockRayTraceResult blockHitInfo = rayTrace(worldIn, playerIn, FluidMode.NONE);
			//BlockRayTraceResult sfad = worldIn.rayTraceBlocks(new RayTraceContext(playerIn.getPositionVec().add(0, playerIn.getEyeHeight(), 0), (playerIn.getPositionVec().add(0, playerIn.getEyeHeight(), 0)).mul(0, 30, 0), BlockMode.COLLIDER, FluidMode.NONE, null));
			Minecraft mc = Minecraft.getInstance();
			RayTraceResult entityRayTraceResult = mc.objectMouseOver;
				
			if (entityRayTraceResult != null)
			{
				Vector3d entPos = entityRayTraceResult.getHitVec();
				playerIn.getEntityWorld().createExplosion(null, entPos.getX(), entPos.getY(), entPos.getZ(), 3, Explosion.Mode.BREAK);
			}
			else if (blockHitInfo != null)
			{
				Vector3d blockPos = blockHitInfo.getHitVec();
				playerIn.getEntityWorld().createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3, Explosion.Mode.BREAK);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) 
	{
		entity.setGlowing(true);
		return super.onEntityItemUpdate(stack, entity);
	}
	
}
