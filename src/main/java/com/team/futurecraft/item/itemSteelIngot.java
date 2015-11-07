package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class itemSteelIngot extends Item {
	public itemSteelIngot(String name) {
		this(name, true);
	}
	
	public itemSteelIngot(String steel_ingot, boolean onTab) {
		this.setUnlocalizedName(steel_ingot);
		if (onTab) {
			this.setCreativeTab(FutureCraft.tabFutureCraft);
		}
		GameRegistry.registerItem(this, steel_ingot);
	}
}
