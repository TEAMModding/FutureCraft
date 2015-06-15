package com.team.futurecraft.block;

import com.team.futurecraft.CommonUtils;
import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.tileentity.TileEntityMachine;
import com.team.futurecraft.tileentity.TileEntityWire;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * The Machine class, currently updating.
 * Do not mess with this code until i've finished updating it.
 * 
 * @author Joseph
 *
 */
public class Machine extends BlockContainer implements IElectric
{
	public IIcon input;
	public IIcon output;
	public IIcon front; 
	public IIcon top; 
	private boolean isFull;
	private String frontString;
	
	public Machine(boolean full, String frontTexture) 
	{
		super(Material.iron);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
		this.isFull = full;
		this.frontString = frontTexture;
	}
	
	public Machine()
	{
		this(false, "machine");
	}
	
	public EnumMachineSetting getSide(int side, int meta)
	{
		return EnumMachineSetting.energyInput;
	}
	
	public int getRenderType()
	{
		return 0;
	}
	
	 @Override
	public IIcon getIcon(int side, int meta) 
	{
		return side == 1 ? this.top : (side == 0 ? this.top : (side != meta ? this.blockIcon : this.getFrontIcon(side, meta)));
	}
	 
	public IIcon getFrontIcon(int side, int meta)
	{
		return this.front;
	}

	public void registerBlockIcons(IIconRegister iconRegister)
	{
		 this.front = iconRegister.registerIcon(this.frontString);
		 this.top = iconRegister.registerIcon("machine");
		 this.blockIcon = iconRegister.registerIcon("machine");
	}

	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection side) 
	{
		if (CommonUtils.getBlockNextTo(world, x, y, z, side) instanceof IElectric)
			return true;
		else
			return false;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityMachine(10, 10000, this, isFull, 0);
	}

	@Override
	public int onPowered(World world, int x, int y, int z, int amount, ForgeDirection side) 
	{
		TileEntityMachine tileEntity = (TileEntityMachine)world.getTileEntity(x, y, z);
		if (tileEntity != null)
			return tileEntity.tryToPower(amount, side);
		else
			return amount;
	}
	
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }

        if (l == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }

        if (l == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }

        if (l == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
        }
    }
	
	public int getEnergy(World world, int x, int y, int z) {
		return ((TileEntityMachine)world.getTileEntity(x, y, z)).energy;
	}
}