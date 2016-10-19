package com.team.futurecraft.biome;

import com.team.futurecraft.space.Planet;

import net.minecraft.init.Blocks;

public class BiomeTerra extends BiomePlanet {
	public BiomeTerra(int id, Planet planet) {
        super(id, planet);
        this.setBiomeName("terra");
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.generateLakes = false;
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
    }
}
