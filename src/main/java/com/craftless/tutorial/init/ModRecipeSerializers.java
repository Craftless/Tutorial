package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.recipes.IModRecipe;
import com.craftless.tutorial.recipes.ModRecipe;
import com.craftless.tutorial.recipes.ModRecipeSerializer;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers 
{
	public static final IRecipeSerializer<ModRecipe> MOD_RECIPE_SERIALIZER = new ModRecipeSerializer();
	public static final IRecipeType<IModRecipe> MOD_TYPE = registerType(IModRecipe.RECIPE_TYPE_ID);
	
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Tutorial.MOD_ID);
	
	public static final RegistryObject<IRecipeSerializer<?>> MOD_SERIALIZER = RECIPE_SERIALIZERS.register("mod", () -> MOD_RECIPE_SERIALIZER);
	
	
	public static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T>
	{
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
	

	public static IRecipeType<IModRecipe> registerType(ResourceLocation recipeTypeId)
	{
		return Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
	}
}
