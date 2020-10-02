package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids 
{

	public static final ResourceLocation MILK_STILL_RL = new ResourceLocation(Tutorial.MOD_ID, "block/milk_still");
	public static final ResourceLocation MILK_FLOWING_RL = new ResourceLocation(Tutorial.MOD_ID, "block/milk_flowing");
	public static final ResourceLocation MILK_OVERLAY_RL = new ResourceLocation(Tutorial.MOD_ID, "block/milk_overlay");

	
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Tutorial.MOD_ID);
	public static final RegistryObject<FlowingFluid> MILK_FLUID = FLUIDS.register("milk_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.MILK_PROPERTIES));
	public static final RegistryObject<FlowingFluid> MILK_FLOWING = FLUIDS.register("milk_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.MILK_PROPERTIES));

	public static final ForgeFlowingFluid.Properties MILK_PROPERTIES = new ForgeFlowingFluid.Properties(() -> MILK_FLUID.get(), () -> MILK_FLOWING.get(),
			FluidAttributes.builder(MILK_STILL_RL, MILK_FLOWING_RL)
					.density(5)
					.luminosity(15)
					.rarity(Rarity.RARE)
					.overlay(MILK_OVERLAY_RL)
					.viscosity(69420)
					.sound(SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET, SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS))
				.block(() -> ModFluids.MILK_BLOCK.get())
				.bucket(() -> Items.MILK_BUCKET)
				.bucket(() -> ModItems.MILK_BUCKET.get())
				.canMultiply()
				.explosionResistance(2);
	
	public static final RegistryObject<FlowingFluidBlock> MILK_BLOCK = ModBlocks.BLOCKS.register("milk", () -> new FlowingFluidBlock(() -> ModFluids.MILK_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100).noDrops().jumpFactor(10).variableOpacity()));
}
