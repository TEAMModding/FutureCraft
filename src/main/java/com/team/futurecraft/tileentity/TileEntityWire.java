package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.BlockWire;
import com.team.futurecraft.block.IElectric;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;

/**
 * TileEnity wire is different from most other energy blocks. It doesn't extend TileEntityMachine since it shouldn't
 * have any inventory. Instead it extends EnergyContainer directly and only overrides update().
 *
 */
public class TileEntityWire extends EnergyContainer {
	private BlockWire theBlock;
	
	public TileEntityWire(int energyTransferred, BlockWire block) {
		super(energyTransferred, energyTransferred);
		this.theBlock = block;
	}
	
	@Override
	public void update() {
		Object[] sides = theBlock.getConnectedBlocks(this.worldObj, this.pos);
		for (int i = 0; i < sides.length; i++) {
			int passesLeft = sides.length - i + 1;
			EnumFacing dir = (EnumFacing)sides[i];
			Block block = this.worldObj.getBlockState(this.pos.offset(dir)).getBlock();
			if (block instanceof IElectric) {
				int energyToUse = this.getEnergy() / passesLeft;
				this.removeEnergy(energyToUse - ((IElectric)block).onPowered(worldObj, this.pos.offset(dir), energyToUse, dir.getOpposite()));
			}
		}
	}
	
	public int power(int amount)
	{
		if (!this.isFull()) {
			if (this.getMaxEnergy() - this.getEnergy() > amount) {
				this.addEnergy(amount);
				return 0;
			}
			else {
				int difference = this.getMaxEnergy() - this.getEnergy();
				this.setEnergy(this.getMaxEnergy());
				return amount - difference;
			}
		}
		else return amount;
	}
}
