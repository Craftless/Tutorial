package com.craftless.tutorial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.craftless.tutorial.entities.HogEntity;
import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModEntityTypes;
import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.items.RawHogMeat;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new WeaponAbilities());
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
    
    
    
}
