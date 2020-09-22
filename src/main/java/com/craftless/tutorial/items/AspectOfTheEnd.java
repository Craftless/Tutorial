package com.craftless.tutorial.items;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class AspectOfTheEnd extends SwordItem
{

	public AspectOfTheEnd(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		if (!worldIn.isRemote)
		{
			playerIn.sendMessage(new StringTextComponent("AOTED"), playerIn.getUniqueID());
	//		if (((playerIn.getPosition()).add((playerIn.getLookVec().mul(new Vector3d(0, 0, 8))));
			boolean blocksAreInTheWay = false;
			for (int i = 0; i < 8; i++)
			{
				if (worldIn.getBlockState(playerIn.getPosition().offset(Direction.getFacingFromVector(playerIn.getLookVec().getX(), playerIn.getLookVec().getY(), playerIn.getLookVec().getZ()), i)).getBlock() != Blocks.AIR)
				{
					blocksAreInTheWay = true;
				}	
			}
			if (blocksAreInTheWay == false)
			{
				playerIn.setPositionAndRotation(playerIn.getPosition().offset(Direction.getFacingFromVector(playerIn.getLookVec().getX(), playerIn.getLookVec().getY(), playerIn.getLookVec().getZ()), 8).getX(), playerIn.getPosition().offset(Direction.getFacingFromVector(playerIn.getLookVec().getX(), playerIn.getLookVec().getY(), playerIn.getLookVec().getZ()), 8).getY(), playerIn.getPosition().offset(Direction.getFacingFromVector(playerIn.getLookVec().getX(), playerIn.getLookVec().getY(), playerIn.getLookVec().getZ()), 8).getZ(), playerIn.getYaw(Minecraft.getInstance().getRenderPartialTicks()), playerIn.getPitch(Minecraft.getInstance().getRenderPartialTicks()));
				playerIn.attackEntityFrom(DamageSource.FALL, 1);
				playerIn.sendMessage(new StringTextComponent("directly tped"), playerIn.getUniqueID());
	
			}
			else
			{
				EnderPearlEntity pearl = new EnderPearlEntity(worldIn, playerIn);
				pearl.setNoGravity(true);
				pearl.setMotion(pearl.getMotion().mul(new Vector3d(0, 0, 30)));
				pearl.addTag("Aspect of the End");
				worldIn.addEntity(pearl);
				playerIn.sendMessage(new StringTextComponent("Threw enderPearl"), playerIn.getUniqueID());
	
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
}
