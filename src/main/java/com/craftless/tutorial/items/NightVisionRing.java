package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class NightVisionRing extends Item{

	public NightVisionRing() {
		super(new Item.Properties().group(Tutorial.TAB));
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		LivingEntity ent = (LivingEntity)entityIn;
		if (!ent.isPotionActive(Effects.NIGHT_VISION))
		{
			ent.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 10*20, 1, true, false));
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
