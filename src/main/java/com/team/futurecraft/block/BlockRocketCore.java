package com.team.futurecraft.block;

import com.team.futurecraft.entity.ChunkEntity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockRocketCore extends SimpleBlock {
	public BlockRocketCore(String name) {
		super(Material.iron, name);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (!worldIn.isRemote) {
			ChunkEntity chunk = new ChunkEntity(worldIn);
			chunk.setPosition(pos.getX(), pos.getY(), pos.getZ());
			System.out.println(placer.rotationYaw);
			chunk.rotationYaw = placer.rotationYaw;
			worldIn.spawnEntityInWorld(chunk);
		}
		return worldIn.getBlockState(pos);
	}
	
	
}
