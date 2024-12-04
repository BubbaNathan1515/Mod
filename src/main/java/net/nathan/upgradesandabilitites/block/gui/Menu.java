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


public class Menu extends AbstractContainerMenu
{
    private final ContainerData data;

    public Menu(int id, Inventory plyInv, Container container, ContainerData data) {
        super(ModBlocks.MODIF_MENU_TYPE.get(), id);
        System.out.println("Menu 1");
        this.data = (data != null) ? data : new SimpleContainerData(3); // Default data
        System.out.println("Menu 2");
        container = (container != null) ? container : new SimpleContainer(4); // Default container
        System.out.println("Menu 3");

        //adds 4 item slots
        for(int y = 0; y <4; y++)
        {
            System.out.println("Menu 4");

            int x = 154+(y*18);
            this.addSlot(new Slot(container,y,x,99));
            System.out.println("Menu 5");

        }
        System.out.println("Menu 6");

        //Player's inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(plyInv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }
        System.out.println("Menu 7");

        //Hotbar's slots
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(plyInv, col, 8 + col * 18, 142));
        }
        System.out.println("Menu 8");
    }



    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex)
    {
        System.out.println("Menu 9");
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        System.out.println("Menu 10");
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            System.out.println("Menu 11");
            if (pIndex < 4) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemstack1, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        System.out.println("Menu 12");
        return itemstack;
    }


    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

}
