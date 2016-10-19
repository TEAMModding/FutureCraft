package com.team.futurecraft.tileentity;

import com.team.futurecraft.block.BlockGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGenerator extends TileEntityMachine {
	public int burnTime;
	public int currentItemBurnTime;
	
	public TileEntityGenerator(IBlockState block) {
		super(10, 500, block, false, 1);
	}
	
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.burnTime = tag.getInteger("BurnTime");
		this.currentItemBurnTime = getItemBurnTime(this.getStackInSlot(0));
    }
	
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("BurnTime", burnTime);
    }
	
	public void update() {	
		boolean flag = false;
		super.update();
		
		if (true) {
			if (this.burnTime == 0) {	
				if (this.getStackInSlot(0) != null) {
					if (getItemBurnTime(this.getStackInSlot(0)) != 0) {
						flag = true;
						this.burnTime = getItemBurnTime(this.getStackInSlot(0));
						--this.getStackInSlot(0).stackSize;

						if (this.getStackInSlot(0).stackSize == 0) {
							this.setInventorySlotContents(0, this.getStackInSlot(0).getItem().getContainerItem(this.getStackInSlot(0)));
						}
					}
				}
			}
			else {
				this.burnTime--;
				if (!this.isFull()) this.addEnergy(1);
				BlockGenerator.setState(isBurning(), this.worldObj, this.pos);
				flag = true;
			}
        }
		if (flag) {
			this.markDirty();
		}
    }
	
	/**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack stack) {
        if (stack == null) {
            return 0;
        }
        else {
            Item item = stack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab) {
                    return 150;
                }

                if (block.getMaterial() == Material.wood) {
                    return 300;
                }

                if (block == Blocks.coal_block) {
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
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
        }
    }
	
	@SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }

        return this.burnTime * p_145955_1_ / this.currentItemBurnTime;
    }
	
	public boolean isBurning() {
        return this.burnTime > 0;
    }
	
	public String getInventoryName() {
        return "container.generator";
    }
}
