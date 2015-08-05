package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSpaceSuit extends ItemArmor {
	public ItemSpaceSuit(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "textures/models/armor/spaceSuit_layer_1.png";
    }

}
