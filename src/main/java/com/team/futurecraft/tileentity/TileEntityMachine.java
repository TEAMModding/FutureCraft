package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.EnumMachineSetting;
import com.team.futurecraft.block.IElectric;
import com.team.futurecraft.block.Machine;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Currently unused. Need to update the machine class.
 * 
 * @author Joseph
 *
 */
public class TileEntityMachine extends EnergyContainer implements ISidedInventory
{
	public Machine theBlock;
	public ItemStack[] slots;
	
	public TileEntityMachine(int energyTransfer, int maxEnergy, Machine block, boolean isFull, int slots)
	{
		super(maxEnergy, energyTransfer);
		this.theBlock = block;
		if (isFull)
		{
			this.energy = maxEnergy;
		}
		this.slots = new ItemStack[slots];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.energy = tag.getInteger("energy");
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length)
            {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) 
	{
		super.writeToNBT(tag);
		tag.setInteger("energy", this.energy);
		NBTTagList nbttaglist = new NBTTagList();
	    for (int i = 0; i < this.slots.length; ++i)
	    {
	        if (this.slots[i] != null)
	        {
	            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setByte("Slot", (byte)i);
	            this.slots[i].writeToNBT(nbttagcompound1);
	            nbttaglist.appendTag(nbttagcompound1);
	        }
	    }
	    tag.setTag("Items", nbttaglist);
	}
	
	public int tryToPower(int amount, ForgeDirection side)
	{
		boolean flag = false;
		
		if (this.theBlock.getSide(side.flag, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) == EnumMachineSetting.energyInput)
		{
			if (this.energy < this.getMaxEnergy())
			{
				if (this.getMaxEnergy() - this.energy >= amount)
				{
				this.energy += amount;
				flag = true;
				return 0;
				}
				else
				{
					amount = this.getMaxEnergy() - this.energy;
					this.energy = this.getMaxEnergy();
					flag = true;
					return amount;
				}
			}
		}
		if (flag)
		{
			this.markDirty();
		}
		return amount;
	}

	@Override
	public void updateEntity() 
	{
		int i = 0;
		while (this.energy > this.energyTransferred() && i < 6)
		{
			if (this.theBlock.getSide(i, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) == EnumMachineSetting.energyOutput)
			{
				this.powerNeighbor(i);
				this.markDirty();
			}
			i++;
		}
	}
	
	private void powerNeighbor(int side)
	{
		int energyToTransfer;
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		int targetX = this.xCoord + dir.offsetX;
		int targetY = this.yCoord + dir.offsetY;
		int targetZ = this.zCoord + dir.offsetZ;
		
		if (this.energy > 0)
		{
			if (this.energy < this.energyTransferred())
			{
				energyToTransfer = this.energy;
			}
			else
			{
				energyToTransfer = this.energyTransferred();
			}
			if (this.theBlock.canConnectTo(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir))
			{
				IElectric blockToPower = (IElectric)this.worldObj.getBlock(targetX, targetY, targetZ);
				this.energy -= energyToTransfer - blockToPower.onPowered(this.worldObj, targetX, targetY, targetZ, energyToTransfer, dir.getOpposite());
			}
		}
	}
	
	/**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return slots.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int index)
    {
        return slots[index];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
    	if (this.slots[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.slots[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.slots[p_70298_1_];
                this.slots[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.slots[p_70298_1_].splitStack(p_70298_2_);

                if (this.slots[p_70298_1_].stackSize == 0)
                {
                    this.slots[p_70298_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }
    
    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
    	if (this.slots[p_70304_1_] != null)
        {
            ItemStack itemstack = this.slots[p_70304_1_];
            this.slots[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
    	this.slots[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return "machine";
    }

    /**
     * Returns if the inventory is named
     */
    public boolean hasCustomInventoryName()
    {
        return false;
    }
    
    /**
     * Returns the maximum stack size for a inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return new int[] {0};
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
    {
        return false;
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
    {
        return false;
    }
}
