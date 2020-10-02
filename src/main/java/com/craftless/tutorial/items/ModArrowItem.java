package com.craftless.tutorial.items;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModArrowItem extends ArrowItem 
{
	
	private boolean infinity;
	
	private Class<? extends AbstractArrowEntity> entityClass;
    public ModArrowItem(Item.Properties builder, final Class<? extends AbstractArrowEntity> entityClass) 
    {
       super(builder);
       this.infinity = false;
       this.entityClass = entityClass;
    }
	@Override
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) 
	{
		try {
		    final Constructor<? extends AbstractArrowEntity> ctor = this.entityClass.getDeclaredConstructor(World.class, LivingEntity.class);
		    ctor.setAccessible(true);
		    final AbstractArrowEntity arrow = (AbstractArrowEntity)ctor.newInstance(worldIn, shooter);
		    return arrow;
		}
		catch (NoSuchMethodException e) {
		    e.printStackTrace();
		}
		catch (SecurityException e2) {
		    e2.printStackTrace();
		}
		catch (InstantiationException e3) {
		    e3.printStackTrace();
		}
		catch (IllegalAccessException e4) {
		    e4.printStackTrace();
		}
		catch (IllegalArgumentException e5) {
		    e5.printStackTrace();
		}
		catch (InvocationTargetException e6) {
		    e6.printStackTrace();
		}
		return null;
	    
	    
	}
	
	public ModArrowItem setInfinity(final boolean infinity)
	{
		this.infinity = infinity;
		return (ModArrowItem)this;
	}
	
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player) {
		final int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
		return enchant > 0 && this.infinity;
	}
}
