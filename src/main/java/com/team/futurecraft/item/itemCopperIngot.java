package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class itemCopperIngot extends Item {
	public itemCopperIngot(String name) {
		this(name, true);
	}
	
	public itemCopperIngot(String copper_ingot, boolean onTab) {
		this.setUnlocalizedName(copper_ingot);
		if (onTab) {
			this.setCreativeTab(FutureCraft.tabFutureCraft);
		}
		GameRegistry.registerItem(this, copper_ingot);
	}
}
