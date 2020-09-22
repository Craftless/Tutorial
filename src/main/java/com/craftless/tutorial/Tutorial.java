package com.craftless.tutorial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.craftless.tutorial.client.ColorHandler;
import com.craftless.tutorial.entities.HogEntity;
import com.craftless.tutorial.init.ModBiomes;
import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.init.ModEnchantments;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModFluids;
import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.init.ModParticles;
import com.craftless.tutorial.init.ModSounds;
import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.items.RawHogMeat;
import com.craftless.tutorial.util.ClientEventBusSubscriber;

import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("deprecation")
@Mod("tutorial")
public class Tutorial 
{
    @SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger();
    
    public static final String MOD_ID = "tutorial";

    public Tutorial() {
    	

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandler::registerItemColor);

        ModSounds.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModEnchantments.ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModFluids.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainerTypes.CONTAINERS_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModParticles.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        ModBiomes.BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientEventBusSubscriber());
        MinecraftForge.EVENT_BUS.register(new RawHogMeat());

    }
    

    @SuppressWarnings("deprecation")
	private void setup(final FMLCommonSetupEvent event) 
    {
    	DeferredWorkQueue.runLater(() -> {
    		GlobalEntityTypeAttributes.put(ModEntityTypes.HOG.get(), HogEntity.setCustomAttributes().create());
    	});
    	
    	ComposterBlock.registerCompostable(0.4f, ModBlocks.RUBY_SAPLING.get());
    	ComposterBlock.registerCompostable(0.6f, ModBlocks.RUBY_LEAVES.get());
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) {}

    public static final ItemGroup TAB = new ItemGroup("tutorialTab")
	{
    	@Override
    	public ItemStack createIcon()
    	{
    		return new ItemStack(ModItems.RUBY.get());
    	}
	};
	
	@SubscribeEvent
	public static void onRegisterBiomes(final RegistryEvent.Register<Biome> e)
	{
		ModBiomes.RegisterBiomes();
	}
	
//	@SubscribeEvent
//	public static void onRegisterItems(final RegistryEvent.Register<Item> event) 
//	{
//		final IForgeRegistry<Item> registry = event.getRegistry();
//
//		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !(block instanceof CustomCrop)).forEach(block -> {
//					final Item.Properties properties = new Item.Properties().group(Tutorial.TAB);
//					final BlockItem blockItem = new BlockItem(block, properties);
//					blockItem.setRegistryName(block.getRegistryName());
//					registry.register(blockItem);
//				});
//
//		LOGGER.debug("Registered BlockItems!");
//	}
    
    
    
}
