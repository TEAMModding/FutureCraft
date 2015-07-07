package com.team.futurecraft.space;

import net.minecraft.block.Block;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.biome.BiomeList;

public class WorldTypeFrozen extends WorldType {
	@Override
	public Block getStoneBlock() {
		return BlockList.dirty_ice;
	}

	@Override
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN;
	}
}
