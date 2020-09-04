package com.craftless.tutorial;

import java.util.HashMap;
import java.util.UUID;

import com.craftless.tutorial.init.ModItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WeaponAbilities 
{
	public HashMap<UUID, Boolean> isDoubleJumping = new HashMap<>();
	
	@SubscribeEvent
	public void onJump(LivingJumpEvent e)
	{
		if (e.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			if (player.isAirBorne)
			{
				if (!isDoubleJumping.containsKey(player.getUniqueID()))
				player.setMotion(player.getMotion().getX(), player.getMotion().getY() + 5, player.getMotion().getZ() + 3);
				isDoubleJumping.put(player.getUniqueID(), true);
			}
		}
	}
	
	@SubscribeEvent
	public void onFall(LivingFallEvent e)
	{
		if (e.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) e.getEntityLiving();
			if (isDoubleJumping.containsKey(player.getUniqueID()))
			{
				isDoubleJumping.remove(player.getUniqueID());
			}
		}
	}
	
	
	@SubscribeEvent 
	public void onHit(AttackEntityEvent e)
	{
		PlayerEntity player = e.getPlayer();
		if (player.getHeldItemMainhand().getItem() == ModItems.RUBY_SWORD.get().asItem())
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
}
