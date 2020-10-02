package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.tileentities.ItemPedestalTileEntity;
import com.craftless.tutorial.tileentities.JarBlockTileEntity;
import com.craftless.tutorial.tileentities.QuarryTileEntity;
import com.craftless.tutorial.tileentities.RubyChestTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes 
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Tutorial.MOD_ID);

    public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = TILE_ENTITY_TYPES.register("quarry", () -> TileEntityType.Builder.create(QuarryTileEntity::new, ModBlocks.QUARRY.get()).build(null));
    public static final RegistryObject<TileEntityType<RubyChestTileEntity>> RUBY_CHEST = TILE_ENTITY_TYPES.register("ruby_chest", () -> TileEntityType.Builder.create(RubyChestTileEntity::new, ModBlocks.RUBY_CHEST.get()).build(null));
    public static final RegistryObject<TileEntityType<JarBlockTileEntity>> JAR_BLOCK = TILE_ENTITY_TYPES.register("jar_block", () -> TileEntityType.Builder.create(JarBlockTileEntity::new, ModBlocks.JAR_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL = TILE_ENTITY_TYPES.register("item_pedestal", () -> TileEntityType.Builder.create(ItemPedestalTileEntity::new, ModBlocks.ITEM_PEDESTAL.get()).build(null));
    //public static final RegistryObject<TileEntityType<ModFurnaceTileEntity>> MOD_FURNACE = TILE_ENTITY_TYPES.register("mod_furnace", () -> TileEntityType.Builder.create(ModFurnaceTileEntity::new, ModBlocks.MOD_FURNACE.get()).build(null));

}
