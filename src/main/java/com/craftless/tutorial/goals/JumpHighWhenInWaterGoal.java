package com.craftless.tutorial.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;

public class JumpHighWhenInWaterGoal extends Goal
{
	Entity ent;
	
	public JumpHighWhenInWaterGoal(Entity ent) 
	{
		this.ent = ent;
	}

	@Override
	public boolean shouldExecute() {
		return ent.isInWater();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return ent.isInWater();
	}
	
	@Override
	public void startExecuting() {
		ent.setMotion(ent.getMotion().getX(), ent.getMotion().getX() + 2, ent.getMotion().getZ());
	}
	
	
	
}
