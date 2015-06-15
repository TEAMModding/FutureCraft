package com.team.futurecraft.world;

import java.util.Random;

import com.team.futurecraft.BlockList;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * Handles ore generation for all planets.
 * 
 * todo: need to make a new system for planet ore generation
 * instead of hardcoding each planet.
 * 
 * @author Joseph
 *
 */
public class OreSpawn implements IWorldGenerator
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch (world.provider.getDimensionId())
		{
			case 0:
			{
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
			}
			case -10:
			{
				generateMoon(world, random, chunkX * 16, chunkZ * 16);
			}
			case -11:
			{
				generateMars(world, random, chunkX * 16, chunkZ * 16);
			}
		}
	}

	private void generateSurface(World world, Random random, int x, int z)
	{
		this.addOreSpawn(BlockList.malachite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 10, 1, 60, Blocks.stone);
		this.addOreSpawn(BlockList.cassiterite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 10, 1, 60, Blocks.stone);
		this.addOreSpawn(BlockList.bauxite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 10, 1, 50, Blocks.stone);
		//this.addOreSpawn(BlockList.graphite_ore, world, random, x, z, 16, 16, 9 + random.nextInt(7), 8, 1, 40, Blocks.stone);
	}
	
	private void generateMoon(World world, Random random, int x, int z)
	{
		this.addOreSpawn(BlockList.selena_malachite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 15, 15, 60, BlockList.selena_stone);
		this.addOreSpawn(BlockList.selena_cassiterite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 15, 15, 60, BlockList.selena_stone);
		this.addOreSpawn(BlockList.selena_bauxite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 15, 15, 60, BlockList.selena_stone);
	}
	
	private void generateMars(World world, Random random, int x, int z)
	{
		this.addOreSpawn(BlockList.desert_malachite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 10, 15, 60, BlockList.desert_stone);
		this.addOreSpawn(BlockList.desert_cassiterite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 10, 15, 60, BlockList.desert_stone);
		//this.addOreSpawn(GameRegistry.findBlock("futurecraft", "mars_iron_ore"), world, random, x, z, 16, 16, 9 + random.nextInt(7), 20, 15, 80, GameRegistry.findBlock("futurecraft", "mars_stone"));
		this.addOreSpawn(BlockList.desert_bauxite, world, random, x, z, 16, 16, 9 + random.nextInt(7), 10, 15, 50, BlockList.desert_stone);
	}

	/**
	 * Adds an Ore Spawn to Minecraft. Simply register all Ores to spawn with this method in your Generation method in your IWorldGeneration extending Class
	 * 
	 * @param The Block to spawn
	 * @param The World to spawn in
	 * @param A Random object for retrieving random positions within the world to spawn the Block
	 * @param An int for passing the X-Coordinate for the Generation method
	 * @param An int for passing the Z-Coordinate for the Generation method
	 * @param An int for setting the maximum X-Coordinate values for spawning on the X-Axis on a Per-Chunk basis
	 * @param An int for setting the maximum Z-Coordinate values for spawning on the Z-Axis on a Per-Chunk basis
	 * @param An int for setting the maximum size of a vein
	 * @param An int for the Number of chances available for the Block to spawn per-chunk
	 * @param An int for the minimum Y-Coordinate height at which this block may spawn
	 * @param An int for the maximum Y-Coordinate height at which this block may spawn
	 **/
	public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY, Block target)
	{
		assert maxY > minY : "The maximum Y must be greater than the Minimum Y";
		assert maxX > 0 && maxX <= 16 : "addOreSpawn: The Maximum X must be greater than 0 and less than 16";
		assert minY > 0 : "addOreSpawn: The Minimum Y must be greater than 0";
		assert maxY < 256 && maxY > 0 : "addOreSpawn: The Maximum Y must be less than 256 but greater than 0";
		assert maxZ > 0 && maxZ <= 16 : "addOreSpawn: The Maximum Z must be greater than 0 and less than 16";

		int diffBtwnMinMaxY = maxY - minY;
		for (int x = 0; x < chancesToSpawn; x++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(diffBtwnMinMaxY);
			int posZ = blockZPos + random.nextInt(maxZ);
			new WorldGenMinable(block.getDefaultState(), maxVeinSize, BlockHelper.forBlock(target)).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
}
