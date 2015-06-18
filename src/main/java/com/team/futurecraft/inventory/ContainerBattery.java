package com.team.futurecraft.inventory;

import com.team.futurecraft.tileentity.TileEntityMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerBattery extends Container
{
	private TileEntityMachine tileEntity;
	
	public ContainerBattery(TileEntityMachine tileEntity)
	{
		this.tileEntity = tileEntity;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) 
	{
		return this.tileEntity.isUseableByPlayer(p_75145_1_);
	}
	
}
