package com.team.futurecraft.space;

import net.minecraft.block.Block;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.biome.BiomeList;

public class WorldTypeDesert extends WorldType{
	@Override
	public Block getStoneBlock() {
		return BlockList.desert_stone;
	}

	@Override
	public BiomePlanet getBiome() {
		return BiomeList.mars;
	}

}
