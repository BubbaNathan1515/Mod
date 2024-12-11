package net.nathan.upgradesandabilitites.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nathan.upgradesandabilitites.UpgradesandAbilitites;
import net.nathan.upgradesandabilitites.item.custom.Cane;
import net.nathan.upgradesandabilitites.item.custom.GoldBellItem;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UpgradesandAbilitites.MOD_ID);

    //registers the modded items
    public static final RegistryObject<Item> GOLDBELL = ITEMS.register("goldbell", () -> new GoldBellItem(new Item.Properties()));
    public static final RegistryObject<Item> WALKINGCANE = ITEMS.register("walkingcane", () -> new Cane(new Item.Properties()));

//registers the items to an eventbus, is called when the game first boots
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
