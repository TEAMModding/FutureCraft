package com.team.futurecraft.block;

import com.team.futurecraft.tileentity.TileEntityMachine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBattery extends Machine {
	private boolean isFull;
	
	public BlockBattery(boolean full, String name) {
		super(name);
		this.isFull = full;
	}
	
	public EnumMachineSetting getSide(EnumFacing side) {
		if (side == EnumFacing.NORTH) return EnumMachineSetting.energyOutput;
		return EnumMachineSetting.energyInput;
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui("futurecraft", 103, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachine(10, 10000, this.getStateFromMeta(meta), isFull, 0);
	}
}
