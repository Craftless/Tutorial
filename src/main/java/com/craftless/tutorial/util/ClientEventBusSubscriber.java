package com.craftless.tutorial.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.InvisibleLightSourceBlock;
import com.craftless.tutorial.client.gui.ItemPedestalScreen;
import com.craftless.tutorial.client.gui.JarScreen;
import com.craftless.tutorial.client.gui.RubyChestScreen;
import com.craftless.tutorial.client.renderer.HogRenderer;
import com.craftless.tutorial.client.renderer.ItemPedestalRenderer;
import com.craftless.tutorial.entities.LightningStormArrowEntity;
import com.craftless.tutorial.goals.JumpHighGoal;
import com.craftless.tutorial.goals.JumpHighWhenInWaterGoal;
import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.init.ModEnchantments;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.items.FreezingSwordItem;
import com.craftless.tutorial.items.LightningSwordItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber 
{
	
	
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent e)
	{
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HOG.get(), HogRenderer::new);
		ScreenManager.registerFactory(ModContainerTypes.RUBY_CHEST.get(), RubyChestScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.JAR.get(), JarScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.ITEM_PEDESTAL.get(), ItemPedestalScreen::new);
		RenderTypeLookup.setRenderLayer(ModBlocks.RUBY_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.RUBY_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CORN_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.RUBY_DOOR.get(), RenderType.getCutout());
		
		ItemModelsProperties.func_239418_a_(ModItems.CRYSTAL.get(), new ResourceLocation(Tutorial.MOD_ID, "count"), new IItemPropertyGetter() {
			
			@Override
			public float call(ItemStack is, ClientWorld worldIn, LivingEntity entIn) {
				
				return is.isDamaged() ? 0.0F : 1.0F;
			}
		});
		
		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.ITEM_PEDESTAL.get()
				,ItemPedestalRenderer::new);
	}
	/*
	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
	{
		ModSpawnEggItem.initSpawnEggs();
	}
	*/
	
	//public HashMap<UUID, Boolean> isDoubleJumping = new HashMap<>();
	public HashMap<UUID, Long> howLongBeforeCanDoubleJumpAfterJump = new HashMap<>();
	
	@SubscribeEvent
	public void onJump(LivingJumpEvent e)
	{
		if (e.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			if (player.isAirBorne)
			{
				if (player.getItemStackFromSlot(EquipmentSlotType.FEET).isItemEqualIgnoreDurability(ModItems.RUBY_BOOTS.get().getDefaultInstance()))
				{
					if (howLongBeforeCanDoubleJumpAfterJump.containsKey(player.getUniqueID()) && howLongBeforeCanDoubleJumpAfterJump.get(player.getUniqueID()) > System.currentTimeMillis()) {}
				
					else {
						player.setMotion(player.getMotion().getX(), player.getMotion().getY() + 0.3, player.getMotion().getZ() + 0.3);
						player.sendMessage(new StringTextComponent("Double Jumped!"), player.getUniqueID());
						howLongBeforeCanDoubleJumpAfterJump.put(player.getUniqueID(), System.currentTimeMillis() + (3 * 100));
						//isDoubleJumping.put(player.getUniqueID(), true);
					}
				}
			}
		}
	}
		
	/*
	@SubscribeEvent
	public void onFall(LivingFallEvent e)
	{
		if (e.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			if (isDoubleJumping.containsKey(player.getUniqueID()))
			{
				//isDoubleJumping.remove(player.getUniqueID());
			}
		}
	}
	*/
	
	@SubscribeEvent 
	public void onHit(AttackEntityEvent e)
	{
		PlayerEntity player = e.getPlayer();
		if (player.getHeldItemMainhand().isItemEqualIgnoreDurability(ModItems.RUBY_SWORD.get().getDefaultInstance()))
		{
			if (e.getTarget().isAlive())
			{
				if (e.getTarget() instanceof LivingEntity)
				{
					LivingEntity target = (LivingEntity) e.getTarget();
					if (target instanceof SheepEntity)
					{
						
						target.addPotionEffect(new EffectInstance(Effects.LEVITATION, 5 * 20));
						
					}
				}
					
			}
		}		
		
		
	} 
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent e)
	{
		if (e.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			if (e.getSource().getImmediateSource() instanceof FireballEntity)
			{
				player.sendMessage(new StringTextComponent("Fireball damage"), player.getUniqueID());
				if (e.getSource().getTrueSource() instanceof PlayerEntity)
				{
					player.sendMessage(new StringTextComponent("True source is player"), player.getUniqueID());
					e.setAmount(e.getAmount() * 0.2f);
				}
				if (e.getSource().equals(DamageSource.GENERIC))
				{
					player.sendMessage(new StringTextComponent("Generic source detected"), player.getUniqueID());
					e.setAmount(e.getAmount() * 0.2f);
				}
			}
			
			if (e.getSource().getImmediateSource() instanceof LightningStormArrowEntity)
			{
				e.setCanceled(true);
			}
		}
	}
	
	 
	@SubscribeEvent
	public void onsfoais(LivingDamageEvent e)
	{
		if (e.getSource().getTrueSource() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getSource().getTrueSource();
			player.sendMessage(new StringTextComponent(Float.toString(e.getAmount())), (UUID)player.getUniqueID());
		}
	}
	
	
	@SubscribeEvent
	public void onDisconnect(PlayerLoggedOutEvent e)
	{
		if (howLongBeforeCanDoubleJumpAfterJump.containsKey(e.getPlayer().getUniqueID()))
		{
			howLongBeforeCanDoubleJumpAfterJump.remove(e.getPlayer().getUniqueID());
		}
	}
	
	@SubscribeEvent
	public void onTeleport(EnderTeleportEvent e)
	{
		if (e.getEntityLiving().getHeldItemMainhand().isItemEqualIgnoreDurability(ModItems.ASPECT_OF_THE_END.get().getDefaultInstance()) || e.getEntityLiving().getHeldItemOffhand().isItemEqualIgnoreDurability(ModItems.ASPECT_OF_THE_END.get().getDefaultInstance()))
		{
			e.setAttackDamage(1);
		}
	}
	
	
	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent e)
	{
		if (e.getEntity() instanceof MobEntity)
		{
			MobEntity lEnt = (MobEntity) e.getEntity();
			lEnt.goalSelector.addGoal(1, new JumpHighGoal(lEnt));
			
		}
		if (e.getEntity() instanceof ZombieEntity)
		{
			ZombieEntity ent = (ZombieEntity) e.getEntity();
			Stream<PrioritizedGoal> goals = ent.goalSelector.getRunningGoals();
			for (Object oGoal : goals.toArray())
			{
				Goal goal = (Goal)oGoal;
				if (goal instanceof ZombieAttackGoal)
				{
					ent.goalSelector.removeGoal(goal);
				}
			}
			
		}
		else if (e.getEntity() instanceof SheepEntity)
		{	
			SheepEntity ent = (SheepEntity) e.getEntity();
			ent.goalSelector.addGoal(2, new JumpHighWhenInWaterGoal(ent));
		}
			
		
	}

	
	
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e)
	{
		PlayerEntity player = e.player;
		BlockPos bp = player.getPosition();
		BlockState bs = player.getEntityWorld().getBlockState(bp);
		for (int x = -10; x < 10; x++)
		{
			for (int y = -10; y < 10; y++)
			{
				for (int z = -10; z < 10; z++)
				{
					if (bs.getBlock() instanceof InvisibleLightSourceBlock)
					{
						player.getEntityWorld().addParticle(ParticleTypes.BARRIER, bp.getX(), bp.getY(), bp.getZ(), 1, 1, 1);
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBreak(BlockEvent.BreakEvent e)
	{
		/*
		PlayerEntity player = e.getPlayer();
		BlockState bs = e.getState();
		BlockPos bp = e.getPos();
		ToolType tt = bs.getHarvestTool();
		World world = player.getEntityWorld();
		
		calculate(bp, bs, world, player);
		player.sendMessage(new StringTextComponent("Finished"), player.getUniqueID());
*/
		BlockPos bp = e.getPos();
		Block targetBlock = e.getState().getBlock();
		PlayerEntity player = e.getPlayer();
		World world = player.getEntityWorld();
		if (player.isCrouching())
		{
			int durability = player.getHeldItemMainhand().getMaxDamage() - player.getHeldItemMainhand().getDamage();
			int someDurability = 0;
			Set<BlockPos> orePositions = calculate3(bp, targetBlock, world, player, 2 + 4 * 2);
			for (BlockPos orePos : orePositions)
			{
				if (destroyBlock(world, orePos, player))
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
			if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMELTING_TOUCH.get(), player.getHeldItemMainhand()) > 0)
			{
				e.setCanceled(true);
			}
		}
		
		
		
	}
	
	
	private void calculate(BlockPos bp, BlockState bs, World world, PlayerEntity player)
	{
		int blocksBrokenDown = 0;
		int blocksBrokenUp = 0;
		int blocksBrokenEast = 0;
		int blocksBrokenWest = 0;

		while((blocksBrokenDown + blocksBrokenUp + blocksBrokenEast + blocksBrokenWest) < 50)
		{
			if (world.getBlockState(bp.east(blocksBrokenEast)).equals(bs))
			{
				Block.spawnDrops(world.getBlockState(bp.east(blocksBrokenEast)), world, bp.east(blocksBrokenEast));
				world.setBlockState(bp.east(blocksBrokenEast), Blocks.AIR.getDefaultState());
				blocksBrokenEast++;
			}
			
			if (world.getBlockState(bp.down(blocksBrokenDown)).equals(bs))
			{
				Block.spawnDrops(world.getBlockState(bp.down(blocksBrokenDown)), world, bp.down(blocksBrokenDown));
				world.setBlockState(bp.down(blocksBrokenDown), Blocks.AIR.getDefaultState());
				blocksBrokenDown++;
			}
			if (world.getBlockState(bp.up(blocksBrokenUp)).equals(bs))
			{
				Block.spawnDrops(world.getBlockState(bp.up(blocksBrokenUp)), world, bp.up(blocksBrokenUp));
				world.setBlockState(bp.up(blocksBrokenUp), Blocks.AIR.getDefaultState());
				blocksBrokenUp++;
			}
			
			if (world.getBlockState(bp.west(blocksBrokenWest)).equals(bs))
			{
				Block.spawnDrops(world.getBlockState(bp.west(blocksBrokenWest)), world, bp.west(blocksBrokenWest));
				world.setBlockState(bp.west(blocksBrokenWest), Blocks.AIR.getDefaultState());
				blocksBrokenWest++;
			}
			if (!world.getBlockState(bp.west(blocksBrokenWest)).equals(bs) && !world.getBlockState(bp.down(blocksBrokenDown)).equals(bs) && !world.getBlockState(bp.up(blocksBrokenUp)).equals(bs) && !world.getBlockState(bp.east(blocksBrokenEast)).equals(bs))
			{
				blocksBrokenUp += 5;
				player.sendMessage(new StringTextComponent("NO MORE" + blocksBrokenDown + blocksBrokenUp + blocksBrokenEast + blocksBrokenWest), player.getUniqueID());

			}
			player.sendMessage(new StringTextComponent("" + blocksBrokenDown + blocksBrokenUp + blocksBrokenEast + blocksBrokenWest), player.getUniqueID());
		}
		
	}
	
	public static Set<BlockPos> calculate2(BlockPos blockPos, Block targetBlock, World world, PlayerEntity player)
	{
		Queue<BlockPos> queue = new LinkedList<>();
		Set<BlockPos> explored = new LinkedHashSet<>();
		queue.add(blockPos);
		explored.add(blockPos);
		while(!queue.isEmpty())
		{
			final BlockPos bpRemoved = queue.remove();
			if (bpRemoved.getY() > 1)
			{
				addMatchingBlockToQueue1(targetBlock, world, bpRemoved.down(), queue, explored);
                addMatchingBlockToQueue1(targetBlock, world, bpRemoved.up(), queue, explored);
                addMatchingBlockToQueue1(targetBlock, world, bpRemoved.east(), queue, explored);
                addMatchingBlockToQueue1(targetBlock, world, bpRemoved.west(), queue, explored);
                addMatchingBlockToQueue1(targetBlock, world, bpRemoved.north(), queue, explored);
                addMatchingBlockToQueue1(targetBlock, world, bpRemoved.south(), queue, explored);
			}explored.add(bpRemoved);
		}
		return explored;
	}
	
	public static Set<BlockPos> calculate3(BlockPos blockPos, Block targetBlock, World world, PlayerEntity player, final int depth)
	{
		Queue<BlockEntry> queue = new LinkedList<>();
		Set<BlockPos> explored = new LinkedHashSet<>();
		queue.add(new BlockEntry(blockPos, depth));
		explored.add(blockPos);
		while(!queue.isEmpty())
		{
			final BlockEntry entry = queue.remove();
			if (entry.getDepth() - 1 > 0)
			{
				addMatchingBlockToQueue(targetBlock, world, entry.bp.down(), queue, explored, entry.getDepth() - 1);
                addMatchingBlockToQueue(targetBlock, world, entry.bp.up(), queue, explored, entry.getDepth() - 1);
                addMatchingBlockToQueue(targetBlock, world, entry.bp.east(), queue, explored, entry.getDepth() - 1);
                addMatchingBlockToQueue(targetBlock, world, entry.bp.west(), queue, explored, entry.getDepth() - 1);
                addMatchingBlockToQueue(targetBlock, world, entry.bp.north(), queue, explored, entry.getDepth() - 1);
                addMatchingBlockToQueue(targetBlock, world, entry.bp.south(), queue, explored, entry.getDepth() - 1);
			}
			explored.add(entry.bp);
		}
		return explored;
		
	}
	
	public static void addMatchingBlockToQueue1(final Block targetBlock, final World world, final BlockPos bp, final Queue<BlockPos> queue, final Set<BlockPos> explored) {
		BlockState currentBs = world.getBlockState(bp);
        if (currentBs.isAir(world, bp)) {
            return;
        }
        if ((currentBs.getBlock() == null || currentBs.getBlock() == targetBlock.getBlock()) && !explored.contains(bp)) {
            queue.offer(bp);
        }
    }
	
	public static void addMatchingBlockToQueue(final Block targetBlock, final World world, final BlockPos bp, final Queue<BlockEntry> queue, final Set<BlockPos> explored, final int depth) {
		BlockState currentBs = world.getBlockState(bp);
        if (currentBs.isAir(world, bp)) {
            return;
        }
        if ((currentBs.getBlock() == null || currentBs.getBlock() == targetBlock.getBlock()) && !explored.contains(bp)) {
            queue.offer(new BlockEntry(bp, depth));
        }
    }
	
	
	public static boolean destroyBlock(World world, BlockPos bp, PlayerEntity player)
	{
		if (!world.getBlockState(bp).isAir(world, bp))
		{
			
			if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.TELEKINESIS.get(), player.getHeldItemMainhand()) > 0)
			{
				List<ItemStack> drops = getItems(world, bp, player).getIs();
				for (ItemStack drop : drops)
				{
					player.addItemStackToInventory(drop);
				}
				world.setBlockState(bp, Blocks.AIR.getDefaultState());
			}
			
			else if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMELTING_TOUCH.get(), player.getHeldItemMainhand()) > 0)
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
	                    
	                    world.removeBlock(bp, false);
	            	}
	            	else 
	    			{
	    				Block.spawnDrops(world.getBlockState(bp), world, bp);
	    				world.setBlockState(bp, Blocks.AIR.getDefaultState());
	    				player.sendMessage(new StringTextComponent("Destroyed Block"), player.getUniqueID());
	    			}
	            }
			}
			
			else 
			{
				Block.spawnDrops(world.getBlockState(bp), world, bp);
			}
			world.setBlockState(bp, Blocks.AIR.getDefaultState());
			return true;
		}
		return false;
	}
	
	public static ItemDropsPlusEXP getItems(World world, BlockPos bp, PlayerEntity player)
	{
		if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMELTING_TOUCH.get(), player.getHeldItemMainhand()) > 0)
		{
			BlockState bs = world.getBlockState(bp);
			if (bs.getBlock() == net.minecraftforge.common.Tags.Blocks.ORES)
            {
            	if (!player.isCreative())
            	{
            		final ItemStack result = new ItemStack((IItemProvider)Items.INGOTS);
//            		final ItemEntity resultEntity = new ItemEntity(world, bp.getX(), bp.getY(), bp.getZ(), result);

            		
            		final ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, bp.getX(), bp.getY(), bp.getZ(), 3);

            		List<ItemStack> resultList = new ArrayList<>();
            		resultList.add(result);
            		return new ItemDropsPlusEXP(resultList, expOrb);
            	}
            }
            
            else if (bs.getBlock().getRegistryName().toString().contains("ore"))
            {
            	final String t = bs.getBlock().getRegistryName().toString().replace("ore", "ingot");
            	
            	if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(t)) && !player.isCreative())
            	{

            		final ItemStack result = new ItemStack((IItemProvider)ForgeRegistries.ITEMS.getValue(new ResourceLocation(t)));
                    final ItemEntity resultEntity = new ItemEntity(world, (double)bp.getX(), (double)bp.getY(), (double)bp.getZ(), result);

            		
                    final ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, bp.getX(), bp.getY(), bp.getZ(), 3);
                    
                    List<ItemStack> resultList = new ArrayList<>();
            		resultList.add(result);
            		return new ItemDropsPlusEXP(resultList, expOrb);

            	}
            	else 
    			{
    				final List<ItemStack> drops = (List<ItemStack>)Block.getDrops(bs, (ServerWorld) world, bp, null);
    				world.setBlockState(bp, Blocks.AIR.getDefaultState());
    				player.sendMessage(new StringTextComponent("Got Block"), player.getUniqueID());
    				return new ItemDropsPlusEXP(drops, null);
    			}
            }
		}
		else
		{
			final List<ItemStack> drops = (List<ItemStack>)Block.getDrops(world.getBlockState(bp), (ServerWorld) world, bp, (TileEntity)null);
			return new ItemDropsPlusEXP(drops, null);
		}
		return null;
		
	}
	
	
	
	
	

