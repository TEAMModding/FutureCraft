package com.team.futurecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Currently unused. Havent updated any energy blocks yet.
 * 
 * @author Joseph
 *
 */
public abstract class EnergyContainer extends TileEntity
{
	public int energy = 0;
	private int energyTransferred;
	private int maxEnergy;
	
	public EnergyContainer(int maxEnergy, int energyTransfer)
	{
		this.maxEnergy = maxEnergy;
		this.energyTransferred = energyTransfer;
	}
	
	public int getEnergy()
	{
		return this.energy;
	}
	
	public int getMaxEnergy()
	{
		return this.maxEnergy;
	}
	
	public int energyTransferred()
	{
		return this.energyTransferred;
	}
	
	public boolean isFull()
	{
		return this.energy == this.maxEnergy;
	}
	
	public void increaseEnergy(int amount)
	{
		this.energy += amount;
		if (this.energy > this.maxEnergy)
			this.energy = this.maxEnergy;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) 
	{
		tag.setInteger("energy", this.energy);
		super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.energy = tag.getInteger("energy");
		super.readFromNBT(tag);
	}
	
	public int getEnergyAmountScaled(int par1)
	{
		return this.energy * par1 / this.maxEnergy;
	}
}
