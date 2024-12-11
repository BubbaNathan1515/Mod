package net.nathan.upgradesandabilitites.block.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.nathan.upgradesandabilitites.block.ModBlocks;
import net.nathan.upgradesandabilitites.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.nathan.upgradesandabilitites.UpgradesandAbilitites.MOD_ID;


public class Screen extends AbstractContainerScreen<Menu>
{
    private boolean showReq = false;
    private boolean materialBell;
    private boolean materialCane;
    private Integer[] amount;
    private Item[] materials;
    Container thiscontainer = menu.container;
    Menu customMenu;


    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/gui_modif_table.png");

    public Screen(Menu container, Inventory plyInv, Component component)
    {
        super(container, plyInv, component);
        this.imageWidth = 175;
        this.imageHeight = 165;
        customMenu = container;
    }

    Button topButton;
    Button bottomButton;
    Button confirmButton;
    ItemStack output;
    Player player = Minecraft.getInstance().player;
    @Override
    protected void init()
    {
        super.init();
        //Side Buttons
        //top left corner
        int x = 15;
        int y = 12;
        //W and H of button texture
        int height = 23;
        int width = 23;

        topButton = Button.builder(Component.literal(""), this::topButton)
                .pos(this.leftPos+x,this.topPos+y) //position
                .size(width, height) //size of the button
                .build(); //takes in those properties and builds it
        this.addRenderableWidget(topButton);  // renders the button to the screen
        y=49;
        bottomButton = Button.builder(Component.literal(""), this::bottomButton)
                .pos(this.leftPos+x,this.topPos+y)
                .size(width, height)
                .build();
        this.addRenderableWidget(bottomButton);  // renders the button to the screen

        x=63;
        y=10;
        width=42;
        height=18;

        //Confirmation button
        System.out.println("4");
        confirmButton = Button.builder(Component.literal(""), this::confirmButton)
                .pos(this.leftPos+x,this.topPos+y)
                .size(width, height)
                .build();
            this.addRenderableWidget(confirmButton);  // renders the button to the screen
    }

    //these two activate when their respective buttons were pushed
    //they basically say they are the one's pushed, allow the display of requirments, and set their input item types, amounts, outputs.
    private void topButton(Button button)
    {
        materialBell = true;
        materialCane = false;
        showReq = true;
        materials = new Item[]{Items.GOLD_INGOT, Items.LAPIS_LAZULI, Items.GOLDEN_APPLE, Items.EMERALD};
        amount = new Integer[]{5, 4, 1, 1};
        output = new ItemStack(ModItems.GOLDBELL.get(), 1);
    }

    private void bottomButton(Button button)
    {
        materialBell = false;
        materialCane = true;
        showReq = true;
        materials = new Item[]{Items.OAK_LOG, Items.LAPIS_LAZULI, Items.IRON_INGOT, Items.DIAMOND};
        amount = new Integer[]{1, 2, 2, 1};
        output = new ItemStack(ModItems.WALKINGCANE.get(), 1);
    }

