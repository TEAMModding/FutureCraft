package com.team.item;

import com.team.FutureCraft;
import com.team.block.IElectric;

import net.minecraft.block.Block;
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
public class ItemMultimeter extends SimpleItem
{
	public ItemMultimeter(String name)
    {
		super(name);
		this.maxStackSize = 1;
		this.setCreativeTab(FutureCraft.tabFutureCraft);
    }
	
	 /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	Block block = world.getBlockState(pos).getBlock();
    	
    	if (block instanceof IElectric)
    	{
    		if (world.isRemote)
    			player.addChatMessage(new ChatComponentText("Client Energy: " + ((IElectric)block).getEnergy(world, pos) + " Joules."));
    		else
    			player.addChatMessage(new ChatComponentText("Server Energy: " + ((IElectric)block).getEnergy(world, pos) + " Joules."));
    	}
    	
    	return true;
    }
}
