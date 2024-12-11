package net.nathan.upgradesandabilitites.block.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.nathan.upgradesandabilitites.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;


public class Menu extends AbstractContainerMenu
{
    private final ContainerData data;
    public  final Container container;
    //didnt feel like trying to import this to Screen, that's why you only see it used here
    private final List<Slot> guiSlots = new ArrayList<>();

    public Menu(int id, Inventory plyInv, Container container, ContainerData data)
    {
        super(ModBlocks.MODIF_MENU_TYPE.get(), id); //once again verifies properties
        this.data = (data != null) ? data : new SimpleContainerData(3); // Default data
        this.container = (container != null) ? container : new SimpleContainer(4); // Default container

        // Adds 4 item slots to the custom container (or GUI)
        for (int y = 0; y < 4; y++) {
            int x = 80 + (y * 18);
            this.guiSlots.add(this.addSlot(new Slot(this.container, y, x, 47)));
        }
            //These two have to be added in order to see your inventory items, otherwise you can't place in items
        // Player's inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(plyInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar's slots
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(plyInv, col, 8 + col * 18, 142));
        }
    }



    //This dictates where an item goes if the player Shift-clicks the item
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex)
    {
        Slot slot = this.slots.get(pIndex);
        ItemStack itemStack3 = ItemStack.EMPTY;

        if(pPlayer != null && slot.hasItem() && slot != null)
        {
            ItemStack itemStack = slot.getItem();
            itemStack3 = itemStack;

            //sends the shift clicked item FROM the GUI TO the PLAYER INVENTORY
            if (pIndex < 4)
            {
                if (!this.moveItemStackTo(itemStack, 4, 40, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            //sends shift clicked items FROM the PLAYER INVENTORY TO the GUI
            if (pIndex >= 4 && pIndex <= 40)
            {
                if (!this.moveItemStackTo(itemStack, 0, 4, false))
                {
                    return ItemStack.EMPTY;
                }
            }
        }
        return itemStack3;
    }

    //This is the receiving end of collecting data from Screen.java to sync Clientside (Screen) to Serverside (Menu)
    //This took me a while to do too, looks like I personally have an issue with syncing
    @Override
    public void slotsChanged(Container pContainer)
    {
        super.slotsChanged(pContainer);
    }

    //checks player validity
    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }
}
