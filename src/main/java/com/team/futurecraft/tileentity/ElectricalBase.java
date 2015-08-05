package com.team.futurecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class ElectricalBase extends TileEntity implements IUpdatePlayerListBox {
	public int energy = 0;
	private EnumFacing[] outputDirections = EnumFacing.HORIZONTALS;
	
	public ElectricalBase() {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("energy", this.energy);
		super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.energy = tag.getInteger("energy");
		super.readFromNBT(tag);
	}
	
	public boolean acceptsEnergyFrom(EnumFacing side) {
		return true;
	}

	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			for (EnumFacing i : outputDirections) {
				BlockPos neighborPos = this.pos.offset(i);
				if (this.worldObj.getTileEntity(neighborPos) instanceof ElectricalBase) {
					ElectricalBase neighbor = (ElectricalBase)this.worldObj.getTileEntity(neighborPos);
					if (neighbor.acceptsEnergyFrom(i.getOpposite())); {
						if ((this.energy - neighbor.energy) > 0 && this.energy > 0) {
							System.out.println("powering neighbor to the " + i);
							neighbor.energy++;
							this.energy--;
							this.markDirty();
						}
					}
				}
			}
		}
	}
}
