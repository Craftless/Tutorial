package com.craftless.tutorial.enchantments;

import java.util.UUID;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;
import com.craftless.tutorial.util.ClientEventBusSubscriber;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class CubismEnchantment extends Enchantment 
{

	public CubismEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) 
	{
		super(rarityIn, typeIn, slots);
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
	public static class CubismListener
	{
		@SubscribeEvent
		public void onDealDamage(LivingDamageEvent e)
		{
			if (e.getEntityLiving() instanceof SlimeEntity || e.getEntityLiving() instanceof MagmaCubeEntity || e.getEntityLiving() instanceof ZombieEntity)
			{
				if (e.getSource().getTrueSource() instanceof PlayerEntity)
				{
					
					PlayerEntity player = (PlayerEntity) e.getSource().getTrueSource();
					e.setAmount(e.getAmount() + 50 * (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.CUBISM.get(), player.getHeldItemMainhand().getItem().getDefaultInstance())));						
					
				}
			}
		}
	}
	
}
