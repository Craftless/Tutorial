package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.container.ItemPedestalContainer;
import com.craftless.tutorial.container.JarContainer;
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
	public static final RegistryObject<ContainerType<JarContainer>> JAR = CONTAINERS_TYPES.register("jar", () -> IForgeContainerType.create(JarContainer::new));
	public static final RegistryObject<ContainerType<ItemPedestalContainer>> ITEM_PEDESTAL = CONTAINERS_TYPES.register("item_pedestal", () -> IForgeContainerType.create(ItemPedestalContainer::new));
}
