package net.nathan.upgradesandabilitites.block.custom;

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

public class Modif_table extends Block
{
    public Modif_table(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!level.isClientSide)  // Ensure this is only executed on the server side
        {
            System.out.println("Server-side: Attempting to open the screen.");

            // Open the screen
            NetworkHooks.openScreen((ServerPlayer) player,
                    new SimpleMenuProvider(
                            (id, inventory, playerEntity) -> {
                                System.out.println("Creating Menu...");
                                return new Menu(id, inventory, new SimpleContainer(4), new SimpleContainerData(3));
                            },
                            Component.literal("Modification Table")
                    ), pos);

            System.out.println("ModifScreen opened on the server for player " + player.getName().getString());
        }
        else
        {
            System.out.println("Client-side: Ignored screen opening, only server should handle this.");
        }

        return InteractionResult.SUCCESS;
    }
}