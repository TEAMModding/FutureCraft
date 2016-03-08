package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.BlockWire;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * TileEnity wire is different from most other energy blocks. It doesn't extend TileEntityMachine since it shouldn't
 * have any inventory. Instead it extends EnergyContainer directly and only overrides update().
 *
 */
public class TileEntityWire extends EnergyContainer implements IElectric {
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
	
	//====================================================
	//<-----======IElectric implementations=======------->
	//====================================================
	
	@Override
	public int onPowered(World world, BlockPos pos, int amount, EnumFacing side) {
		return this.power(amount);
	}

	@Override
	public boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (world.getTileEntity(pos.offset(side)) instanceof IElectric) 
			return true;
		else 
			return false;
	}

	@Override
	public int getEnergy(World world, BlockPos pos) {
		return this.getEnergy();
	}
}
