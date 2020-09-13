package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.BlockQuarry;
import com.craftless.tutorial.blocks.CustomCrop;
import com.craftless.tutorial.blocks.CustomDoor;
import com.craftless.tutorial.blocks.CustomSaplingBlock;
import com.craftless.tutorial.blocks.ModButtonBlock;
import com.craftless.tutorial.blocks.ModPressurePlateBlock;
import com.craftless.tutorial.blocks.RubyBlock;
import com.craftless.tutorial.blocks.RubyChestBlock;
import com.craftless.tutorial.blocks.RubyOre;
import com.craftless.tutorial.blocks.TestBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
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
    public static final RegistryObject<Block> RUBY_PLANKS = BLOCKS.register("ruby_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> RUBY_LOG = BLOCKS.register("ruby_log", () -> createLogBlock(MaterialColor.WOOD, RUBY_BLOCK.get().getMaterialColor()));
    public static final RegistryObject<Block> RUBY_LEAVES = BLOCKS.register("ruby_leaves", () -> new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> RUBY_SAPLING = BLOCKS.register("ruby_sapling", () -> new CustomSaplingBlock(null, Block.Properties.from(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> RUBY_CROP = BLOCKS.register("ruby_crop", () -> new CustomCrop(Block.Properties.from(Blocks.WHEAT)));
    public static final RegistryObject<Block> RUBY_DOOR = BLOCKS.register("ruby_door", () -> new CustomDoor(Block.Properties.from(Blocks.OAK_DOOR)));
    
    public static final RegistryObject<Block> RUBY_STAIRS = BLOCKS.register("ruby_stairs", () -> new StairsBlock(() -> RUBY_BLOCK.get().getDefaultState(), Block.Properties.create(ModBlocks.RUBY_PLANKS.get().getDefaultState().getMaterial(), ModBlocks.RUBY_PLANKS.get().getMaterialColor()).hardnessAndResistance(5, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_FENCE = BLOCKS.register("ruby_fence", () -> new FenceBlock(Block.Properties.create(ModBlocks.RUBY_PLANKS.get().getDefaultState().getMaterial(), ModBlocks.RUBY_PLANKS.get().getMaterialColor()).hardnessAndResistance(5, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_BUTTON = BLOCKS.register("ruby_button", () -> new ModButtonBlock(Block.Properties.create(ModBlocks.RUBY_PLANKS.get().getDefaultState().getMaterial(), ModBlocks.RUBY_PLANKS.get().getMaterialColor()).hardnessAndResistance(2, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_PRESSURE_PLATE = BLOCKS.register("ruby_pressure_plate", () -> new ModPressurePlateBlock(Sensitivity.MOBS, Block.Properties.create(ModBlocks.RUBY_BLOCK.get().getDefaultState().getMaterial(), ModBlocks.RUBY_PLANKS.get().getMaterialColor()).hardnessAndResistance(2, 5).harvestLevel(0).setRequiresTool()));
    public static final RegistryObject<Block> RUBY_SLAB = BLOCKS.register("ruby_slab", () -> new SlabBlock(Block.Properties.from(ModBlocks.RUBY_PLANKS.get())));
    
    
    
    //Tile Entity
    public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", BlockQuarry::new);

    public static final RegistryObject<Block> RUBY_CHEST = BLOCKS.register("ruby_chest", () -> new RubyChestBlock(Block.Properties.from(RUBY_BLOCK.get())));
    
    private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (p_235431_2_) -> {
           return p_235431_2_.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
        }).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
     }
    
    
    
}
