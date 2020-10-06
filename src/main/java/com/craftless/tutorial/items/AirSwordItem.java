package com.craftless.tutorial.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class AirSwordItem extends SwordItem
{

	public AirSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.setMotion(target.getMotion().getX(), target.getMotion().getY() + 2, target.getMotion().getZ());
		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (isSelected)
		{
			if (entityIn.isLiving())
			{
				LivingEntity lEnt = (LivingEntity) entityIn;
				lEnt.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 1));
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		double xCoord = playerIn.getPosX();
		double yCoord = playerIn.getPosY();
		double zCoord = playerIn.getPosZ();
		List<Entity> entityList = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(xCoord-25, yCoord-25, zCoord-25, xCoord+25, yCoord+25, zCoord+25));
		for (Entity ent : entityList)
		{
			playerIn.sendMessage(new TranslationTextComponent(ent.toString()), playerIn.getUniqueID());
			ent.setMotion(ent.getMotion().getX(), ent.getMotion().getY() + 3, ent.getMotion().getZ());
			if (ent.isLiving())
			{
				LivingEntity lEnt = (LivingEntity) ent;
				lEnt.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 20 * 2, -2));
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
