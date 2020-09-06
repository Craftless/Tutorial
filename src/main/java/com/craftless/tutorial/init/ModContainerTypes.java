package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.container.RubyChestContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes 
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Tutorial.MOD_ID);
	public static final RegistryObject<ContainerType<RubyChestContainer>> RUBY_CHEST = CONTAINERS_TYPES.register("ruby_chest", () -> IForgeContainerType.create(RubyChestContainer::new));
	
}
