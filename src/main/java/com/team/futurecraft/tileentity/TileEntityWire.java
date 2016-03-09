package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.BlockWire;

import net.minecraft.tileentity.TileEntity;
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
			TileEntity te = this.worldObj.getTileEntity(this.pos.offset(dir));
			if (te instanceof IElectric) {
				int energyToUse = this.getEnergy() / passesLeft;
				this.removeEnergy(energyToUse - ((IElectric)te).onPowered(worldObj, this.pos.offset(dir), energyToUse, dir.getOpposite()));
			}
		}
	}
}
