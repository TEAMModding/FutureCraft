package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.BlockAlloyFurnace;
import com.team.futurecraft.inventory.ContainerAlloyFurnace;
import com.team.futurecraft.recipe.AlloyRecipe;
import com.team.futurecraft.recipe.AlloyRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;

/**
 * Currently unused. Need to update the alloy furnace.
 * 
 * @author Joseph
 *
 */
public class TileEntityAlloyFurnace extends TileEntityLockable implements ISidedInventory, IUpdatePlayerListBox
{
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	private int cookTime;
    private String furnaceCustomName;
	public ItemStack[] furnaceItemStacks = new ItemStack[4];
	
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        this.furnaceBurnTime = compound.getShort("BurnTime");
        this.cookTime = compound.getShort("CookTime");
        
        if (compound.hasKey("CustomName", 8))
        {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        compound.setShort("BurnTime", (short)this.furnaceBurnTime);
        compound.setShort("CookTime", (short)this.cookTime);
        compound.setTag("Items", nbttaglist);
        
        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.furnaceCustomName);
        }
    }
    
    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    public void update()
    {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime != 0 || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null)
            {
                if (this.furnaceBurnTime == 0 && this.canSmelt())
                {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

                    if (this.furnaceBurnTime > 0)
                    {
                        flag1 = true;

                        if (this.furnaceItemStacks[1] != null)
                        {
                            --this.furnaceItemStacks[1].stackSize;

                            if (this.furnaceItemStacks[1].stackSize == 0)
                            {
                                this.furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.cookTime;

                    if (this.cookTime == 200)
                    {
                        this.cookTime = 0;
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }

            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                BlockAlloyFurnace.setState(this.furnaceBurnTime > 0, this.worldObj, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }
    
    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack p_145952_0_)
    {
        if (p_145952_0_ == null)
        {
            return 0;
        }
        else
        {
            Item item = p_145952_0_.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)
                {
                    return 150;
                }

                if (block.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (block == Blocks.coal_block)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(p_145952_0_);
        }
    }

    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[0] == null) return false;
        if (this.furnaceItemStacks[3] == null) return false;
    	
    	AlloyRecipe recipe = AlloyRegistry.getRecipeFromItems(this.furnaceItemStacks[0], this.furnaceItemStacks[3]);
            
        if (recipe == null) return false;
        if (this.furnaceItemStacks[2] == null) return true;
            
        ItemStack itemstack = recipe.getOutput();
            
        if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) return false;
        int result = furnaceItemStacks[2].stackSize + itemstack.stackSize;
        return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[2].getMaxStackSize();
    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = AlloyRegistry.getRecipeFromItems(this.furnaceItemStacks[0], this.furnaceItemStacks[3]).getOutput();

            if (this.furnaceItemStacks[0].stackSize == 1)
            	this.furnaceItemStacks[0] = null;
            else
            	this.furnaceItemStacks[0].stackSize--;
            
            if (this.furnaceItemStacks[3].stackSize == 1)
            	this.furnaceItemStacks[3] = null;
            else
            	this.furnaceItemStacks[3].stackSize--;
            if (this.furnaceItemStacks[2] != null)
            {
            	if (this.furnaceItemStacks[2].getItem() == itemstack.getItem() && this.furnaceItemStacks[2].getItemDamage() == itemstack.getItemDamage())
            		this.furnaceItemStacks[2].stackSize++;
            }
            else
            	this.furnaceItemStacks[2] = itemstack;
        }
    }
    
    //<=====IInventory overrides start=====>
    
    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }
    
    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks[index];
    }
    
    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int index, int count)
    {
        if (this.furnaceItemStacks[index] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[index].stackSize <= count)
            {
                itemstack = this.furnaceItemStacks[index];
                this.furnaceItemStacks[index] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.furnaceItemStacks[index].splitStack(count);

                if (this.furnaceItemStacks[index].stackSize == 0)
                {
                    this.furnaceItemStacks[index] = null;
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
        if (this.furnaceItemStacks[index] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[index];
            this.furnaceItemStacks[index] = null;
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
        boolean flag = stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]);
        this.furnaceItemStacks[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag)
        {
            this.cookTime = 0;
            this.markDirty();
        }
    }
    
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getName()
    {
        return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
    }
    
    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : p_70300_1_.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {}

    public void closeInventory(EntityPlayer player) {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return p_94041_1_ == 2 ? false : (p_94041_1_ == 1 ? isItemFuel(p_94041_2_) : true);
    }
    
    public static boolean isItemFuel(ItemStack p_145954_0_)
    {
        /**
         * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
         * fuel
         */
        return getItemBurnTime(p_145954_0_) > 0;
    }

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] {1};
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
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.water_bucket && item != Items.bucket)
            {
                return false;
            }
        }

        return true;
    }
    
    public String getGuiID()
    {
        return "futurecraft:alloy_furnace";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerAlloyFurnace(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
        }
    }

    public int getFieldCount()
    {
        return 3;
    }

    public void clear()
    {
        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            this.furnaceItemStacks[i] = null;
        }
    }
  //<=====IInventory overrides end=====>
}
