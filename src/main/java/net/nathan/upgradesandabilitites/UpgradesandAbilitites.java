package net.nathan.upgradesandabilitites;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nathan.upgradesandabilitites.block.ModBlocks;
import net.nathan.upgradesandabilitites.block.gui.ModifScreen;
import net.nathan.upgradesandabilitites.item.ModCreativeModeTabs;
import net.nathan.upgradesandabilitites.item.ModItems;

import org.slf4j.Logger;

@Mod(UpgradesandAbilitites.MOD_ID)
public class UpgradesandAbilitites
{
    // The mod ID
    public static final String MOD_ID = "upgradesandabilitites";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public UpgradesandAbilitites()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // registers new items
        ModItems.register(modEventBus);
        // registers new blocks
        ModBlocks.register(modEventBus);

        // Registers items to the Vanilla creative tabs
        modEventBus.addListener(this::addCreative);

        // Registers items to the modded creative tabs
        ModCreativeModeTabs.register((modEventBus));

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModBlocks.MENU_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    // Adds the modded items to the tools and utils creative mode tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            event.accept(ModItems.GOLDBELL);
            event.accept(ModItems.WALKINGCANE);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() ->
            {
                MenuScreens.register(ModBlocks.MODIF_MENU_TYPE.get(), ModifScreen::new);
            });
        }
    }
}

