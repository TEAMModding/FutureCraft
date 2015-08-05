package com.team.futurecraft.block;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.tileentity.ElectricalBase;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBatteryNew extends BlockContainer {
	public BlockBatteryNew() {
		super(Material.iron);
		this.setUnlocalizedName("battery_new");
		this.setCreativeTab(FutureCraft.tabFutureCraft);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new ElectricalBase();
	}
	
	public int getRenderType() {
        return 3;
    }
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		ElectricalBase te = ((ElectricalBase)world.getTileEntity(pos));
		String sideValue;
		if (world.isRemote)
			sideValue = "CLIENT";
		else
			sideValue = "SERVER";
		if (player.isSneaking() && !world.isRemote) {
			te.energy++;
			te.markDirty();
			System.out.println(sideValue + ": increased energy to: " + te.energy);
		}
		else  if (!player.isSneaking()){
			System.out.println(sideValue + ": energy: " + te.energy);
		}
				
		
		return true;
	}
}
