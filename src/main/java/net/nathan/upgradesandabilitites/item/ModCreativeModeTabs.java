package net.nathan.upgradesandabilitites.item;

import net.nathan.upgradesandabilitites.UpgradesandAbilitites;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.upgradesandabilitites.UpgradesandAbilitites;
import net.nathan.upgradesandabilitites.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UpgradesandAbilitites.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("upgradesandabilitites_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GOLDBELL.get()))
                    .title(Component.translatable("creativetab.upgradesandabilitites_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.GOLDBELL.get());
                        pOutput.accept(ModItems.WALKINGCANE.get());

                        pOutput.accept(ModBlocks.MODIF_TABLE.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}