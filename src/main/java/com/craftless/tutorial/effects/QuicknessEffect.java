package com.craftless.tutorial.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class QuicknessEffect extends Effect
{

	public QuicknessEffect(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Quickness");
	}
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		super.performEffect(entityLivingBaseIn, amplifier);
		entityLivingBaseIn.extinguish();
		entityLivingBaseIn.setArrowCountInEntity(20);
		entityLivingBaseIn.setGlowing(true);
		entityLivingBaseIn.setJumping(true);
	}

}
