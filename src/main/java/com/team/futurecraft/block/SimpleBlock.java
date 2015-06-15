package com.team.futurecraft.block;

import java.util.Random;

import com.team.futurecraft.FutureCraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

/**
 * This is a simple block class for convenience.
 * Instead of specifying things such as new Block().setUnlocalizedName("block") and such,
 * this will do it all in the constructor.
 * It is recommended most other blocks extend this class instead of Block and then they can just
 * specify super(material, name) in their constructor.
 * 
 * @author Joseph
 *
 */
public class SimpleBlock extends Block
{
	private Item dropThis = Item.getItemFromBlock(this);
	
	public SimpleBlock(Material par1, String name)
	{
		super(par1);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
		this.setUnlocalizedName(name);
	}
	
	public SimpleBlock(Material par1, Item drop, String name)
	{
		super(par1);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
		this.dropThis = drop;
		this.setUnlocalizedName(name);
	}
	
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return this.dropThis;
	}
}