package com.craftless.tutorial.enchantments;

import java.util.Set;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class ScrapingEnchantment extends Enchantment
{

	public ScrapingEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
		super(rarityIn, typeIn, slots);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canGenerateInLoot() {
		return true;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	@Override
	public void onEntityDamaged(LivingEntity user, Entity target, int level) {
		if (target.isLiving())
		{
			LivingEntity livingTarget = (LivingEntity) target;
			if (livingTarget.getHeldItemMainhand().isDamageable())
			{
				int durability = livingTarget.getHeldItemMainhand().getMaxDamage() - livingTarget.getHeldItemMainhand().getDamage();
				int someDurability = level * 100;
				
				if (someDurability > livingTarget.getHeldItemMainhand().getMaxDamage() - livingTarget.getHeldItemMainhand().getDamage())
				{
					livingTarget.getHeldItemMainhand().setDamage(livingTarget.getHeldItemMainhand().getMaxDamage());
		
				}
				else
				{
					livingTarget.getHeldItemMainhand().setDamage(livingTarget.getHeldItemMainhand().getDamage() + someDurability);
				}
			}
		}
	}
	

}
