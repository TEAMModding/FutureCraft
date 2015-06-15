package com.team.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * A WorldGenerator class for generating craters.
 * This seems to be causing a fair amount of lag, need to figure out a fix sometime.
 * 
 * @author Joseph
 *
 */
public class WorldGenCraters extends WorldGenerator
{
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) 
	{
		if (world.canBlockSeeSky(pos) && !world.isAirBlock(pos.down()))
		{
			int size = 10 + rand.nextInt(10);
			placeCrater(world, pos.getX(), pos.getY() + size / 2, pos.getZ(), size);
		}
		return true;
	}
	
	public void placeCrater(World world, int x, int y, int z, int size)
	{
		int dimensions = size * 2 + 1;
		Vec3 centerVec = new Vec3((double)x, (double)y, (double)z);
		for (int xCount = 0; xCount < dimensions; xCount++)
			for (int yCount = size + 1; yCount > 0; yCount--)
				for (int zCount = 0; zCount < dimensions; zCount++)
				{
					int newX = xCount + x - size;
					int newY = yCount + y - size;
					int newZ = zCount + z - size;
					Vec3 posVec = new Vec3((double)newX, (double)newY, (double)newZ);
					if (centerVec.distanceTo(posVec) < size && !world.isAirBlock(new BlockPos(newX, newY, newZ)))
					{
						IBlockState topBlock = world.getBiomeGenForCoords(new BlockPos(newY, 0, newZ)).topBlock;
						IBlockState fillerBlock = world.getBiomeGenForCoords(new BlockPos(newX, 0, newZ)).fillerBlock;
						BlockPos craterPos = new BlockPos(newX, newY, newZ);
						world.setBlockToAir(craterPos);
						world.setBlockState(craterPos.down(1), topBlock);
						world.setBlockState(craterPos.down(2), fillerBlock);
						world.setBlockState(craterPos.down(3), fillerBlock);
					}
				}
	}
	
}
