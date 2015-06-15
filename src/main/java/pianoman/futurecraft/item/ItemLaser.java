package pianoman.futurecraft.item;

import pianoman.futurecraft.FutureCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This was a failed first attempt to make a laser. currently unimplimented.
 * Feel free to experiment with this class and see if you can get it to know when it's
 * right clicked. I got stuck there and moved on to other stuff.
 * 
 * @author Joseph
 *
 */
public class ItemLaser extends Item
{
	
	public ItemLaser()
	{
		this.maxStackSize = 1;
		this.setMaxDamage(384);
		this.setFull3D();
        this.setCreativeTab(FutureCraft.tabFutureCraft);
	}
	
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        return p_77654_1_;
    }
	
	public ItemStack onItemRightClick(ItemStack par1, World par2, EntityPlayer par3)
    {
		par1.damageItem(1, par3);
		return par1;
    }
	
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 1;
    }
}
