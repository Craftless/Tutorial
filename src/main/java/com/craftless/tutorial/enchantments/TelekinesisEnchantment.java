package com.craftless.tutorial.enchantments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.init.ModEnchantments;
import com.craftless.tutorial.util.ClientEventBusSubscriber;
import com.craftless.tutorial.util.ClientEventBusSubscriber.ItemDropsPlusEXP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class TelekinesisEnchantment extends Enchantment
{

	public TelekinesisEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
		super(rarityIn, typeIn, slots);
		// TODO Auto-generated constructor stub
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
	public static class TelekinesisListener
	{
		@SubscribeEvent(priority = EventPriority.HIGH)
		public static void onBreak(BlockEvent.BreakEvent e)
		{
			if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get(), e.getPlayer().getHeldItemMainhand()) > 0)
			{
				BlockPos bp = e.getPos();
				BlockState bs = e.getState();
				PlayerEntity player = e.getPlayer();
				World world = player.getEntityWorld();
				int exp = e.getExpToDrop();
				final MinecraftServer server= world.getServer();
	            final ServerWorld serverWorld = server.getWorld(World.field_234918_g_);
	            
	            ItemDropsPlusEXP idpe = ClientEventBusSubscriber.getItems(world, bp, player);
	            boolean thereIsSpace = false;
	            for (int i = 0; i < 36; i++)
	            {
	            	if (player.inventory.getStackInSlot(i) == ItemStack.EMPTY)
	            	{
	            		thereIsSpace = true;
	            	}
	            }
	            
				if (thereIsSpace)
	            {
		            player.sendMessage(new StringTextComponent("TELEKINESIS ACTIVATED"), player.getUniqueID());
					final List<ItemStack> drops = (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMELTING_TOUCH.get(), player.getHeldItemMainhand()) > 0) ? idpe.getIs() : (List<ItemStack>)Block.getDrops(bs, (ServerWorld) world, bp, (TileEntity)null);
					
		            final ItemStack ore0 = new ItemStack((IItemProvider)drops.get(0).getItem());
					
					world.setBlockState(bp, Blocks.AIR.getDefaultState());
		            drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));
		            player.giveExperiencePoints(exp);
		            
		            if (player.isCrouching())
		    		{
		    			int durability = player.getHeldItemMainhand().getMaxDamage() - player.getHeldItemMainhand().getDamage();
		    			int someDurability = 0;
		    			Set<BlockPos> orePositions = ClientEventBusSubscriber.calculate3(bp, bs.getBlock(), world, player, 2 + 4 * 2);
		    			for (BlockPos orePos : orePositions)
		    			{
		    				if (ClientEventBusSubscriber.destroyBlock(world, orePos, player))
		    				{
		    					someDurability++;
		    				}
		    					
		    			}
		    			
		    			if (someDurability > player.getHeldItemMainhand().getMaxDamage() - player.getHeldItemMainhand().getDamage())
		    			{
		    				player.getHeldItemMainhand().setDamage(player.getHeldItemMainhand().getMaxDamage());
		    	
		    			}
		    			else
		    			{
		    				player.getHeldItemMainhand().setDamage(player.getHeldItemMainhand().getDamage() + someDurability);
		    			}
		    		}
		            
		            
					e.setCanceled(true);
	            }
			}

		}
//		
		@SubscribeEvent
        public static void entityOnDeath(final LivingDropsEvent event) {
			{
                final LivingEntity entity = event.getEntityLiving();
                final Collection<ItemEntity> items = (Collection<ItemEntity>)event.getDrops();
                final World world = entity.getEntityWorld();
                final Entity source = event.getSource().getTrueSource();
                final List<ItemEntity> itemlist = new ArrayList<ItemEntity>();
                items.forEach(itemlist::add);
                final MinecraftServer server = world.getServer();
                final ServerWorld serverworld = server.getWorld(World.field_234918_g_);
                if (source instanceof PlayerEntity) {
                    final PlayerEntity player = (PlayerEntity)source;
                    final ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                    final int lvl = EnchantmentHelper.getEnchantmentLevel((Enchantment)ModEnchantments.TELEKINESIS.get(), stack);
                    if (lvl == 1) {
                        items.forEach(itemEntity -> player.addItemStackToInventory(itemEntity.getItem()));
                    }
                }
			}
        }
		
		public static boolean destroyBlock(World world, BlockPos bp, PlayerEntity player)
		{
			if (!world.getBlockState(bp).isAir(world, bp))
			{
				final List<ItemStack> drops = (List<ItemStack>)Block.getDrops(world.getBlockState(bp), (ServerWorld) world, bp, (TileEntity)null);
				for (ItemStack drop : drops)
				{
					player.addItemStackToInventory(drop);
				}
				world.setBlockState(bp, Blocks.AIR.getDefaultState());
				return true;
			}
			return false;
		}
		
		

	}

}
