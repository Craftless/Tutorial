package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.effects.JumpHighEffect;
import com.craftless.tutorial.effects.QuicknessEffect;

import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPotions 
{
	public static final DeferredRegister<Effect> POTION_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Tutorial.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Tutorial.MOD_ID);

	public static final RegistryObject<Effect> QUICKNESS_EFFECT = POTION_EFFECTS.register("quickness_effect", () -> new QuicknessEffect(EffectType.BENEFICIAL, 37848743).addAttributesModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 1.0D, Operation.ADDITION));
	public static final RegistryObject<Effect> JUMP_HIGH_EFFECT = POTION_EFFECTS.register("jump_high_effect", () -> new JumpHighEffect(EffectType.NEUTRAL, 37948712));

	
	public static final RegistryObject<Potion> QUICKNESS_POTION = POTIONS.register("quickness_effect", () -> new Potion(new EffectInstance(QUICKNESS_EFFECT.get(), 4 * 20 * 60)));
	public static final RegistryObject<Potion> JUMP_HIGH_POTION = POTIONS.register("jump_high_effect", () -> new Potion(new EffectInstance(JUMP_HIGH_EFFECT.get(), 4 * 20 * 60)));
	
}
