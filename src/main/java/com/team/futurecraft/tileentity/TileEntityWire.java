package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.BlockWire;
import com.team.futurecraft.block.IElectric;

import net.minecraft.block.Block;

/**
 * Currently unused. Need to update the wire block.
 * 
 * @author Joseph
 *
 */
public class TileEntityWire extends EnergyContainer
{
	private BlockWire theBlock;
	
	public TileEntityWire(int energyTransferred, BlockWire block)
	{
		super(energyTransferred, energyTransferred);
		this.theBlock = block;
	}
	
	@Override
	public void updateEntity() 
	{
		Object[] sides = theBlock.getConnectedBlocks(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		for (int i = 0; i < sides.length; i++)
		{
			int passesLeft = sides.length - i + 1;
			ForgeDirection dir = (ForgeDirection)sides[i];
			Block block = this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			if (block instanceof IElectric)
			{
				int energyToUse = this.energy / passesLeft;
				this.energy -= energyToUse - ((IElectric)block).onPowered(worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, energyToUse, dir.getOpposite());
			}
		}
	}
	
	public int power(int amount)
	{
		if (!this.isFull())
		{
			if (this.getMaxEnergy() - this.energy > amount)
			{
				this.energy += amount;
				return 0;
			}
			else
			{
				int difference = this.getMaxEnergy() - this.energy;
				this.energy = this.getMaxEnergy();
				return amount - difference;
			}
		}
		else
			return amount;
	}
}
