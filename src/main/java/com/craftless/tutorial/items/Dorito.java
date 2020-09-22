package com.craftless.tutorial.items;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.craftless.tutorial.Tutorial;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class Dorito extends Item{

	public Dorito() {
		super(new Item.Properties()
				.group(Tutorial.TAB)
				.food(new Food.Builder()
						.fastToEat()
						.hunger(2)
						.saturation(0)
						.setAlwaysEdible()
						.build()));
	}
	
	@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static class DoritoListener
	{
		@SubscribeEvent
		public void onEat(LivingEntityUseItemEvent.Finish e) throws InterruptedException
		{
			if (e.getEntityLiving() instanceof PlayerEntity)
			{
				PlayerEntity player = (PlayerEntity) e.getEntityLiving();
				player.sendMessage(new StringTextComponent("Player!"), player.getUniqueID());
				if (e.getItem().getItem() instanceof Dorito)
				{
					double x = player.getPosX();
					double y = player.getPosY();
					double z = player.getPosZ();
					List<Entity> entities = player.getEntityWorld().getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(x,y,z,x+10,y+10,z+10));
					HashMap<UUID, LivingEntity> revengeTarget = new HashMap<>();
					HashMap<UUID, Float> AIMoveSpeed = new HashMap<>();
					for (Entity ent : entities)
					{
						if (ent.isLiving())
						{
							LivingEntity livingEnt = (LivingEntity) ent;
							revengeTarget.put(livingEnt.getUniqueID(), livingEnt.getRevengeTarget());
							livingEnt.setRevengeTarget(null);
							AIMoveSpeed.put(livingEnt.getUniqueID(), livingEnt.getAIMoveSpeed());
							livingEnt.setAIMoveSpeed(1);
						}
						
					}
					wait(1000 * 30);
					
					for (Entity ent : entities)
					{
						if (ent.isLiving())
						{
							LivingEntity livingEnt = (LivingEntity) ent;
							livingEnt.setRevengeTarget(revengeTarget.get(livingEnt.getUniqueID()));
							revengeTarget.remove(livingEnt.getUniqueID());
							livingEnt.setAIMoveSpeed(AIMoveSpeed.get(livingEnt.getUniqueID()));
							AIMoveSpeed.remove(livingEnt.getUniqueID());

						}
					}
				}
			}
		}
	}


}

