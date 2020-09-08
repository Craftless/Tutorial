package com.craftless.tutorial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.craftless.tutorial.entities.HogEntity;
import com.craftless.tutorial.init.ModBiomes;
import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.init.ModTileEntityTypes;
import com.craftless.tutorial.items.RawHogMeat;
import com.craftless.tutorial.util.ClientEventBusSubscriber;

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

@Mod("tutorial")
public class Tutorial 
{
    @SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger();
    
    public static final String MOD_ID = "tutorial";

    public Tutorial() {
    	

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);        


        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainerTypes.CONTAINERS_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBiomes.BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientEventBusSubscriber());
        MinecraftForge.EVENT_BUS.register(new RawHogMeat());

    }
    

    private void setup(final FMLCommonSetupEvent event) 
    {
    	DeferredWorkQueue.runLater(() -> {
    		GlobalEntityTypeAttributes.put(ModEntityTypes.HOG.get(), HogEntity.setCustomAttributes().create());
    	});
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
    
    
    
}
