package com.craftless.tutorial.items;

import java.util.List;

import com.craftless.tutorial.Tutorial;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
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
					float f2 = 10 * 2.0F;
				      int k1 = MathHelper.floor(player.getPosition().getX() - (double)f2 - 1.0D);
				      int l1 = MathHelper.floor(player.getPosition().getX() + (double)f2 + 1.0D);
				      int i2 = MathHelper.floor(player.getPosition().getY() - (double)f2 - 1.0D);
				      int i1 = MathHelper.floor(player.getPosition().getY() + (double)f2 + 1.0D);
				      int j2 = MathHelper.floor(player.getPosition().getZ() - (double)f2 - 1.0D);
				      int j1 = MathHelper.floor(player.getPosition().getZ() + (double)f2 + 1.0D);
				      List<Entity> list = player.getEntityWorld().getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
					
					
					for (Entity ent : list)
					{
						if (ent.isLiving())
						{
							LivingEntity lEnt = (LivingEntity) ent;
							if (lEnt instanceof MonsterEntity)
							{
								((MonsterEntity) lEnt).setAggroed(false);
							}
								
						}
						
					}
				}
			}
		}
	}


}

