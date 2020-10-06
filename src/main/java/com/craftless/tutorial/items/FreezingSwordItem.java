package com.craftless.tutorial.items;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FreezingSwordItem extends SwordItem
{

	int duration;
	int amplifier;

	
	public FreezingSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		
		if (tier.getMaxUses() < 50)
		{
			duration = 20 * 2;
		}
		else if (tier.getMaxUses() < 100)
		{
			duration = 20 * 4;
		}
		else if (tier.getMaxUses() < 400)
		{
			duration = 20 * 6;
		}
		else if (tier.getMaxUses() < 900)
		{
			duration = 20 * 7;
		}
		else if (tier.getMaxUses() < 1500)
		{
			duration = 20 * 8;
		}
		else if (tier.getMaxUses() < 2000)
		{
			duration = 20 * 10;
		}
		else
		{
			duration = 20 * 12;
		}
		
		if ((tier.getAttackDamage() + attackDamageIn) < 3)
		{
			amplifier = 2;
		}
		else if ((tier.getAttackDamage() + attackDamageIn) < 5)
		{
			amplifier = 1;
		}
		else
		{
			amplifier = 0;
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) 
	{
		
		target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 10, 100));
		
		return super.hitEntity(stack, target, attacker);

	}
	
	public HashMap<UUID, Long> cooldown = new HashMap<>();
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (cooldown.containsKey(playerIn.getUniqueID()))
		{
			if ((cooldown.get(playerIn.getUniqueID()) + 10 * 1000) < System.currentTimeMillis())
			{
				doAbilityStuff(worldIn, playerIn);
				cooldown.put(playerIn.getUniqueID(), System.currentTimeMillis());
			}
			else
			{
				playerIn.sendMessage(new StringTextComponent("Ability on cooldown. Try again in " + (int)((cooldown.get(playerIn.getUniqueID()) + 10 * 1000 - System.currentTimeMillis()) / 1000) + " seconds"), playerIn.getUniqueID());;
			}
		}
		else
		{
			doAbilityStuff(worldIn, playerIn);
			cooldown.put(playerIn.getUniqueID(), System.currentTimeMillis());
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void doAbilityStuff(World world, PlayerEntity playerIn)
	{
		double xCoord = playerIn.getPosX();
		double yCoord = playerIn.getPosY();
		double zCoord = playerIn.getPosZ();
		List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(xCoord-25, yCoord-25, zCoord-25, xCoord+25, yCoord+25, zCoord+25));
		for (Entity ent : entityList)
		{
			playerIn.sendMessage(new TranslationTextComponent(ent.toString()), playerIn.getUniqueID());
			if (ent.isLiving())
			{
				LivingEntity lEnt = (LivingEntity) ent;
				lEnt.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 10, 100));
			}
		}
	}
	
}
