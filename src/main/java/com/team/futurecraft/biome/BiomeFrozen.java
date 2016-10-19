package com.team.futurecraft.biome;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.space.Planet;

public class BiomeFrozen extends BiomePlanet {
	public BiomeFrozen(int id, Planet planet) {
        super(id, planet);
        this.setBiomeName("frozen");
        this.spawnableCreatureList.clear();
        this.topBlock = BlockList.dirty_ice.getDefaultState();
        this.fillerBlock = BlockList.dirty_ice.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.generateLakes = false;
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
    }
}
