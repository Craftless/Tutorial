package com.craftless.tutorial.init;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.blocks.BlockItemBase;
import com.craftless.tutorial.items.CookedHogMeat;
import com.craftless.tutorial.items.CustomFuel;
import com.craftless.tutorial.items.ExplosiveStick;
import com.craftless.tutorial.items.ItemBase;
import com.craftless.tutorial.items.ModSpawnEggItem;
import com.craftless.tutorial.items.PoisonApple;
import com.craftless.tutorial.items.RawHogMeat;
import com.craftless.tutorial.util.enums.ModArmorMaterial;
import com.craftless.tutorial.util.enums.ModItemTier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
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

    

    //Tools		base: 4
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(ModItemTier.RUBY, 4, -2.4f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(ModItemTier.RUBY, 0, -2.8f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(ModItemTier.RUBY, 0.5f, -3.0f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(ModItemTier.RUBY, 5, -3.1f, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(ModItemTier.RUBY, -3, -1.0f, new Item.Properties().group(Tutorial.TAB)));

    //Armor
    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_LEGGINS = ITEMS.register("ruby_leggings", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(Tutorial.TAB)));
    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(ModArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(Tutorial.TAB)));

    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(ModBlocks.RUBY_BLOCK.get()));
    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore", () -> new BlockItemBase(ModBlocks.RUBY_ORE.get()));
    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItemBase(ModBlocks.TEST_BLOCK.get()));

}
