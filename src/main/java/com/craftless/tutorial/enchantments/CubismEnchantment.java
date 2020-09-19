package com.craftless.tutorial.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class CubismEnchantment extends Enchantment
{

	public CubismEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) 
	{
		super(rarityIn, typeIn, slots);
	}
	
	
	@Override
	public void onEntityDamaged(LivingEntity user, Entity target, int level) 
	{
		if (target instanceof SlimeEntity || target instanceof MagmaCubeEntity)
		{
			target.attackEntityFrom(DamageSource.GENERIC, level * 100F);
		}
			
	}

}
