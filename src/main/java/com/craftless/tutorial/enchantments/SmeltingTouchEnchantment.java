package com.craftless.tutorial.enchantments;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

public class SmeltingTouchEnchantment extends Enchantment 
{

	protected static IRecipeType<? extends AbstractCookingRecipe> recipeType;

	public SmeltingTouchEnchantment() {
		super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
		this.recipeType = IRecipeType.SMELTING;
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static class SmeltingTouchListener
	{


		@SubscribeEvent
		public static void onBreak(BlockEvent.BreakEvent e)
		{
			BlockPos bp = e.getPos();
			BlockState bs = e.getState();
			PlayerEntity player = e.getPlayer();
			World world = player.getEntityWorld();
			int exp = e.getExpToDrop();
			final MinecraftServer server= world.getServer();
            final ServerWorld serverWorld = server.getWorld(World.field_234918_g_);
            int someDurability = 0;
            
            
	            if (destroyBlock(world, bp, player))
	            {
//	                IRecipe<?> irecipe = world.getRecipeManager().getRecipe((IRecipeType<FurnaceRecipe>)IRecipeType.SMELTING, null, world).orElse(null);
                	

	            	
//	                if (!(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get(), player.getHeldItemMainhand()) > 0))
//	                {
	                player.sendMessage(new StringTextComponent("SMELTING TOUCH"), player.getUniqueID());
	            	
	            	someDurability++;
	            	player.getHeldItemMainhand().setDamage(player.getHeldItemMainhand().getDamage() + someDurability);
	            }
	        	e.setCanceled(true);
//            }
            
		}
		public static boolean destroyBlock(World world, BlockPos bp, PlayerEntity player)
		{
			if (!world.getBlockState(bp).isAir(world, bp))
			{
				if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMELTING_TOUCH.get(), player.getHeldItemMainhand()) > 0)
				{
					BlockState bs = world.getBlockState(bp);
					if (bs.getBlock() == net.minecraftforge.common.Tags.Blocks.ORES)
		            {
		            	if (!player.isCreative())
		            	{
		            		final ItemStack result = new ItemStack((IItemProvider)Items.INGOTS);
		            		final ItemEntity resultEntity = new ItemEntity(world, bp.getX(), bp.getY(), bp.getZ(), result);
		            		world.addEntity(resultEntity);
		            		
		            		final ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, bp.getX(), bp.getY(), bp.getZ(), 3);
		            		world.addEntity(expOrb);
		            		player.sendMessage(new StringTextComponent("Blocks.ORE"), player.getUniqueID());
		            		world.removeBlock(bp, false);
		            		
		            	}
		            }
		            
		            else if (bs.getBlock().getRegistryName().toString().contains("ore"))
		            {
		            	final String t = bs.getBlock().getRegistryName().toString().replace("ore", "ingot");
		            	
		            	if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(t)) && !player.isCreative())
		            	{

		            		final ItemStack result = new ItemStack((IItemProvider)ForgeRegistries.ITEMS.getValue(new ResourceLocation(t)));
		                    final ItemEntity resultEntity = new ItemEntity(world, (double)bp.getX(), (double)bp.getY(), (double)bp.getZ(), result);
		                    world.addEntity(resultEntity);
		            		
		                    final ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, bp.getX(), bp.getY(), bp.getZ(), 3);
		                    world.addEntity(expOrb);
		            		player.sendMessage(new StringTextComponent("registry name 'ore'"), player.getUniqueID());

		                    world.removeBlock(bp, false);
						}
						else
						{
							Block.spawnDrops(world.getBlockState(bp), world, bp);
							world.removeBlock(bp, false);

						}
		            }
				}
				else 
				{
					Block.spawnDrops(world.getBlockState(bp), world, bp);
				}
				world.setBlockState(bp, net.minecraft.block.Blocks.AIR.getDefaultState());
				return true;
			}
			return false;
		}
	}
	
}
