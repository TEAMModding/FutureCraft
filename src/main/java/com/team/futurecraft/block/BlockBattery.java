package com.team.futurecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBattery extends Machine {
	
	public BlockBattery(boolean full, String name) {
		super(full, name);
	}
	
	public EnumMachineSetting getSide(EnumFacing side) {
		if (side == EnumFacing.NORTH) return EnumMachineSetting.energyOutput;
		return EnumMachineSetting.energyInput;
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui("futurecraft", 103, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
