package net.nathan.upgradesandabilitites.block;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.upgradesandabilitites.block.custom.Modif_table;
import net.nathan.upgradesandabilitites.block.gui.Menu;
import net.nathan.upgradesandabilitites.item.ModItems;

import java.util.function.Supplier;

import static net.nathan.upgradesandabilitites.UpgradesandAbilitites.MOD_ID;


public class ModBlocks
{
    public static final DeferredRegister<Block>  BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final RegistryObject<Block> MODIF_TABLE = registerBlock("modif_table", () -> new Modif_table(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).sound(SoundType.WOOD)));



    //Registers the modded blocks
    private static <T extends  Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItems(name, toReturn);
        return  toReturn;
    }

    //associates a block to an item, so it can be placed
    private static <T extends Block>RegistryObject<Item> registerBlockItems(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    //This registers the menu for my custom GUI
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "upgradesandabilitites");

    //can create a new instance of menu
    public static Menu createMenu(int windowId, Inventory playerInventory, FriendlyByteBuf extraData)
    {
        Container container = new SimpleContainer(4);
        ContainerData data = new SimpleContainerData(3);
        return new Menu(windowId, playerInventory, container, data);
    }

    public static final RegistryObject<MenuType<Menu>> MODIF_MENU_TYPE = MENU_TYPES.register("modif_menu",
            () -> IForgeMenuType.create(ModBlocks::createMenu)); // Use IForgeMenuType.create


    //Another event bus, loads these assets when the game launches
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
        MENU_TYPES.register(eventBus);
    }

}
