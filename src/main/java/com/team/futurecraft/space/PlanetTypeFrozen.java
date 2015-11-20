package com.team.futurecraft.space;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.Noise;
import com.team.futurecraft.biome.BiomeFrozen;
import com.team.futurecraft.biome.BiomePlanet;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;

public class PlanetTypeFrozen extends PlanetType {
	@Override
	public Block getStoneBlock() {
		return BlockList.dirty_ice;
	}
	
	@Override
	public int getHeight(int x, int z, Noise noise) {
		float normal = 80 - (noise.ridgedNoise(new Vec3(x, 0, z),10, 0.0016f, 0.5f) * 60);
		return (int)normal;
	}

	@Override
	public Class<? extends BiomePlanet> getBiome() {
		return BiomeFrozen.class;
	}
}
