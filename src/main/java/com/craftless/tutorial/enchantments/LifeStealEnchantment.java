package com.craftless.tutorial.enchantments;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class LifeStealEnchantment extends Enchantment
{

	public LifeStealEnchantment() {
		super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static class LifeStealListener
	{
		@SubscribeEvent
		public static void onDamaged(LivingDamageEvent e)
		{
			Entity entity = e.getSource().getTrueSource();
			if (entity != null)
			{
				if (entity.isLiving())
				{
					LivingEntity lEnt = (LivingEntity) entity;
					int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIFE_STEAL.get(), lEnt.getHeldItem(Hand.MAIN_HAND));
	
					if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIFE_STEAL.get(), lEnt.getHeldItemMainhand()) > 0)
					{
						lEnt.setHealth(lEnt.getHealth() + e.getAmount() * (0.1f * level));
					}
				}
			}
		}
	}

}
