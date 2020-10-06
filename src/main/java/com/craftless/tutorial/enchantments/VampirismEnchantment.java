package com.craftless.tutorial.enchantments;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class VampirismEnchantment extends Enchantment
{

	public VampirismEnchantment() {
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
	public static class VampirismListener
	{
		@SubscribeEvent
		public static void onDeath(LivingDropsEvent e)
		{
			if (e.getSource().getTrueSource() != null)
			{
				if (e.getSource().getTrueSource().isLiving())
				{
					LivingEntity lEnt = (LivingEntity) e.getSource().getTrueSource();
					if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.VAMPIRISM.get(), lEnt.getHeldItemMainhand()) > 0)
					{
						float health = e.getEntityLiving().getMaxHealth();
						lEnt.setHealth(lEnt.getHealth() + health * (0.1f * EnchantmentHelper.getEnchantmentLevel(ModEnchantments.VAMPIRISM.get(), lEnt.getHeldItemMainhand())));
					}
				}
			}
		}
	}
	

}
