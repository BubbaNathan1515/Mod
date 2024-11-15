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
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack item = player.getItemInHand(hand);
        CompoundTag tag = item.getOrCreateTag();
        boolean active = tag.getBoolean("ActiveShimmer");
        player.getCooldowns().addCooldown(this, 20);
        active = !active;
        tag.putBoolean("ActiveShimmer", active);
        return InteractionResultHolder.success(item);
    }

    @Override
    public boolean isFoil(ItemStack item)
    {
        CompoundTag tag = item.getOrCreateTag();
        return tag.getBoolean("ActiveShimmer");
    }

    @Override
    public int getMaxStackSize(ItemStack stack)
    {
        return 1;
    }

    @Override
    public boolean canBeDepleted()
    {
        return false;
    }

    public void jump()
    {
        Player player = Minecraft.getInstance().player;
        if (player != null)
        {
            ItemStack item = null;
            for (ItemStack stack : player.getInventory().items)
            {
                if (stack.getItem() instanceof GoldBellItem)
                {
                    item = stack;
                    break;
                }
            }
            if (item != null)
            {
                CompoundTag tag = item.getOrCreateTag();
                boolean active = tag.getBoolean("ActiveShimmer");
                boolean cooldown = tag.getBoolean("JumpCooldown");
                if (!player.onGround() && active && !cooldown)
                {
                    Vec3 momentum = player.getDeltaMovement();
                    player.setDeltaMovement(momentum.x, 0.8D, momentum.z);
                    player.fallDistance = -0F;
                    tag.putBoolean("JumpCooldown", true);
                }
            }
        }
    }
}