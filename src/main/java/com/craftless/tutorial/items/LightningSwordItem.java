package com.craftless.tutorial.items;

import java.util.HashMap;
import java.util.UUID;

import com.craftless.tutorial.entities.LightningStormArrowEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class LightningSwordItem extends SwordItem
{

	public LightningSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack) {
		return 50 * 20;
	}
	
	@Override
	public boolean isPiglinCurrency(ItemStack stack) {
		return true;
	}
	
	@Override
	public SoundEvent getEatSound() {
		return SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON;
	}
	
	@Override
	public SoundEvent getDrinkSound() {
		return SoundEvents.BLOCK_NETHERITE_BLOCK_STEP;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		LightningBoltEntity lbEnt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, attacker.getEntityWorld());
		lbEnt.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
		attacker.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 10 * 20));
		attacker.getEntityWorld().addEntity(lbEnt);
		attacker.extinguish();
		for (int x = -5; x <= 5; x++)
		{
			for (int y = -5; y <= 5; y++)
			{
				for (int z = -5; z <= 5; z++)
				{
					BlockPos bp = new BlockPos(target.getPosition().getX() + x, target.getPosition().getY() + y, target.getPosition().getZ() + z);
					if (attacker.getEntityWorld().getBlockState(bp).getBlock().equals(Blocks.FIRE))
					{
						attacker.getEntityWorld().setBlockState(bp, Blocks.AIR.getDefaultState());
					}
				}
			}
		}
		return super.hitEntity(stack, target, attacker);
	}
	
	public HashMap<UUID, Long> cooldown = new HashMap<>();
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (cooldown.containsKey(playerIn.getUniqueID()))
		{
			if (cooldown.containsKey(playerIn.getUniqueID()) && cooldown.get(playerIn.getUniqueID()) < System.currentTimeMillis())
			{
				playerIn.sendMessage(new StringTextComponent("containsKey and activated"), playerIn.getUniqueID());
				doStuff(playerIn);
			}
			else
			{
				playerIn.sendMessage(new StringTextComponent("Ability on cooldown. Try again in " + (int)((cooldown.get(playerIn.getUniqueID()) - System.currentTimeMillis()) / 1000) + " seconds"), playerIn.getUniqueID());;
			}
		}
		else if (!cooldown.containsKey(playerIn.getUniqueID()))
		{
			playerIn.sendMessage(new StringTextComponent("cooldown no key"), playerIn.getUniqueID());
			doStuff(playerIn);
			cooldown.put(playerIn.getUniqueID(), System.currentTimeMillis() + 10 * 1000);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
		return true;
	}
	
	
	
	private void doStuff(PlayerEntity playerIn)
	{
		LightningStormArrowEntity lbEnt = new LightningStormArrowEntity(playerIn.getEntityWorld(), playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
		playerIn.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 10 * 20));
		playerIn.getEntityWorld().addEntity(lbEnt);
		playerIn.addPotionEffect(new EffectInstance(Effects.REGENERATION, 2, 10));
		playerIn.extinguish();
		for (int x = -5; x <= 5; x++)
		{
			for (int y = -5; y <= 5; y++)
			{
				for (int z = -5; z <= 5; z++)
				{
					BlockPos bp = new BlockPos(playerIn.getPosition().getX() + x, playerIn.getPosition().getY() + y, playerIn.getPosition().getZ() + z);
					if (playerIn.getEntityWorld().getBlockState(bp).getBlock().equals(Blocks.FIRE))
					{
						playerIn.getEntityWorld().setBlockState(bp, Blocks.AIR.getDefaultState());
					}
				}
			}
		}
	}
	
	
	
	
	
	


}
