package com.team.futurecraft.block;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This is the main class that all energy-using blocks implement.
 * 
 * @author Joseph
 *
 */
public interface IElectric {
	/**
	 * Called when the block is trying to be powered. Any block implementing this interface should
	 * compute what to do with the energy (most commonly sending it to the tileEntity to deal with), and return
	 * any excess energy it cant hold. Also when you call this to power blocks, side must be the side of the block
	 * you are powering, facing the block powering it.
	 */
	public int onPowered(World world, BlockPos pos, int amount, EnumFacing side);
	
	/**
	 * Returns if this block can connect to another block
	 * to power or be powered by it. This should NEVER return true
	 * if the target block is not an instance of IElectric.
	 */
	public boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing side);
	
	/**
	 * Returns the energy value of this block.
	 */
	public int getEnergy(World world, BlockPos pos);
}
