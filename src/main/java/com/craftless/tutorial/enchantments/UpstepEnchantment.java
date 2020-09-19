package com.craftless.tutorial.enchantments;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class UpstepEnchantment extends Enchantment
{

	public UpstepEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) 
	{
		super(rarityIn, typeIn, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}
	
	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return ench.equals(Enchantments.DEPTH_STRIDER) ? false : true;
	}
	
	@Override
	public String getName() {
		return "Upstep";
	}
	
	@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static class UpstepEquipped
	{
		@SubscribeEvent
		public static void doStuff(PlayerTickEvent e)
		{
			PlayerEntity player = e.player;
			World worldIn = player.world;
			
			if (EnchantmentHelper.getEnchantments(player.getItemStackFromSlot(EquipmentSlotType.FEET)).get(ModEnchantments.UPSTEP.get()) != null)
			{
				if (player.isCrouching())
				{
					if (worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.BARRIER)
					{
						worldIn.setBlockState(player.getPosition().down(), Blocks.AIR.getDefaultState());
						worldIn.setBlockState(player.getPosition().down().down(), Blocks.BARRIER.getDefaultState());
					}
				}
				else if (worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.AIR)
				{
					worldIn.setBlockState(player.getPosition().down(), Blocks.BARRIER.getDefaultState());
				}
			}
		}
	}
	
	

}
