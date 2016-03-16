package com.team.futurecraft.item;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.tileentity.IElectric;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/** 
 * The multimeter class, for reading electric charge of blocks.
 * 
 * @author Joseph
 *
 */
public class ItemMultimeter extends SimpleItem {
	public ItemMultimeter(String name) {
		super(name);
		this.maxStackSize = 1;
		this.setCreativeTab(FutureCraft.tabFutureCraft);
    }
	
	 /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
    	
    	if (world.getTileEntity(pos) instanceof IElectric) {
    		IElectric te = (IElectric)world.getTileEntity(pos);
    		if (world.isRemote) player.addChatMessage(new ChatComponentText("Client Energy: " + te.getEnergy(world, pos) + " Units."));
    		else player.addChatMessage(new ChatComponentText("Server Energy: " + te.getEnergy(world, pos) + " Units."));
    	}
    	return true;
    }
}
