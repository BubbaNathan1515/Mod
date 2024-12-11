package net.nathan.upgradesandabilitites.gamechecker;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.nathan.upgradesandabilitites.item.ModItems;

public class SpeedEffect
{
    //event bus
    public static void register(IEventBus eventBus)
    {
        MinecraftForge.EVENT_BUS.register(new SpeedEffect());
    }

    //It says it has no uses, but that is because it is a Subscriber Event, it is only used when it is lined up to be
    //In this case it is linked to each player tick (Client side time basically)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;

        ItemStack offhand = player.getOffhandItem();
        //if the Cane is in the offhand
        if (offhand.getItem() == ModItems.WALKINGCANE.get())
        {
            //and the player dosen't have the buff already
            if (!player.hasEffect(MobEffects.MOVEMENT_SPEED))
            {
                //Initialize the new speed effect (there aren't any default easily callable ones for some reason, speed is a vanilla effect)
                MobEffectInstance speedEffect = new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        60,
                        2,
                        true,
                        true
                );
                player.addEffect(speedEffect); //adds the effect to the player
            }
        }
        else //if it is not in their hand
        {
            if (player.hasEffect(MobEffects.MOVEMENT_SPEED)) // and they have the speed buff
            {
                player.removeEffect(MobEffects.MOVEMENT_SPEED); //remove the speed buff
            }
        }
    }
}

