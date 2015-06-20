package com.team.futurecraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSpaceSuit extends ItemArmor {
	public ItemSpaceSuit(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (type == "overlay") {
			return "armor/iron_layer_2";
		}
		else {
			return "armor/iron_layer_1";
		}
    }

}
