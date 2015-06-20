package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;

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
public class ItemLaser extends Item {
	public ItemLaser() {
		this.maxStackSize = 1;
		this.setMaxDamage(384);
		this.setFull3D();
        this.setCreativeTab(FutureCraft.tabFutureCraft);
	}
	
	public ItemStack onItemRightClick(ItemStack par1, World par2, EntityPlayer par3) {
		par1.damageItem(1, par3);
		return par1;
    }
	
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 1;
    }
}
