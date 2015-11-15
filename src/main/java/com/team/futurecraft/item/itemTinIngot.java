package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class itemTinIngot extends Item {
	public itemTinIngot(String name) {
		this(name, true);
	}
	
	public itemTinIngot(String tin_ingot, boolean onTab) {
		this.setUnlocalizedName(tin_ingot);
		if (onTab) {
			this.setCreativeTab(FutureCraft.tabFutureCraft);
		}
		GameRegistry.registerItem(this, tin_ingot);
	}
}
