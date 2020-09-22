package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.particles.ColouredParticle;
import com.craftless.tutorial.particles.ColouredParticle.ColouredParticleData;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Tutorial.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModParticles 
{
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Tutorial.MOD_ID);
	
	public static final RegistryObject<ParticleType<ColouredParticleData>> COLOURED_PARTICLE = PARTICLE_TYPES.register("coloured_particle", () -> new ColouredParticleData(false, ColouredParticleData.DESERIALIZER));
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void registerParticleFactory(ParticleFactoryRegisterEvent e)
	{
		Minecraft.getInstance().particles.registerFactory(ModParticles.COLOURED_PARTICLE.get(), ColouredParticle.Factory::new);
	}
}
