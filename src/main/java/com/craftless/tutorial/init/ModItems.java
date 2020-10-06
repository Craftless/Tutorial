package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.BlockItemBase;
import com.craftless.tutorial.entities.EnderArrowEntity;
import com.craftless.tutorial.entities.ExplosiveArrowEntity;
import com.craftless.tutorial.entities.LightningBoltArrowEntity;
import com.craftless.tutorial.entities.LightningStormArrowEntity;
import com.craftless.tutorial.entities.MiningArrowEntity;
import com.craftless.tutorial.entities.TNTArrowEntity;
import com.craftless.tutorial.items.AirSwordItem;
import com.craftless.tutorial.items.AspectOfTheEnd;
import com.craftless.tutorial.items.BridgeEggItem;
import com.craftless.tutorial.items.CookedHogMeat;
import com.craftless.tutorial.items.CustomFuel;
import com.craftless.tutorial.items.Dorito;
import com.craftless.tutorial.items.ExplosiveCactusItem;
import com.craftless.tutorial.items.ExplosiveStick;
import com.craftless.tutorial.items.FireballWand;
import com.craftless.tutorial.items.FreezingSwordItem;
import com.craftless.tutorial.items.Gun;
import com.craftless.tutorial.items.InvisibleLightSourceRemover;
import com.craftless.tutorial.items.ItemBase;
import com.craftless.tutorial.items.JarItem;
import com.craftless.tutorial.items.LightningSwordItem;
import com.craftless.tutorial.items.ModArrowItem;
import com.craftless.tutorial.items.ModMusicDiscItem;
import com.craftless.tutorial.items.ModSpawnEggItem;
import com.craftless.tutorial.items.PoisonApple;
import com.craftless.tutorial.items.RawHogMeat;
import com.craftless.tutorial.util.DiggingToolItem;
import com.craftless.tutorial.util.enums.ModArmorMaterial;
import com.craftless.tutorial.util.enums.ModItemTier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tutorial.MOD_ID);
    
    
    //Items
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", ItemBase::new);
    public static final RegistryObject<PoisonApple> POISON_APPLE = ITEMS.register("poison_apple", PoisonApple::new);
    public static final RegistryObject<RawHogMeat> RAW_HOG_MEAT = ITEMS.register("raw_hog_meat", RawHogMeat::new);
    public static final RegistryObject<CookedHogMeat> COOKED_HOG_MEAT = ITEMS.register("cooked_hog_meat", CookedHogMeat::new);
    public static final RegistryObject<ExplosiveStick> EXPLOSIVE_STICK = ITEMS.register("explosive_stick", ExplosiveStick::new);
    public static final RegistryObject<ModSpawnEggItem> HOG_SPAWN_EGG = ITEMS.register("hog_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.HOG, 0xF1BF7B, 0x705128, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<CustomFuel> HOG_HIDE = ITEMS.register("hog_hide", () -> new CustomFuel(new Item.Properties().group(Tutorial.TAB), 20 * 50));
    public static final RegistryObject<JarItem> JAR = ITEMS.register("jar", JarItem::new);
    public static final RegistryObject<Item> CRYSTAL = ITEMS.register("crystal", () -> new ItemBase(new Item.Properties().maxStackSize(4)));
    public static final RegistryObject<Item> DORITO = ITEMS.register("dorito", Dorito::new);
    public static final RegistryObject<Item> DON_DISC = ITEMS.register("music_disc_don", () -> new ModMusicDiscItem(5, () -> ModSounds.LAZY_DON.get(), new Item.Properties().group(Tutorial.TAB).maxStackSize(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new ItemBase(new Item.Properties().group(Tutorial.TAB).food(new Food.Builder().fastToEat().hunger(4).saturation(2).build())));
    public static final RegistryObject<Item> MILK_BUCKET = ITEMS.register("milk_bucket", () -> new BucketItem(() -> ModFluids.MILK_FLUID.get(), new Item.Properties().group(Tutorial.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> NIGHT_VISION_RING = ITEMS.register("night_vision_ring", ItemBase::new);
    public static final RegistryObject<Item> FIREBALL_WAND = ITEMS.register("fireball_wand", FireballWand::new);
    public static final RegistryObject<Item> GUN = ITEMS.register("gun", Gun::new);
    public static final RegistryObject<Item> BRIDGE_EGG = ITEMS.register("bridge_egg", BridgeEggItem::new);
    public static final RegistryObject<Item> QUARRY_PIECE = ITEMS.register("quarry_piece", ItemBase::new);
    public static final RegistryObject<Item> INVISIBLE_LIGHT_SOURCE_REMOVER = ITEMS.register("invisible_light_source_remover", InvisibleLightSourceRemover::new);
    public static final RegistryObject<Item> EXPLOSIVE_CACTUS = ITEMS.register("explosive_cactus", () -> new ExplosiveCactusItem(new Item.Properties().group(Tutorial.TAB)));
    
    
    public static final RegistryObject<ModArrowItem> EXPLOSIVE_ARROW_ITEM = ITEMS.register("explosive_arrow", () -> new ModArrowItem(new Item.Properties().group(Tutorial.TAB), ExplosiveArrowEntity.class).setInfinity(true));
    public static final RegistryObject<ModArrowItem> LIGHTNING_BOLT_ARROW_ITEM = ITEMS.register("lightning_bolt_arrow", () -> new ModArrowItem(new Item.Properties().group(Tutorial.TAB), LightningBoltArrowEntity.class).setInfinity(true));
    public static final RegistryObject<ModArrowItem> LIGHTNING_STORM_ARROW_ITEM = ITEMS.register("lightning_storm_arrow", () -> new ModArrowItem(new Item.Properties().group(Tutorial.TAB), LightningStormArrowEntity.class).setInfinity(false));
    public static final RegistryObject<ModArrowItem> TNT_ARROW_ITEM = ITEMS.register("tnt_arrow", () -> new ModArrowItem(new Item.Properties().group(Tutorial.TAB), TNTArrowEntity.class).setInfinity(false));
    public static final RegistryObject<ModArrowItem> MINING_ARROW_ITEM = ITEMS.register("mining_arrow", () -> new ModArrowItem(new Item.Properties().group(Tutorial.TAB), MiningArrowEntity.class).setInfinity(false));
    public static final RegistryObject<ModArrowItem> ENDER_ARROW_ITEM = ITEMS.register("ender_arrow", () -> new ModArrowItem(new Item.Properties().group(Tutorial.TAB), EnderArrowEntity.class).setInfinity(true));
    
    //Tools		base: 4
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(ModItemTier.RUBY, 0, -2.8f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(ModItemTier.RUBY, 0.5f, -3.0f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(ModItemTier.RUBY, 8, -3.5f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(ModItemTier.RUBY, -3, -1.0f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<DiggingToolItem> RUBY_DIGGING_TOOL = ITEMS.register("ruby_digging_tool", () -> new DiggingToolItem(ModItemTier.RUBY, 4, -3, new Item.Properties().group(Tutorial.TAB)));
    
    public static final RegistryObject<SwordItem> RUBY_DAGGER = ITEMS.register("ruby_dagger", () -> new SwordItem(ModItemTier.RUBY, 3, -2f, new Item.Properties().group(Tutorial.TAB)));

    public static final RegistryObject<SwordItem> ASPECT_OF_THE_END = ITEMS.register("aspect_of_the_end", () -> new AspectOfTheEnd(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    
    public static final RegistryObject<SwordItem> LIGHTNING_SWORD = ITEMS.register("lightning_sword", () -> new LightningSwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<SwordItem> FREEZING_SWORD = ITEMS.register("freezing_sword", () -> new FreezingSwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<SwordItem> AIR_SWORD = ITEMS.register("air_sword", () -> new AirSwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    
    //Armor
    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_LEGGINS = ITEMS.register("ruby_leggings", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(Tutorial.TAB)));    
    
    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(ModBlocks.RUBY_BLOCK.get()));
    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore", () -> new BlockItemBase(ModBlocks.RUBY_ORE.get()));
    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItemBase(ModBlocks.TEST_BLOCK.get()));
    public static final RegistryObject<Item> WHITE_BLOCK_ITEM = ITEMS.register("white_block", () -> new BlockItemBase(ModBlocks.WHITE_BLOCK.get()));
    public static final RegistryObject<Item> INVISIBLE_LIGHT_SOURCE_ITEM = ITEMS.register("invisible_light_source", () -> new BlockItemBase(ModBlocks.INVISIBLE_LIGHT_SOURCE.get()));
    
    public static final RegistryObject<Item> QUARRY = ITEMS.register("quarry", () -> new BlockItemBase(ModBlocks.QUARRY.get()));
    public static final RegistryObject<Item> RUBY_CHEST = ITEMS.register("ruby_chest", () -> new BlockItemBase(ModBlocks.RUBY_CHEST.get()));
    public static final RegistryObject<Item> JAR_BLOCK = ITEMS.register("jar_block", () -> new BlockItemBase(ModBlocks.JAR_BLOCK.get()));
    
    public static final RegistryObject<Item> RUBY_STAIRS_ITEM = ITEMS.register("ruby_stairs", () -> new BlockItemBase(ModBlocks.RUBY_STAIRS.get()));
    public static final RegistryObject<Item> RUBY_FENCE_ITEM = ITEMS.register("ruby_fence", () -> new BlockItemBase(ModBlocks.RUBY_FENCE.get()));
    public static final RegistryObject<Item> RUBY_BUTTON_ITEM = ITEMS.register("ruby_button", () -> new BlockItemBase(ModBlocks.RUBY_BUTTON.get()));
    public static final RegistryObject<Item> RUBY_PRESSURE_PLATE_ITEM = ITEMS.register("ruby_pressure_plate", () -> new BlockItemBase(ModBlocks.RUBY_PRESSURE_PLATE.get()));
    public static final RegistryObject<Item> RUBY_SLAB = ITEMS.register("ruby_slab", () -> new BlockItemBase(ModBlocks.RUBY_SLAB.get()));
    
    public static final RegistryObject<Item> RUBY_SEEDS = ITEMS.register("ruby_seeds", () -> new BlockItem(ModBlocks.RUBY_CROP.get(), new Item.Properties().group(Tutorial.TAB).isBurnable().rarity(Rarity.UNCOMMON).food(new Food.Builder().fastToEat().hunger(1).saturation(1).build()).setNoRepair()));
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds", () -> new BlockItem(ModBlocks.CORN_CROP.get(), new Item.Properties().group(Tutorial.TAB).isBurnable().rarity(Rarity.COMMON).food(new Food.Builder().fastToEat().hunger(2).saturation(2).build())));

    public static final RegistryObject<Item> RUBY_PLANKS_ITEM = ITEMS.register("ruby_planks", () -> new BlockItemBase(ModBlocks.RUBY_PLANKS.get()));
    public static final RegistryObject<Item> RUBY_LOG_ITEM = ITEMS.register("ruby_log", () -> new BlockItemBase(ModBlocks.RUBY_LOG.get()));
    public static final RegistryObject<Item> RUBY_LEAVES_ITEM = ITEMS.register("ruby_leaves", () -> new BlockItemBase(ModBlocks.RUBY_LEAVES.get()));
    public static final RegistryObject<Item> RUBY_SAPLING_ITEM = ITEMS.register("ruby_sapling", () -> new BlockItemBase(ModBlocks.RUBY_SAPLING.get()));
    public static final RegistryObject<Item> RUBY_CROP_ITEM = ITEMS.register("ruby_crop", () -> new BlockItemBase(ModBlocks.RUBY_CROP.get()));
    public static final RegistryObject<Item> RUBY_DOOR_ITEM = ITEMS.register("ruby_door", () -> new BlockItemBase(ModBlocks.RUBY_DOOR.get()));
    public static final RegistryObject<Item> RUBY_FENCE_GATE_ITEM = ITEMS.register("ruby_fence_gate", () -> new BlockItemBase(ModBlocks.RUBY_FENCE_GATE.get()));
    public static final RegistryObject<Item> RUBY_WALL_ITEM = ITEMS.register("ruby_wall", () -> new BlockItemBase(ModBlocks.RUBY_WALL.get()));
    public static final RegistryObject<Item> RUBY_TORCH_ITEM = ITEMS.register("ruby_torch", () -> new WallOrFloorItem(ModBlocks.RUBY_TORCH_BLOCK.get(), ModBlocks.RUBY_WALL_TORCH_BLOCK.get(), new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<Item> RUBY_LADDER_ITEM = ITEMS.register("ruby_ladder", () -> new BlockItemBase(ModBlocks.RUBY_LADDER_BLOCK.get()));
    
    public static final RegistryObject<Item> ITEM_PEDESTAL_ITEM = ITEMS.register("item_pedestal", () -> new BlockItemBase(ModBlocks.ITEM_PEDESTAL.get()));
    
}
