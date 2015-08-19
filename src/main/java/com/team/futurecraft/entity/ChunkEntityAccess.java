package com.team.futurecraft.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public class ChunkEntityAccess implements IBlockAccess {
	private ChunkEntity entity;
	
	public ChunkEntityAccess(ChunkEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public TileEntity getTileEntity(BlockPos pos) {
		return null;
	}

	@Override
	public int getCombinedLight(BlockPos pos, int par2) {
		return this.entity.worldObj.getCombinedLight(this.entity.getPosition(), par2);
	}

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if (x >= 0 && x < 16) {
			if (y >= 0 && y < 16) {
				if (z >= 0 && z < 16) {
					return entity.blocks[x][y][z];
				}
			}
		}
		return Blocks.air.getDefaultState();
	}

	@Override
	public boolean isAirBlock(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if (x >= 0 && x < 16) {
			if (y >= 0 && y < 16) {
				if (z >= 0 && z < 16) {
					return entity.blocks[x][y][z] == Blocks.air.getDefaultState();
				}
			}
		}
		return true;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
		return this.entity.worldObj.getBiomeGenForCoords(entity.getPosition());
	}

	@Override
	public boolean extendedLevelsInChunkCache() {
		return false;
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction) {
		return 0;
	}

	@Override
	public WorldType getWorldType() {
		return WorldType.DEFAULT;
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if (x >= 0 && x < 16) {
			if (y >= 0 && y < 16) {
				if (z >= 0 && z < 16) {
					return entity.blocks[x][y][z].getBlock().isSideSolid(this, pos, side);
				}
			}
		}
		return Blocks.air.isSideSolid(this, pos, side);
	}

}
