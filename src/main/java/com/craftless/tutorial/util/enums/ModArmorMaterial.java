package com.craftless.tutorial.util.enums;

import java.util.function.Supplier;

import com.craftless.tutorial.Tutorial;

import com.craftless.tutorial.init.ModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterial implements IArmorMaterial
{
	
	RUBY(Tutorial.MOD_ID + "ruby", 25, new int[] { 2, 5, 6, 2 }, 18, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f, () -> { return Ingredient.fromItems(ModItems.RUBY.get()); }, 0);
	
	private static final int[] MAX_DAMAGE_ARRAY = new int[] {11, 16, 15, 13};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;
	private final float knockbackResistance;
	
	ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial, float knockbackResistance)
	{
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = repairMaterial;
		this.knockbackResistance = knockbackResistance;
	}
	
	
	@Override
	public int getDamageReductionAmount(EquipmentSlotType arg0) {
		// TODO Auto-generated method stub
		return this.damageReductionAmountArray[arg0.getIndex()];
	}

	@Override
	public int getDurability(EquipmentSlotType arg0) {
		// TODO Auto-generated method stub
		return MAX_DAMAGE_ARRAY[arg0.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getEnchantability() {
		// TODO Auto-generated method stub
		return this.enchantability;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Ingredient getRepairMaterial() {
		// TODO Auto-generated method stub
		return this.repairMaterial.get();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public SoundEvent getSoundEvent() {
		// TODO Auto-generated method stub
		return this.soundEvent;
	}

	@Override
	public float getToughness() {
		// TODO Auto-generated method stub
		return this.toughness;
	}


	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

}
