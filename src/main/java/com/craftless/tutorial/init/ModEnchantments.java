package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.enchantments.BigDongEnchantment;
import com.craftless.tutorial.enchantments.CubismEnchantment;
import com.craftless.tutorial.enchantments.LifeStealEnchantment;
import com.craftless.tutorial.enchantments.ScrapingEnchantment;
import com.craftless.tutorial.enchantments.SmeltingTouchEnchantment;
import com.craftless.tutorial.enchantments.TelekinesisEnchantment;
import com.craftless.tutorial.enchantments.UpstepEnchantment;
import com.craftless.tutorial.enchantments.VampirismEnchantment;

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
	public static final RegistryObject<Enchantment> SCRAPING = ENCHANTMENTS.register("scraping", () -> new ScrapingEnchantment(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));
	public static final RegistryObject<Enchantment> TELEKINESIS = ENCHANTMENTS.register("telekinesis", () -> new TelekinesisEnchantment(Rarity.RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));
	public static final RegistryObject<Enchantment> SMELTING_TOUCH = ENCHANTMENTS.register("smelting_touch", SmeltingTouchEnchantment::new);
	public static final RegistryObject<Enchantment> LIFE_STEAL = ENCHANTMENTS.register("life_steal", LifeStealEnchantment::new);
	public static final RegistryObject<Enchantment> BIG_DONG = ENCHANTMENTS.register("big_dong", BigDongEnchantment::new);
	public static final RegistryObject<Enchantment> VAMPIRISM = ENCHANTMENTS.register("vampirism", VampirismEnchantment::new);


}
