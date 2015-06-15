package pianoman.futurecraft.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import pianoman.futurecraft.block.BlockGenerator;

/**
 * Currently unused. Need to update the generator.
 * 
 * @author Joseph
 *
 */
public class TileEntityGenerator extends TileEntityMachine
{
	public int burnTime;
	public int currentItemBurnTime;
	
	public TileEntityGenerator(BlockGenerator block)
	{
		super(10, 500, block, false, 1);
	}
	
	public void readFromNBT(NBTTagCompound tag)
    {
		super.readFromNBT(tag);
		this.burnTime = tag.getInteger("BurnTime");
		this.currentItemBurnTime = getItemBurnTime(this.slots[0]);
    }
	
	public void writeToNBT(NBTTagCompound tag)
    {
		super.writeToNBT(tag);
		tag.setInteger("BurnTime", burnTime);
    }
	
	public void updateEntity()
	{	
		boolean flag = false;
		super.updateEntity();
		
		if (true)
        {
			if (this.burnTime == 0)
			{	
				if (this.slots[0] != null)
				{
					if (getItemBurnTime(this.slots[0]) != 0)
					{
						flag = true;
						this.burnTime = getItemBurnTime(this.slots[0]);
						--this.slots[0].stackSize;

						if (this.slots[0].stackSize == 0)
						{
							this.slots[0] = slots[0].getItem().getContainerItem(slots[0]);
						}
					}
				}
			}
			else
			{
				this.burnTime--;
				if (!this.isFull())
					this.energy++;
				BlockGenerator.updateFurnaceBlockState(isBurning(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				flag = true;
			}
        }
		if (flag)
		{
			this.markDirty();
		}
    }
	
	private void useFuel()
	{
		if (this.slots[0].stackSize > 1)
			this.slots[0].splitStack(1);
	}
	
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
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return GameRegistry.getFuelValue(p_145952_0_);
        }
    }
	
	@SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.burnTime * p_145955_1_ / this.currentItemBurnTime;
    }
	
	public boolean isBurning()
    {
        return this.burnTime > 0;
    }
	
	public String getInventoryName()
    {
        return "container.generator";
    }
}
