package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds 
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,Tutorial.MOD_ID);
	
	public static final Lazy<SoundEvent> LAZY_DON = Lazy.of(() -> new SoundEvent(new ResourceLocation(Tutorial.MOD_ID, "entity.hog.ambient")));
	public static final RegistryObject<SoundEvent> AMBIENT = SOUNDS.register("entity.hog.ambient", () -> new SoundEvent(new ResourceLocation(Tutorial.MOD_ID, "entity.hog.ambient")));
}
