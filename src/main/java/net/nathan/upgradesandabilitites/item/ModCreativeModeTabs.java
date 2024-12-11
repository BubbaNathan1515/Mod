package net.nathan.upgradesandabilitites.item;

import net.nathan.upgradesandabilitites.UpgradesandAbilitites;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.upgradesandabilitites.block.ModBlocks;

public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UpgradesandAbilitites.MOD_ID);

//makes a new creative mode tab and adds the modded items and blocks to it
    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("upgradesandabilitites_tab", () -> CreativeModeTab.builder().icon(() ->
                new ItemStack(ModItems.GOLDBELL.get())).title(Component.translatable("creativetab.upgradesandabilitites_tab")).displayItems((pParameters, pOutput) ->
                    {

                        //modded items
                        pOutput.accept(ModItems.GOLDBELL.get());
                        pOutput.accept(ModItems.WALKINGCANE.get());

                        //modded block
                        pOutput.accept(ModBlocks.MODIF_TABLE.get());

                    })
                    .build());

//registers the tab to an eventbus, is called when the game first boots
    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}