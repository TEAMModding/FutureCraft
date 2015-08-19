package com.team.futurecraft.biome;
import java.util.Random;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.world.WorldGenCraters;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * The Biome gen class for the moon, craters are generated here.
 * 
 * @author Joseph
 *
 */
public class BiomeSelena extends BiomePlanet {
    public BiomeSelena(int p_i1977_1_) {
        super(p_i1977_1_, BlockList.selena_stone);
        this.spawnableCreatureList.clear();
        this.topBlock = BlockList.selena_dirt.getDefaultState();
        this.fillerBlock = BlockList.selena_dirt.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.generateLakes = false;
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
    }

	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		WorldGenCraters craters = new WorldGenCraters();
		
		for (int k = 0; k < 10; ++k) {
            int l = pos.getX() + rand.nextInt(16) + 8;
            int i1 = pos.getZ() + rand.nextInt(16) + 8;
            int j1 = rand.nextInt(world.getHorizon(new BlockPos(l, 0, i1)).getY() + 32);
            craters.generate(world, rand, new BlockPos(l, j1, i1));
        }
	}
}
