package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.enchantments.CubismEnchantment;
import com.craftless.tutorial.enchantments.UpstepEnchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments 
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Tutorial.MOD_ID);
	
	public static final RegistryObject<Enchantment> UPSTEP = ENCHANTMENTS.register("upstep", () -> new UpstepEnchantment(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] {EquipmentSlotType.FEET}));
	public static final RegistryObject<Enchantment> CUBISM = ENCHANTMENTS.register("cubism", () -> new CubismEnchantment(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));
}
