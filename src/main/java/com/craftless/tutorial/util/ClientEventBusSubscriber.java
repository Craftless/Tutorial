package com.craftless.tutorial.util;

import java.util.HashMap;
import java.util.UUID;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.client.gui.ItemPedestalScreen;
import com.craftless.tutorial.client.gui.JarScreen;
import com.craftless.tutorial.client.gui.RubyChestScreen;
import com.craftless.tutorial.client.renderer.HogRenderer;
import com.craftless.tutorial.client.renderer.ItemPedestalRenderer;
import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.init.ModTileEntityTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
				LivingEntity target = (LivingEntity) e.getTarget();
				if (target instanceof SheepEntity)
				{
					
					target.addPotionEffect(new EffectInstance(Effects.LEVITATION, 5 * 20));
					
				}
					
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
	
	
}
