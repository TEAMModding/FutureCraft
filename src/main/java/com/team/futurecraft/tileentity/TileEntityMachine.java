package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.EnumMachineSetting;
import com.team.futurecraft.block.IElectric;
import com.team.futurecraft.block.Machine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

/**
 * This is the class that implements EnergyContainer and adds functionality
 * usually needed in machines without having to implement them yourself, such as handling
 * in/out machine settings, transferring energy based on them, and dealing with inventory slots.
 * It's a bit of a mess right now and I need to organize and comment it all to make it an easy to use
 * api.
 * 
 * @author Joseph
 *
 */
public class TileEntityMachine extends EnergyContainer implements IUpdatePlayerListBox, ISidedInventory
{
	private Machine theBlock;
	private IBlockState state;
	private ItemStack[] slots;
	private String customName;
	
	public TileEntityMachine(int energyTransfer, int maxEnergy, IBlockState state, boolean isFull, int slots)
	{
		super(maxEnergy, energyTransfer);
		System.out.println("instantiating TilEntityMachine with parameters");
		this.theBlock = (Machine)state.getBlock();
		this.state = state;
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
	
	public int tryToPower(int amount, EnumFacing side)
	{
		boolean flag = false;
		
		
		
		if (this.theBlock.getSide(rotatedFace(side, (EnumFacing)this.state.getValue(Machine.FACING))) == EnumMachineSetting.energyInput)
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
	
	private EnumFacing rotatedFace(EnumFacing face1, EnumFacing face2) {
		if (face2 == EnumFacing.NORTH) return face1;
		if (face2 == EnumFacing.SOUTH) return face1.getOpposite();
		if (face2 == EnumFacing.WEST) return face1.rotateY();
		if (face2 == EnumFacing.EAST) return face1.rotateY().rotateY().rotateY();
		
		return face1;
	}

	@Override
	public void update() {
		int i = 0;
		while (this.energy > this.energyTransferred() && i < 4)
		{
			if (this.theBlock.getSide(rotatedFace(EnumFacing.getHorizontal(i), (EnumFacing)this.state.getValue(Machine.FACING))) == EnumMachineSetting.energyOutput)
			{
				this.powerNeighbor(EnumFacing.getHorizontal(i));
				this.markDirty();
			}
			i++;
		}
	}
	
	private void powerNeighbor(EnumFacing side)
	{
		int energyToTransfer;
		BlockPos dirPos = this.pos.offset(side);
		
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
			if (this.theBlock.canConnectTo(this.worldObj, this.pos, side))
			{
				IElectric blockToPower = (IElectric)this.worldObj.getBlockState(dirPos).getBlock();
				this.energy -= energyToTransfer - blockToPower.onPowered(this.worldObj, dirPos, energyToTransfer, side.getOpposite());
			}
		}
	}
	
	//<-------=======IInventory overrides=======------->
	
	/**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {}

    public void closeInventory(EntityPlayer player) {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] {};
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return false;
    }

    public int getField(int id)
    {
        return 0;
    }

    public void setField(int id, int value)
    {
        
    }

    public int getFieldCount()
    {
        return 0;
    }

    public void clear()
    {
        for (int i = 0; i < this.slots.length; ++i)
        {
            this.slots[i] = null;
        }
    }
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.slots.length;
    }
    
    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int index)
    {
        return this.slots[index];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int index, int count)
    {
        if (this.slots[index] != null)
        {
            ItemStack itemstack;

            if (this.slots[index].stackSize <= count)
            {
                itemstack = this.slots[index];
                this.slots[index] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.slots[index].splitStack(count);

                if (this.slots[index].stackSize == 0)
                {
                    this.slots[index] = null;
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
    public ItemStack getStackInSlotOnClosing(int index)
    {
        if (this.slots[index] != null)
        {
            ItemStack itemstack = this.slots[index];
            this.slots[index] = null;
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
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.slots[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.machine";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomInventoryName(String name)
    {
        this.customName = name;
    }
    
    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

	@Override
	public IChatComponent getDisplayName() {
		return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]));
	}
}
