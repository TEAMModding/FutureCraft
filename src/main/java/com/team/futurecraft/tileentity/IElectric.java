package com.team.futurecraft.tileentity;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This is the main class that all energy-using TileEntities implement.
 * 
 * It is essentially used as a communication interface between different IElectric TileEntities throughout the world.
 * When a n electrical TileEntity usually ticks, it checks all surrounding blocks to see if they are instances of IElectric, if
 * they are, then it uses these functions to communicate with it. This allows electrical blocks to communicate with each other without
 * having to know exactly what block type they're communicating with.
 * 
 * You generally won't ever implement this class directly. Instead, use EnergyContainer.
 * 
 * @author Joseph
 *
 */
public interface IElectric {
	/**
	 * Called when the TileEnytity is trying to be powered. Any TileEntity implementing this interface should
	 * compute what to do with the energy (usually storing it), and return
	 * any excess energy it cant hold. When calling this, remember that side is the side this TileEntity is
	 * being powered from, usually the inverse of the caller's output side.
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
