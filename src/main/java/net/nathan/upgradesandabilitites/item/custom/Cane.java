package net.nathan.upgradesandabilitites.item.custom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Cane extends Item
{
    //this is really just to set properties
    public Cane(Properties properties)
    {
        super(properties);
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
}
