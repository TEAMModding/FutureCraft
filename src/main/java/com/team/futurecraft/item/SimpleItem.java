package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Works the same as SimpleBlock but for items.
 * 
 * @author Joseph
 */
public class SimpleItem extends Item {
	public SimpleItem(String name) {
		this(name, true);
	}
	
	public SimpleItem(String name, boolean onTab) {
		this.setUnlocalizedName(name);
		if (onTab) {
			this.setCreativeTab(FutureCraft.tabFutureCraft);
		}
		GameRegistry.registerItem(this, name);
	}
}
