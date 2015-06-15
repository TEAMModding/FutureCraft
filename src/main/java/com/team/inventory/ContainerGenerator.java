package com.team.inventory;

import com.team.tileentity.TileEntityGenerator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Currently unused. Need to update the generator.
 * 
 * @author Joseph
 *
 */
public class ContainerGenerator extends Container
{
	private TileEntityGenerator tileEntity;
	private int lastBurnTime;
    private int lastItemBurnTime;
	
	public ContainerGenerator(InventoryPlayer player, TileEntityGenerator tileEntity)
    {
		this.tileEntity = tileEntity;
		this.addSlotToContainer(new Slot(tileEntity, 0, 80, 40));
		
		int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
        }
    }
	
	public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return this.tileEntity.isUseableByPlayer(p_75145_1_);
    }
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }
	
	public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
        p_75132_1_.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
    }
	
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            
            if (this.lastBurnTime != this.tileEntity.burnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
            }

            if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
            }
        }

        this.lastBurnTime = this.tileEntity.burnTime;
        this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int p_75137_1_, int p_75137_2_)
    {
        if (p_75137_1_ == 1)
        {
            this.tileEntity.burnTime = p_75137_2_;
        }

        if (p_75137_1_ == 2)
        {
            this.tileEntity.currentItemBurnTime = p_75137_2_;
        }
    }
}
