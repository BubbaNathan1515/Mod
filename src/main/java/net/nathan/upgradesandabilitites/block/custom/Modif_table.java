package net.nathan.upgradesandabilitites.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult
            hit)
    {
        if (!level.isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer) player,
                    new SimpleMenuProvider(
                            (windowId, inventory, playerEntity) -> new Menu(windowId, inventory, null, null),
                            Component.literal("Modification Table")
                    ), pos);
        }

        return InteractionResult.SUCCESS;
    }
}
