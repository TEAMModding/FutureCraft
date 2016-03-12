package com.team.futurecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This is the class that all energy-storing TileEntities extend.
 * The only methods you'll be overriding from here will be 
 * writeToNBT(NBTTagCompound tag) and readFromNBT(NBTTagCompound tag).
 * Note that you must call super(tag) in them for this class to save energy data.
 * 
 * This class also has some standard IElectric implementations. These can be freely overridden.
 */
public abstract class EnergyContainer extends TileEntity implements IUpdatePlayerListBox, IElectric {
	private int energy = 0;
	private int energyTransferred;
	private int maxEnergy;
	
	public EnergyContainer(int maxEnergy, int energyTransfer) {
		this.maxEnergy = maxEnergy;
		this.energyTransferred = energyTransfer;
	}
	
	public int getEnergy() {
		return this.energy;
	}
	
	public int getMaxEnergy() {
		return this.maxEnergy;
	}
	
	/**
	 * Gets how much energy is transferred every tick.
	 */
	public int energyTransferred() {
		return this.energyTransferred;
	}
	
	public boolean isFull() {
		return this.energy == this.maxEnergy;
	}
	
	/**
	 * Adds energy to this TE. Dont worry about exceeding the maxEnergy limit, this sets it to the limit
	 * automatically if you increase it higher than the limit. But note that energy will be destroyed.
	 */
	public void addEnergy(int amount) {
		this.energy += amount;
		if (this.energy > this.maxEnergy)
			this.energy = this.maxEnergy;
		
		this.markDirty();
	}
	
	/**
	 * Sets this TE's energy. Dont worry about exceeding the maxEnergy limit, this will set it to the limit if
	 * the amount is higher. Although because of this, there can be energy destruction.
	 */
	public void setEnergy(int amount) {
		if (amount > this.maxEnergy) 
			this.energy = this.maxEnergy;
		else 
			this.energy = amount;
		this.markDirty();
	}
	
	/**
	 * Remove energy from this TE. Automatically checks for negative values and sets them to 0.
	 */
	public void removeEnergy(int amount) {
		this.energy -= amount;
		if (this.energy < 0) 
			this.energy = 0;
		this.markDirty();
	}
	
	/**
	 * Any class that extends this method MUST call super(tag) or else it wont save energy values.
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("energy", this.getEnergy());
		super.writeToNBT(tag);
	}
	
	/**
	 * Any class that extends this method MUST call super(tag) or else it wont read energy values.
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.setEnergy(tag.getInteger("energy"));
		super.readFromNBT(tag);
	}
	
	/**
	 * Gets the energy amount in between 0 and scale. Usually used for energy level gui's.
	 */
	public int getEnergyAmountScaled(int scale) {
		return this.energy * scale / this.maxEnergy;
	}
	
	/**
	 * Adds power to the block and returns excess.
	 */
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
