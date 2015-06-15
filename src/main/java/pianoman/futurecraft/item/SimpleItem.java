package pianoman.futurecraft.item;

import pianoman.futurecraft.FutureCraft;
import net.minecraft.item.Item;

/**
 * Works the same as SimpleBlock but for items.
 * 
 * @author Joseph
 *
 */
public class SimpleItem extends Item
{
	public SimpleItem(String name) {
		this.setUnlocalizedName(name);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
	}
}
