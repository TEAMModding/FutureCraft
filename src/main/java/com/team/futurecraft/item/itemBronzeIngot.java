package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class itemBronzeIngot extends Item {
	public itemBronzeIngot(String name) {
		this(name, true);
	}
	
	public itemBronzeIngot(String bronze_ingot, boolean onTab) {
		this.setUnlocalizedName(bronze_ingot);
		if (onTab) {
			this.setCreativeTab(FutureCraft.tabFutureCraft);
		}
		GameRegistry.registerItem(this, bronze_ingot);
	}
}
