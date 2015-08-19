package com.team.futurecraft.block;

import java.util.ArrayList;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.tileentity.TileEntityWire;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class BlockWire extends BlockContainer implements IElectric {

	public BlockWire(String name) {
		super(Material.cloth);
		this.setUnlocalizedName(name);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
		this.setStepSound(soundTypeCloth);
		GameRegistry.registerBlock(this, name);
	}

	@Override
	public int getRenderType() {
		return 1000;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (world.getBlockState(pos.offset(side)).getBlock() instanceof IElectric) return true;
		else return false;
	}

	@Override
	public int onPowered(World world, BlockPos pos, int amount, EnumFacing side) {
		return ((TileEntityWire)world.getTileEntity(pos)).power(amount);
	}
	
	/**
	 * Returns the <code>EnumFacing</code> equivelants of
	 * the connected blocks.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Object[] getConnectedBlocks(World world, BlockPos pos) {
		ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();
		
		if (this.canConnectTo(world, pos, EnumFacing.NORTH)) sides.add(EnumFacing.NORTH);
		if (this.canConnectTo(world, pos, EnumFacing.SOUTH)) sides.add(EnumFacing.SOUTH);
		if (this.canConnectTo(world, pos, EnumFacing.EAST)) sides.add(EnumFacing.EAST);
		if (this.canConnectTo(world, pos, EnumFacing.WEST)) sides.add(EnumFacing.WEST);
		if (this.canConnectTo(world, pos, EnumFacing.UP)) sides.add(EnumFacing.UP);
		if (this.canConnectTo(world, pos, EnumFacing.DOWN)) sides.add(EnumFacing.DOWN);
		
		sides.trimToSize();
		return sides.toArray();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityWire(100, this);
	}

	@Override
	public int getEnergy(World world, BlockPos pos) {
		return ((TileEntityWire)world.getTileEntity(pos)).getEnergy();
	}
}
