package com.craftless.tutorial.enchantments;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class BigDongEnchantment extends Enchantment
{

	public BigDongEnchantment() {
		super(Rarity.RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
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
	public static class BigDongListener
	{
		
		@SubscribeEvent
		public static void onHoe(UseHoeEvent e)
		{
			PlayerEntity player = e.getPlayer();
			Hand hand = e.getContext().getHand();
			BlockPos bp = e.getContext().getPos();
			int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BIG_DONG.get(), player.getHeldItem(hand));
			ItemStack hoe = player.getHeldItem(hand);
			World world = player.getEntityWorld();
			
			if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BIG_DONG.get(), hoe) < 0) { return; }
			
			for (int x = -level; x <= level; x++)
			{
				for (int z = -level; z <= level; z++)
				{
					BlockPos pos = new BlockPos(bp.getX() + x, bp.getY(), bp.getZ() + z);
					for (Block block : Blocks.DIRT.getAllElements())
					{
						if (world.getBlockState(pos) == block.getDefaultState())
						{
							world.setBlockState(pos, net.minecraft.block.Blocks.FARMLAND.getDefaultState());

						}
					}
				}
			}
		}
	}
	
}
