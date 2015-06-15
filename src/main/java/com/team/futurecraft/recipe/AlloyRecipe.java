package com.team.futurecraft.recipe;

import net.minecraft.item.ItemStack;

/**
 * Represents an alloy recipe for registering.
 * 
 * @author Joseph
 *
 */
public class AlloyRecipe 
{
	private ItemStack neededStack1;
	private ItemStack neededStack2;
	private ItemStack output;
	
	public AlloyRecipe(ItemStack stack1, ItemStack stack2, ItemStack output)
	{
		this.neededStack1 = stack1;
		this.neededStack2 = stack2;
		this.output = output;
	}
	
	public ItemStack getOutput()
	{
		return this.output;
	}
	
	public ItemStack getStack1()
	{
		return this.neededStack1;
	}
	
	public ItemStack getStack2()
	{
		return this.neededStack2;
	}
}