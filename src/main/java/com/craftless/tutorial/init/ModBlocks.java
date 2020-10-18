package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.BlockQuarry;
import com.craftless.tutorial.blocks.CornCrop;
import com.craftless.tutorial.blocks.CustomDoor;
import com.craftless.tutorial.blocks.CustomSaplingBlock;
import com.craftless.tutorial.blocks.InvisibleLightSourceBlock;
import com.craftless.tutorial.blocks.ItemPedestalBlock;
import com.craftless.tutorial.blocks.JarBlock;
import com.craftless.tutorial.blocks.ModButtonBlock;
import com.craftless.tutorial.blocks.ModPressurePlateBlock;
import com.craftless.tutorial.blocks.ModTNTBlock;
import com.craftless.tutorial.blocks.RubyBlock;
import com.craftless.tutorial.blocks.RubyChestBlock;
import com.craftless.tutorial.blocks.RubyCrop;
import com.craftless.tutorial.blocks.RubyOre;
import com.craftless.tutorial.blocks.TestBlock;
import com.craftless.tutorial.entities.FiveTimesTNTEntity;
import com.craftless.tutorial.entities.LavaTNTEntity;
import com.craftless.tutorial.entities.LightningStormTNTEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
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
    public static final RegistryObject<Block> RUBY_CROP = BLOCKS.register("ruby_crop", () -> new RubyCrop(Block.Properties.from(Blocks.WHEAT)));
    public static final RegistryObject<Block> CORN_CROP = BLOCKS.register("corn_crop", () -> new CornCrop(Block.Properties.from(Blocks.WHEAT)));
    public static final RegistryObject<Block> RUBY_DOOR = BLOCKS.register("ruby_door", () -> new CustomDoor(Block.Properties.from(Blocks.OAK_DOOR)));
    public static final RegistryObject<Block> WHITE_BLOCK = BLOCKS.register("white_block", () -> new Block(Block.Properties.create(Material.ROCK).setLightLevel((p_235464_0_) -> {return 10;}).jumpFactor(2).slipperiness(2)));
    public static final RegistryObject<Block> INVISIBLE_LIGHT_SOURCE = BLOCKS.register("invisible_light_source", () -> new InvisibleLightSourceBlock(Block.Properties.create(Material.AIR).noDrops().doesNotBlockMovement()));
    public static final RegistryObject<Block> FIVE_TIMES_TNT_BLOCK = BLOCKS.register("five_times_tnt", () -> new ModTNTBlock(Block.Properties.from(Blocks.TNT), FiveTimesTNTEntity.class));
    public static final RegistryObject<Block> LIGHTNING_STORM_TNT_BLOCK = BLOCKS.register("lightning_storm_tnt", () -> new ModTNTBlock(Block.Properties.from(Blocks.TNT), LightningStormTNTEntity.class));
    public static final RegistryObject<Block> LAVA_TNT_BLOCK = BLOCKS.register("lava_tnt", () -> new ModTNTBlock(Block.Properties.from(Blocks.TNT), LavaTNTEntity.class));
    
    public static final RegistryObject<Block> RUBY_STAIRS = BLOCKS.register("ruby_stairs", () -> new StairsBlock(() -> RUBY_BLOCK.get().getDefaultState(), Block.Properties.from(ModBlocks.RUBY_PLANKS.get())));
    public static final RegistryObject<Block> RUBY_FENCE = BLOCKS.register("ruby_fence", () -> new FenceBlock(Block.Properties.from(ModBlocks.RUBY_PLANKS.get())));
    public static final RegistryObject<Block> RUBY_BUTTON = BLOCKS.register("ruby_button", () -> new ModButtonBlock(Block.Properties.from(ModBlocks.RUBY_PLANKS.get())));
    public static final RegistryObject<Block> RUBY_PRESSURE_PLATE = BLOCKS.register("ruby_pressure_plate", () -> new ModPressurePlateBlock(Sensitivity.MOBS, Block.Properties.from(ModBlocks.RUBY_PLANKS.get())));
    public static final RegistryObject<Block> RUBY_SLAB = BLOCKS.register("ruby_slab", () -> new SlabBlock(Block.Properties.from(ModBlocks.RUBY_PLANKS.get())));
    public static final RegistryObject<FenceGateBlock> RUBY_FENCE_GATE = BLOCKS.register("ruby_fence_gate", () -> new FenceGateBlock(Block.Properties.from(Blocks.OAK_FENCE_GATE))); 
    public static final RegistryObject<WallBlock> RUBY_WALL = BLOCKS.register("ruby_wall", () -> new WallBlock(Block.Properties.from(Blocks.BRICK_WALL)));
    public static final RegistryObject<TorchBlock> RUBY_TORCH_BLOCK = BLOCKS.register("ruby_torch_block", () -> new TorchBlock(Block.Properties.from(Blocks.TORCH), ParticleTypes.ASH));
    public static final RegistryObject<WallTorchBlock> RUBY_WALL_TORCH_BLOCK = BLOCKS.register("ruby_wall_torch_block", () -> new WallTorchBlock(Block.Properties.from(Blocks.WALL_TORCH), ParticleTypes.FALLING_WATER));
    public static final RegistryObject<LadderBlock> RUBY_LADDER_BLOCK = BLOCKS.register("ruby_ladder", () -> new LadderBlock(Block.Properties.from(Blocks.LADDER)));
    
    //Tile Entity
    public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", BlockQuarry::new);
    public static final RegistryObject<Block> RUBY_CHEST = BLOCKS.register("ruby_chest", () -> new RubyChestBlock(Block.Properties.from(RUBY_BLOCK.get())));
    public static final RegistryObject<Block> JAR_BLOCK = BLOCKS.register("jar_block",  JarBlock::new);
    public static final RegistryObject<Block> ITEM_PEDESTAL = BLOCKS.register("item_pedestal", () -> new ItemPedestalBlock(Block.Properties.from(Blocks.ANVIL)));
    //public static final RegistryObject<Block> MOD_FURNACE = BLOCKS.register("mod_furnace", () -> new ModFurnaceBlock(Block.Properties.from(Blocks.ANVIL)));
    
    private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (p_235431_2_) -> {
           return p_235431_2_.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
        }).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
     }
    
    
    
}
