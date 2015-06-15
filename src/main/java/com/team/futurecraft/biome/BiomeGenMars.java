package com.team.futurecraft.biome;

import com.team.futurecraft.BlockList;

/**
 * The Biome class for mars, not really much here.
 * @author Joseph
 *
 */
public class BiomeGenMars extends BiomeGenPlanet
{
	public BiomeGenMars(int p_i1977_1_)
    {
        super(p_i1977_1_, BlockList.desert_stone);
        this.spawnableCreatureList.clear();
        this.topBlock = BlockList.desert_dirt.getDefaultState();
        this.fillerBlock = BlockList.desert_dirt.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.generateLakes = false;
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
    }
}
