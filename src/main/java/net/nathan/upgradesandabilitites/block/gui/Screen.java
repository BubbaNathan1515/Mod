package net.nathan.upgradesandabilitites.block.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import static net.nathan.upgradesandabilitites.UpgradesandAbilitites.MOD_ID;


public class Screen extends AbstractContainerScreen<Menu>
{

    private Button topButton;
    private Button bottomButton;
    private Button confirmButton;
    private boolean materialBell;
    private boolean materialCane;
    private int confirm;
    private Integer[] amount;
    private Item[] materials;


    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/GUI_modif_table.png");

    public Screen(Menu container, Inventory plyInv, Component component)
    {
        super(container, plyInv, component);
        this.imageWidth = 251;
        this.imageHeight = 139;
        System.out.println("1");
    }


    @Override
    protected void init()
    {
        System.out.println("2");
        //Top Button
        super.init();
        //top left corner
        int x = 26;
        int y = 143;
        //W and H of button texture
        int height = 23;
        int width = 23;

        topButton = Button.builder(Component.literal("Test Text"), this::topButton)
                .pos(x, y) //position
                .size(width, height) //size of the button
                .build(); //takes in those properties and builds it
        this.addRenderableWidget(topButton);  // renders the button to the screen
        System.out.println("3");
        bottomButton = Button.builder(Component.literal("Test Text"), this::bottomButton)
                .pos(x, y)
                .size(width, height)
                .build();
        this.addRenderableWidget(bottomButton);  // renders the button to the screen

        x=65;
        y=145;
        width=41;
        height=18;

        if(confirm != 0)
        {
            System.out.println("4");
            confirmButton = Button.builder(Component.literal("Test Text"), this::confirmButton)
                    .pos(x, y)
                    .size(width, height)
                    .build();
            this.addRenderableWidget(confirmButton);  // renders the button to the screen
            if(confirm == 1)
            {
                materials = new Item[]{Items.GOLD_INGOT, Items.LAPIS_LAZULI, Items.GOLDEN_APPLE, Items.EMERALD};
                amount = new Integer[]{5, 4, 5, 1};
            }
            else if(confirm == 2)
            {
                materials = new Item[]{Items.OAK_LOG, Items.LAPIS_LAZULI, Items.IRON_INGOT, Items.DIAMOND};
                amount = new Integer[]{1, 2, 2, 1};
            }
            System.out.println("5");
        }
    }

    private void topButton(Button button) //What Button 1 will do
    {
        materialBell = true;
        System.out.println("6");
    }

    private void bottomButton(Button button) //What button 2 will do
    {
        materialCane = true;
        System.out.println("7");
    }

    private  void confirmButton(Button button) //what Confirm button will do
    {
        boolean reqmet = false;
        for (int x = 0; x < 4; x++)
        {
            for(Slot slot : menu.slots)
            {
                ItemStack item = slot.getItem();
                if (item.getItem() == materials[x] && item.getCount() >= amount[x])
                {
                    reqmet = true;
                }
            }
        }
        if(reqmet)
        {
            for (int x = 0; x < 4; x++)
            {
                for (Slot slot : menu.slots)
                {
                    ItemStack item = slot.getItem();
                    if (item.getItem() == materials[x] && item.getCount() >= amount[x])
                    {
                        // Reduce the item count in the slot
                        item.shrink(amount[x]);
                    }
                }
            }
        }

    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY)
    {
        System.out.println("8");
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
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {
        System.out.println("9");
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderBackground(pGuiGraphics);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        if(materialBell)
        {
            pGuiGraphics.drawString(this.font, "Required Materials: 5 gold, 4 lapis, 1 golden apple, and 1 emerald", this.leftPos + 10, this.topPos + 20, 0x404040);
            confirm = 1;
        }

        if(materialCane)
        {
            pGuiGraphics.drawString(this.font, "Required Materials: 1 oak log, 2 lapis, 2 iron, and 1 diamond", this.leftPos + 10, this.topPos + 20, 0x404040);
            confirm = 2;
        }

            //White/Idle button texture
        //top left corner
        int x = 26;
        int y = 143;
        //W and H of button texture
        int height = 23;
        int width = 23;

        //top button
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 50, this.topPos + 100, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //bottom button
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 50, this.topPos + 50, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Confirm Button
        x=65;
        y=145;
        width=41;
        height=18;
        pGuiGraphics.blit
                (
                        TEXTURE,
                        this.leftPos + 198, this.topPos + 21, //button placement location
                        x, y, //button top left corner location (anchor point)
                        width, height //W and H of button texture
                );

        //Gold Bell icon
        x=5;
        y=164;
        width=11;
        height=13;
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 56, this.topPos + 55, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Cane icon
        x=7;
        y=144;
        width=9;
        height=15;
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 58, this.topPos + 54, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );

        //Crafting tab
        x=132;
        y=149;
        width=110;
        height=118;
        pGuiGraphics.blit
        (
                TEXTURE,
                this.leftPos + 132, this.topPos + 15, //button placement location
                x, y, //button top left corner location (anchor point)
                width, height //W and H of button texture
        );
    }
}
