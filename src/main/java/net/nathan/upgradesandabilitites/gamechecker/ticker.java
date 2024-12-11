package net.nathan.upgradesandabilitites.gamechecker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.nathan.upgradesandabilitites.item.custom.GoldBellItem;

//ticks are basically Minecraft's way of making time, this is more similar to a .05 second checker
//tick checker, mainly used for bell item though
@Mod.EventBusSubscriber
public class ticker
{
    private static int tickCounter1 = 0;
    private static boolean jumpCooldown = false;
    //does not need to be called because it is being ran every tick
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        LocalPlayer player = Minecraft.getInstance().player; //gets player

        if ((event.phase == TickEvent.Phase.END || event.phase == TickEvent.Phase.START) && player != null && player.onGround())  //at the end of the tick, player exists, and player is on the ground
        {
            for (ItemStack stack : player.getInventory().items) //checks for bell item in inventory
            {
                //latches to bell item and takes off double jump cooldown if it is applied
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

        if (event.phase == TickEvent.Phase.END) //at the end of the tick
        {
            ItemStack itemStack1 = null;
            if (Minecraft.getInstance().options.keyJump.isDown()) //if the jump key is pressed down (Minecraft can change its jump button to a different key in the settings)
            {
                if (tickCounter1 > 0) //checks if cooldown is active, if it is it takes a tick down
                {
                    tickCounter1--;
                } else if (!jumpCooldown) //if jump cooldown is not active
                {
                    for (ItemStack itemStack2 : player.getInventory().items)
                    {
                        //says the bell is found
                        if (itemStack2.getItem() instanceof GoldBellItem)
                        {
                            itemStack1 = itemStack2;
                            break;
                        }
                    }
                    if (itemStack1 != null) //if the item is real
                    {
                        //activates the double jump method in GoldBellItem and sets the cooldown
                        ((GoldBellItem) itemStack1.getItem()).jump(itemStack1);
                        jumpCooldown = true;
                        tickCounter1 = 12;
                    }
                }
                else //turns off the cooldown when the spacebar is (still) pressed and if the counter is 0, allowing the jump to be activated again if the spacebar is still held down
                {
                    jumpCooldown = false;
                }
            }
        }
    }
}