//	public ItemDropsPlusEXP getSmeltingTouchDrops(List<ItemStack> isList, World world, BlockPos bp)
//	{
//		BlockState bs = world.getBlockState(bp);
//		if (bs.getBlock() == net.minecraftforge.common.Tags.Blocks.ORES)
//        {
//        	if (!player.isCreative())
//        	{
//        		final ItemStack result = new ItemStack((IItemProvider)Items.INGOTS);
////        		final ItemEntity resultEntity = new ItemEntity(world, bp.getX(), bp.getY(), bp.getZ(), result);
//
//        		
//        		final ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, bp.getX(), bp.getY(), bp.getZ(), 3);
//
//        		List<ItemStack> resultList = new ArrayList<>();
//        		resultList.add(result);
//        		return new ItemDropsPlusEXP(resultList, expOrb);
//        	}
//        }
//        
//        else if (bs.getBlock().getRegistryName().toString().contains("ore"))
//        {
//        	final String t = bs.getBlock().getRegistryName().toString().replace("ore", "ingot");
//        	
//        	if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(t)) && !player.isCreative())
//        	{
//
//        		final ItemStack result = new ItemStack((IItemProvider)ForgeRegistries.ITEMS.getValue(new ResourceLocation(t)));
//                final ItemEntity resultEntity = new ItemEntity(world, (double)bp.getX(), (double)bp.getY(), (double)bp.getZ(), result);
//
//        		
//                final ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, bp.getX(), bp.getY(), bp.getZ(), 3);
//                
//                List<ItemStack> resultList = new ArrayList<>();
//        		resultList.add(result);
//        		return new ItemDropsPlusEXP(resultList, expOrb);
//
//        	}
//        }
//		return null;
//	}
//	
	
	
	
	
	
	
	
	
	public static class ItemDropsPlusEXP
	{
		List<ItemStack> is;
		ExperienceOrbEntity expOrb;
		
		public ItemDropsPlusEXP(List<ItemStack> is, ExperienceOrbEntity expOrb)
		{
			this.is = is;
			this.expOrb = expOrb;
		}
		
		public ExperienceOrbEntity getExpOrb() {
			return expOrb;
		}
		
		public List<ItemStack> getIs() {
			return is;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	public static class BlockEntry
	{
		private BlockPos bp;
		private int depth;
		
		public BlockEntry(BlockPos bp, int depth)
		{
			this.bp = bp;
			this.depth = depth;
		}
		
		public BlockPos getBp() {
			return bp;
		}
		
		public int getDepth() {
			return depth;
		}
		
		@Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final BlockEntry blockEntry = (BlockEntry)o;
            return Objects.equals(this.bp, blockEntry.bp);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.bp);
        }
	}
	
	
	
	
}
