package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.BlockItemBase;
import com.craftless.tutorial.items.CookedHogMeat;
import com.craftless.tutorial.items.CustomFuel;
import com.craftless.tutorial.items.Dorito;
import com.craftless.tutorial.items.ExplosiveStick;
import com.craftless.tutorial.items.ItemBase;
import com.craftless.tutorial.items.JarItem;
import com.craftless.tutorial.items.ModSpawnEggItem;
import com.craftless.tutorial.items.PoisonApple;
import com.craftless.tutorial.items.RawHogMeat;
import com.craftless.tutorial.util.enums.ModArmorMaterial;
import com.craftless.tutorial.util.enums.ModItemTier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
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
    
    //Tools		base: 4
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(ModItemTier.RUBY, 0, -2.8f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(ModItemTier.RUBY, 0.5f, -3.0f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(ModItemTier.RUBY, 5, -3.1f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(ModItemTier.RUBY, -3, -1.0f, new Item.Properties().group(Tutorial.TAB)));

    public static final RegistryObject<SwordItem> ASPECT_OF_THE_END = ITEMS.register("aspect_of_the_end", () -> new SwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    
    //Armor
    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_LEGGINS = ITEMS.register("ruby_leggings", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(Tutorial.TAB)));    
    
    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(ModBlocks.RUBY_BLOCK.get()));
    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore", () -> new BlockItemBase(ModBlocks.RUBY_ORE.get()));
    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItemBase(ModBlocks.TEST_BLOCK.get()));
    
    public static final RegistryObject<Item> QUARRY = ITEMS.register("quarry", () -> new BlockItemBase(ModBlocks.QUARRY.get()));
    public static final RegistryObject<Item> RUBY_CHEST = ITEMS.register("ruby_chest", () -> new BlockItemBase(ModBlocks.RUBY_CHEST.get()));
    public static final RegistryObject<Item> JAR_BLOCK = ITEMS.register("jar_block", () -> new BlockItemBase(ModBlocks.JAR_BLOCK.get()));
    
    public static final RegistryObject<Item> RUBY_STAIRS_ITEM = ITEMS.register("ruby_stairs", () -> new BlockItemBase(ModBlocks.RUBY_STAIRS.get()));
    public static final RegistryObject<Item> RUBY_FENCE_ITEM = ITEMS.register("ruby_fence", () -> new BlockItemBase(ModBlocks.RUBY_FENCE.get()));
    public static final RegistryObject<Item> RUBY_BUTTON_ITEM = ITEMS.register("ruby_button", () -> new BlockItemBase(ModBlocks.RUBY_BUTTON.get()));
    public static final RegistryObject<Item> RUBY_PRESSURE_PLATE_ITEM = ITEMS.register("ruby_pressure_plate", () -> new BlockItemBase(ModBlocks.RUBY_PRESSURE_PLATE.get()));
    public static final RegistryObject<Item> RUBY_SLAB = ITEMS.register("ruby_slab", () -> new BlockItemBase(ModBlocks.RUBY_SLAB.get()));
    
    public static final RegistryObject<Item> RUBY_SEEDS = ITEMS.register("ruby_seeds", () -> new BlockItem(ModBlocks.RUBY_CROP.get(), new Item.Properties().group(Tutorial.TAB).isBurnable().rarity(Rarity.UNCOMMON).food(new Food.Builder().fastToEat().hunger(1).saturation(1).build()).setNoRepair()));

    public static final RegistryObject<Item> RUBY_PLANKS_ITEM = ITEMS.register("ruby_planks", () -> new BlockItemBase(ModBlocks.RUBY_PLANKS.get()));
    public static final RegistryObject<Item> RUBY_LOG_ITEM = ITEMS.register("ruby_log", () -> new BlockItemBase(ModBlocks.RUBY_LOG.get()));
    public static final RegistryObject<Item> RUBY_LEAVES_ITEM = ITEMS.register("ruby_leaves", () -> new BlockItemBase(ModBlocks.RUBY_LEAVES.get()));
    public static final RegistryObject<Item> RUBY_SAPLING_ITEM = ITEMS.register("ruby_sapling", () -> new BlockItemBase(ModBlocks.RUBY_SAPLING.get()));
    public static final RegistryObject<Item> RUBY_CROP_ITEM = ITEMS.register("ruby_crop", () -> new BlockItemBase(ModBlocks.RUBY_CROP.get()));
    public static final RegistryObject<Item> RUBY_DOOR_ITEM = ITEMS.register("ruby_door", () -> new BlockItemBase(ModBlocks.RUBY_DOOR.get()));
    
    public static final RegistryObject<Item> ITEM_PEDESTAL_ITEM = ITEMS.register("item_pedestal", () -> new BlockItemBase(ModBlocks.ITEM_PEDESTAL.get()));
    
}
