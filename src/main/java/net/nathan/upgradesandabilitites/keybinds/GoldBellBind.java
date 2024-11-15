package net.nathan.upgradesandabilitites.keybinds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.nathan.upgradesandabilitites.item.custom.GoldBellItem;

@Mod.EventBusSubscriber
public class GoldBellBind
{
    private static int tickCounter = 0;
    private static boolean jumpCooldown = false;
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        LocalPlayer player = Minecraft.getInstance().player;

        if (event.phase == TickEvent.Phase.END)
        {
            if (player != null)
            {
                if (player.onGround())
                {
                    for (ItemStack stack : player.getInventory().items)
                    {
                        if (stack.getItem() instanceof GoldBellItem)
                        {
                            CompoundTag tag = stack.getOrCreateTag();
                            if (tag.getBoolean("JumpCooldown"))
                            {
                                tag.putBoolean("JumpCooldown", false);
                            }
                        }
                    }
                }
            }
        }
        if (event.phase == TickEvent.Phase.END)
        {
            if (Minecraft.getInstance().options.keyJump.isDown())
            {
                if (tickCounter > 0)
                {
                    tickCounter--;
                } else if (!jumpCooldown)
                {
                    player.getInventory().items.stream()
                            .filter(stack -> stack.getItem() instanceof GoldBellItem)
                            .findFirst()
                            .ifPresent(stack ->
                            {
                                ((GoldBellItem) stack.getItem()).jump();
                                jumpCooldown = true;
                                tickCounter = 12;
                            });
                }
            }
            else
            {
                jumpCooldown = false;
            }
        }
    }
}
