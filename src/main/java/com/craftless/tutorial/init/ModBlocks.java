package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.BlockQuarry;
import com.craftless.tutorial.blocks.ModButtonBlock;
import com.craftless.tutorial.blocks.ModPressurePlateBlock;
import com.craftless.tutorial.blocks.RubyBlock;
import com.craftless.tutorial.blocks.RubyChestBlock;
import com.craftless.tutorial.blocks.RubyOre;
import com.craftless.tutorial.blocks.TestBlock;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.StairsBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Tutorial.MOD_ID);

    //Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore", RubyOre::new);
    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", TestBlock::new);
    
    public static final RegistryObject<Block> RUBY_STAIRS = BLOCKS.register("ruby_stairs", () -> new StairsBlock(() -> RUBY_BLOCK.get().getDefaultState(), Block.Properties.create(ModBlocks.RUBY_BLOCK.get().getDefaultState().getMaterial(), ModBlocks.RUBY_BLOCK.get().getMaterialColor()).hardnessAndResistance(5, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_FENCE = BLOCKS.register("ruby_fence", () -> new FenceBlock(Block.Properties.create(ModBlocks.RUBY_BLOCK.get().getDefaultState().getMaterial(), ModBlocks.RUBY_BLOCK.get().getMaterialColor()).hardnessAndResistance(5, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_BUTTON = BLOCKS.register("ruby_button", () -> new ModButtonBlock(Block.Properties.create(ModBlocks.RUBY_BLOCK.get().getDefaultState().getMaterial(), ModBlocks.RUBY_BLOCK.get().getMaterialColor()).hardnessAndResistance(2, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_PRESSURE_PLATE = BLOCKS.register("ruby_pressure_plate", () -> new ModPressurePlateBlock(Sensitivity.MOBS, Block.Properties.create(ModBlocks.RUBY_BLOCK.get().getDefaultState().getMaterial(), ModBlocks.RUBY_BLOCK.get().getMaterialColor()).hardnessAndResistance(2, 5).harvestLevel(0).setRequiresTool()));

    
    //Tile Entity
    public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", BlockQuarry::new);

    public static final RegistryObject<Block> RUBY_CHEST = BLOCKS.register("ruby_chest", () -> new RubyChestBlock(Block.Properties.from(RUBY_BLOCK.get())));
    
}
