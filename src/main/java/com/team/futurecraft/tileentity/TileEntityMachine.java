package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.EnumMachineSetting;
import com.team.futurecraft.block.Machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

/**
 * This is the class that implements EnergyContainer and adds functionality
 * usually needed in machines without having to implement them yourself, such as handling
 * in/out machine settings, transferring energy based on them, and dealing with inventory slots.
 */
public abstract class TileEntityMachine extends EnergyContainer implements ISidedInventory {
	public ItemStack[] slots;
	public int[] fields;
	private String customName;
	
	public TileEntityMachine(int energyTransfer, int maxEnergy, int slots, int fields) {
		super(maxEnergy, energyTransfer);
		this.slots = new ItemStack[slots];
		this.fields = new int[fields];
	}
	
	/**
	 * reads TE data from an NBT tag for world loading. You'll need to read data tags
	 * in here for any data you wrote in writeToNBT.
	 * 
	 * Just like EnergyContainer, you MUST call super(tag) if you override this.
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length) {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}

	/**
	 * Saves TE data to an NBT tag for world saving. You'll need to write data tags
	 * in here for any data you don't want lost when the game is closed.
	 * 
	 * Just like EnergyContainer, you MUST call super(tag) if you override this.
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTTagList nbttaglist = new NBTTagList();
	    for (int i = 0; i < this.slots.length; ++i) {
	        if (this.slots[i] != null) {
	            NBTTagCompound nbttagcompound = new NBTTagCompound();
	            nbttagcompound.setByte("Slot", (byte)i);
	            this.slots[i].writeToNBT(nbttagcompound);
	            nbttaglist.appendTag(nbttagcompound);
	        }
	    }
	    tag.setTag("Items", nbttaglist);
	}
	
	/**
	 * Rotates a face by another face and returns the face rotated by the face!
	 * example: west of west will be south. Used for computing in/out sides on rotated machines. 
	 */
	private EnumFacing rotatedFace(EnumFacing face1, EnumFacing face2) {
		if (face2 == EnumFacing.NORTH) return face1;
		if (face2 == EnumFacing.SOUTH) return face1.getOpposite();
		if (face2 == EnumFacing.WEST) return face1.rotateY();
		if (face2 == EnumFacing.EAST) return face1.rotateY().rotateY().rotateY();
		
		return face1;
	}

	/**
	 * Updates the TE. This is usually what every TE will override, and doing so, 
	 * MUST call super() or else this class cant do it's job.
	 */
	@Override
	public void update() {
		if (this.worldObj.isRemote) { //we don't do anything on client
			return;
		}
		
		IBlockState state = this.worldObj.getBlockState(this.pos);
		Machine theBlock = (Machine)state.getBlock();
		
		int i = 0;
		while (this.energy > this.energyTransferred && i < 4) {
			if (theBlock.getSide(rotatedFace(EnumFacing.getHorizontal(i), (EnumFacing)state.getValue(Machine.FACING))) == EnumMachineSetting.energyOutput) {
				this.powerNeighbor(EnumFacing.getHorizontal(i));
				this.markDirty();
			}
			i++;
		}
	}
	
	/**
	 * Tries to power any connected blocks. Called by update() for each side set to output every tick.
	 */
	private void powerNeighbor(EnumFacing side) {
		int energyToTransfer;
		BlockPos dirPos = this.pos.offset(side);
		
		if (this.energy > 0) {
			if (this.energy < this.energyTransferred) {
				energyToTransfer = this.energy;
			}
			else {
				energyToTransfer = this.energyTransferred;
			}
			if (this.canConnectTo(this.worldObj, this.pos, side)) {
				TileEntity te = this.worldObj.getTileEntity(dirPos);
				this.removeEnergy(energyToTransfer - ((IElectric)te).onPowered(this.worldObj, dirPos, energyToTransfer, side.getOpposite()));
			}
		}
	}
	
	@Override
	public int onPowered(World world, BlockPos pos, int amount, EnumFacing side) {
		IBlockState state = this.worldObj.getBlockState(this.pos);
		Machine theBlock = (Machine)state.getBlock();
		
		if (theBlock.getSide(rotatedFace(side, (EnumFacing)state.getValue(Machine.FACING))) == EnumMachineSetting.energyInput) {
			if (this.energy < this.maxEnergy) {
				if (this.maxEnergy - this.energy >= amount) {
					this.addEnergy(amount);
					return 0;
				}
				else {
					amount = this.maxEnergy - this.energy;
					this.setEnergy(this.maxEnergy);
					return amount;
				}
			}
		}
		
		return amount;
	}
	
	//==================================================
	//<-------=======IInventory overrides=======------->
	//==================================================
	
	/**
     * Returns if the player is close enough to use this.
     */
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {}

    public void closeInventory(EntityPlayer player) {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    /**
     * Returns slots that automation ties hoppers to from a certain side.
     */
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] {};
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    /*
     * Fields are usually used by Containers in order to synchronize the client/server data on this TileEntity. getField is called
     * from the server so it knows what to send to the client. setField is then called on the client after the packet is received, then it update's
     * it's internal variables accordingly. getFieldCount is just used to that the server knows how much it needs to send.
     */
    
    public int getField(int id) {
    	if (id == 0)
    		return this.energy; //Energy is always 0.
    	
    	else if (id > 0) {
    		return fields[id + 1]; //Offset to make room for energy.
    	}
    	
    	return 0;
    }
    
    public void setField(int id, int value) {
    	if (id == 0)
    		this.energy = value; //Energy is always 0.
    	
    	else if (id > 0) {
    		fields[id + 1] = value; //Offset to make room for energy.
    	}
    }

    public int getFieldCount() {
        return fields.length + 1; //Remember energy right?
    }

    /**
     * Clears the inventory, pretty straightforward.
     */
    public void clear() {
        for (int i = 0; i < this.slots.length; ++i) {
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
    public ItemStack getStackInSlot(int index) {
        return this.slots[index];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        if (this.slots[index] != null) {
            ItemStack itemstack;

            if (this.slots[index].stackSize <= count) {
                itemstack = this.slots[index];
                this.slots[index] = null;
                return itemstack;
            }
            else {
                itemstack = this.slots[index].splitStack(count);

                if (this.slots[index].stackSize == 0) {
                    this.slots[index] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int index) {
        if (this.slots[index] != null) {
            ItemStack itemstack = this.slots[index];
            this.slots[index] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.slots[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.machine";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    /**
     * Sets the custom name of this TE.
     */
    public void setCustomInventoryName(String name) {
        this.customName = name;
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Gets this TE's name in chat, for those rare occasions when you may make TE's talk...
     */
	@Override
	public IChatComponent getDisplayName() {
		return (IChatComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]));
	}
}
