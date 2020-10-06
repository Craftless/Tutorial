package com.craftless.tutorial.goals;

import com.craftless.tutorial.init.ModPotions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.potion.EffectInstance;

public class JumpHighGoal extends Goal
{
	
	LivingEntity ent;
	public JumpHighGoal(LivingEntity ent)
	{
		this.ent = ent;
	}

	@Override
	public boolean shouldExecute() {
		if (ent.isPotionActive(ModPotions.JUMP_HIGH_EFFECT.get()))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if (ent.isPotionActive(ModPotions.JUMP_HIGH_EFFECT.get()))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		ent.setMotion(ent.getMotion().getX(), ent.getMotion().getY() + 2, ent.getMotion().getZ());
	}

}
