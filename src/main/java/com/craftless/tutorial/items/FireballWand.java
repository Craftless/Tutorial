package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireballWand extends Item
{

    public FireballWand()
    {
        super(new Item.Properties().group(Tutorial.TAB).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
    	if (!worldIn.isRemote())
    	{
    		FireballEntity fireball = new FireballEntity(worldIn, playerIn, playerIn.getLookVec().getX(), playerIn.getLookVec().getY(), playerIn.getLookVec().getZ());
    		fireball.setMotion(fireball.getMotion().mul(2, 2, 2));
	    	fireball.setPosition(playerIn.getPositionVec().getX(), playerIn.getPositionVec().getY() + playerIn.getEyeHeight(), playerIn.getPositionVec().getZ());
	        worldIn.addEntity(fireball);
    	}
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
}
