package com.team.futurecraft.item;

import com.team.futurecraft.block.SimpleBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSimpleBlock extends ItemBlock {
	private SimpleBlock block;

	public ItemSimpleBlock(Block block) {
		super(block);
		this.block = (SimpleBlock) block;
		this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack stack)
    {
		String metaName = this.block.getMetaName(stack.getMetadata());
		if (metaName != null) {
			return this.block.getUnlocalizedName() + "." + metaName;
		}
		else {
			return this.block.getUnlocalizedName();
		}
    }
	
	public int getMetadata(int damage)
    {
        return damage;
    }
}
