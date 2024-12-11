package net.nathan.upgradesandabilitites.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class GoldBellItem extends Item
{
    public GoldBellItem(Properties properties)
    {
        super(properties); //passes up the item's base properties to the item class (like durability, max stack size, and other base data for the item)
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack item = player.getItemInHand(hand);//checks if the item is the bell
        player.getCooldowns().addCooldown(this, 20); //sets cooldown on item to prevent glitches from item spam

        //creates tag to apply/remove shimmer effect
        CompoundTag tag = item.getOrCreateTag();
        boolean active = tag.getBoolean("ActiveShimmer");

        //toggles the shimmer effect
        active = !active;
        tag.putBoolean("ActiveShimmer", active);
        return InteractionResultHolder.success(item);
    }

    //will apply the "Shimmer" effect to item
    @Override
    public boolean isFoil(ItemStack itemStack)
    {
        CompoundTag tag = itemStack.getOrCreateTag();
        return tag.getBoolean("ActiveShimmer");
    }
    // set max stack size to 1 (unstackable)
    @Override
    public int getMaxStackSize(ItemStack itemStack)
    {
        return 1;
    }

    //makes sure durability is not applied to item
    @Override
    public boolean canBeDepleted()
    {
        return false;
    }

    //Makes the player jump in mid-air
    public void jump(ItemStack itemStack)
    {
        Player player = Minecraft.getInstance().player;
        if (player != null && itemStack != null) //if the player and item exists
        {
                //grabs shimmer and cooldown NBT tags (data associated to a specific instance of an item)
                CompoundTag tag = itemStack.getOrCreateTag();
                boolean active = tag.getBoolean("ActiveShimmer");
                boolean cooldown = tag.getBoolean("JumpCooldown");

                if (!player.onGround() && active && !cooldown) //checks if the bell is active, is not on the double jump cooldown, and is in the air and allows the player to jump
                {
                    Vec3 momentum = player.getDeltaMovement(); //gets player movement
                    player.setDeltaMovement(momentum.x, 0.8D, momentum.z); //pushes the player upward while keeping the other momentum
                    player.fallDistance = -0F; //resets fall distance to allow less fall damage but still penalize not jumping to higher places or missing
                    tag.putBoolean("JumpCooldown", true); //applies the double jump cooldown
                }

        }
    }
}