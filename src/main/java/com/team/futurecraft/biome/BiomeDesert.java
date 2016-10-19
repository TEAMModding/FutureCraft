package com.team.futurecraft.biome;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.space.Planet;

/**
 * The Biome class for mars, not really much here.
 * @author Joseph
 *
 */
public class BiomeDesert extends BiomePlanet {
	public BiomeDesert(int id, Planet planet) {
        super(id, planet);
        this.setBiomeName("desert");
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