    //this basically checks to see if each item needed is here and also has enough and also checking if there are no duplicate materials
    //each for loop checks each slot for a specific material and amount
    //All when the button is pressed
    private void confirmButton(Button button)
    {
        if (materials == null || amount == null)
        {
            return;
        }

        int enough = 0;

        //I wil only explain one since the rest are the same
        //checks through all the slots
        for (int x = 0; x < 4; x++)
        {
            ItemStack itemStack = (menu.slots.get(x)).getItem();
            Item item = itemStack.getItem();
            int count = (itemStack.getCount());
            //if the slot has the required item and amount of items in it, it will return true and exit
            if(item == materials[0] && count >= amount[0])
            {
                enough++;
                break;
            }
        }

        for (int x = 0; x < 4; x++)
        {
            ItemStack itemStack = (menu.slots.get(x)).getItem();
            Item item = itemStack.getItem();
            int count = (itemStack.getCount());
            if(item == materials[1] && count >= amount[1])
            {
                enough++;
                break;
            }
        }

        for (int x = 0; x < 4; x++)
        {
            ItemStack itemStack = (menu.slots.get(x)).getItem();
            Item item = itemStack.getItem();
            int count = (itemStack.getCount());
            if(item == materials[2] && count >= amount[2])
            {
                enough++;
                break;
            }
        }

        for (int x = 0; x < 4; x++)
        {
            ItemStack itemStack = (menu.slots.get(x)).getItem();
            Item item = itemStack.getItem();
            int count = (itemStack.getCount());
            if(item == materials[3] && count >= amount[3])
            {
                enough++;
                break;
            }
        }





        //if all the materials are there, it will take away whats needed, while also checking the slots again for which slot to take away from
        //This is done because it needs to be checked first for if there are enough materials so they dont just disappear adn you dont get your item because you didnt have enough
        if (enough == 4)
        {
            //like I said, these ones are basically the same except they take away the items
            for (int x = 0; x < 4; x++)
            {
                ItemStack itemStack = (menu.slots.get(x)).getItem();
                Item item = itemStack.getItem();
                int count = (itemStack.getCount());
                if(item == materials[0] && count >= amount[0])
                {
                    itemStack.shrink(amount[0]); //"shrink" or take away line
                    //these two lines send inventory updates to the Menu/Serverside so that the items are taken away and given, otherwise the server will tell the client nothing was added or removed
                    customMenu.slotsChanged(thiscontainer);
                    customMenu.broadcastChanges();
                    break;
                }
            }

            for (int x = 0; x < 4; x++)
            {
                ItemStack itemStack = (menu.slots.get(x)).getItem();
                Item item = itemStack.getItem();
                int count = (itemStack.getCount());
                if(item == materials[1] && count >= amount[1])
                {
                    itemStack.shrink(amount[1]);
                    customMenu.slotsChanged(thiscontainer);
                    customMenu.broadcastChanges();
                    break;
                }
            }

            for (int x = 0; x < 4; x++)
            {
                ItemStack itemStack = (menu.slots.get(x)).getItem();
                Item item = itemStack.getItem();
                int count = (itemStack.getCount());
                if(item == materials[2] && count >= amount[2])
                {
                    itemStack.shrink(amount[2]);
                    customMenu.slotsChanged(thiscontainer);
                    customMenu.broadcastChanges();
                    break;
                }
            }

            for (int x = 0; x < 4; x++)
            {
                ItemStack itemStack = (menu.slots.get(x)).getItem();
                Item item = itemStack.getItem();
                int count = (itemStack.getCount());
                if(item == materials[3] && count >= amount[3])
                {
                    itemStack.shrink(amount[3]);
                    customMenu.slotsChanged(thiscontainer);
                    customMenu.broadcastChanges();

                    break;
                }
            }
            //gives the player the item
            player.getInventory().add(output);
            customMenu.slotsChanged(thiscontainer);
            customMenu.broadcastChanges();
        }
    }



    //I dont have a backround, but that is mainly because I am not exactly used to making textures and did it incorrectly
    //I didn't have enough time to fix this, but you can't tell there is no backround because of how I set up the rest of the textures
    //also the program won't work without it for some reason, even though it does literally nothing
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY)
    {

    }

    //this is what ACTUALLY applies the textures, and has a little logic in it too for showing materials
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderBackground(pGuiGraphics);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        //renders the main window (the whole frame of the texture you see, including your inventory)
        RenderSystem.setShaderTexture(0, TEXTURE);
        pGuiGraphics.blit
                (
                        TEXTURE,
                        this.leftPos,
                        this.topPos,
                        0,
                        0,
                        this.imageWidth,
                        this.imageHeight
                );

        //if a button is pressed, it will display the required materials based on which was pushed, while also making sure the other text is taken down if the other was pushed
        if(showReq)
        {
            if (materialBell)
            {
                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().scale(0.8F, 0.8F, 1.0F);
                pGuiGraphics.drawString(this.font, "Required Materials: 5 gold, 4 lapis, 1 golden apple, and 1 emerald", this.leftPos - 15, this.topPos - 10, 0xFFFFFF);  // White color
                pGuiGraphics.pose().popPose();
            }
            else if (materialCane)
            {
                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().scale(0.8F, 0.8F, 1.0F);
                pGuiGraphics.drawString(this.font, "Required Materials: 1 oak log, 2 lapis, 2 iron, and 1 diamond", this.leftPos - 15, this.topPos - 10, 0xFFFFFF);  // White color
                pGuiGraphics.pose().popPose();
            }
        }
            //White/Idle button texture
        //top left corner of the texture
        int x = 139;
        int y = 202;
        //W and H of button texture
        int height = 24;
        int width = 24;

        //top button location and placement
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 15, this.topPos + 12, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //bottom button  location and placement
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 15, this.topPos + 49, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Gold Bell icon texture location
        x=118;
        y=195;
        width=12;
        height=14;

        //texture placement location
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 21, this.topPos + 17, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Cane icon texture location
        x=120;
        y=175;
        width=10;
        height=16;

        //texture placement location
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 22, this.topPos + 53, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Crafting tab texture location
        x=1;
        y=170;
        width=111;
        height=75;

        //texture placement location
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 57, this.topPos + 5, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Confirm Button texture location (Has to be rendered AFTER crafting tab since it sits on top of it)
        x=121;
        y=229;
        width=42;
        height=18;

        //texture placement location
        pGuiGraphics.blit
                (
                        TEXTURE,
                        this.leftPos + 63, this.topPos + 10, //button placement location
                        x, y, //button top left corner location (anchor point)
                        width, height //W and H of button texture
                );
    }
}
