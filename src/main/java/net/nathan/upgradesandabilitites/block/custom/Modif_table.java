package net.nathan.upgradesandabilitites.block.custom;
/*
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

 */

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.nathan.upgradesandabilitites.block.gui.Menu;
import net.nathan.upgradesandabilitites.block.gui.Screen;


public class Modif_table extends Block
{

//verifies properties
    public Modif_table(Properties properties)
    {
        super(properties);
    }

    //When the block is right clicked when in world
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        //serverside
        if (!level.isClientSide)
        {
            //Basically makes a new instance of a GUI screen for server
            NetworkHooks.openScreen((ServerPlayer) player,
                    new SimpleMenuProvider(
                            (windowId, inventory, playerEntity) -> new Menu(windowId, inventory, new SimpleContainer(4), new SimpleContainerData(3)),

                            Component.literal("Modification Table")
                    ), pos);
        }
        //clientside (This is the part that took me way to long to realize I didn't have)
        else
        {
            //Makes a new instance of the GUI for the client
            Minecraft.getInstance().setScreen(new Screen(
                    new Menu(0, Minecraft.getInstance().player.getInventory(), new SimpleContainer(4), new SimpleContainerData(3)),
                    Minecraft.getInstance().player.getInventory(),
                    Component.literal("Modification Table")
            ));
        }
        //this basically makes that animated of your character swinging their arm when you interact
        return InteractionResult.SUCCESS;
    }
}