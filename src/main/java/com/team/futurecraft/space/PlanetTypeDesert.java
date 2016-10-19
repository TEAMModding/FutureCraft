package com.team.futurecraft.space;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.Noise;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.biome.BiomeDesert;
import com.team.futurecraft.biome.BiomePlanet;

import net.minecraft.block.Block;

public class PlanetTypeDesert extends PlanetType{
	@Override
	public Block getStoneBlock() {
		return BlockList.desert_stone;
	}

	@Override
	public int getHeight(int x, int z, Noise noise) {
		float normal = (noise.defaultNoise(new Vec3f(x, 0, z), 7, 0.0016f, 0.5f) * 40) + 80;
		float mountains = (noise.ridgedNoise(new Vec3f(x, 0, z), 10, 0.005f, 0.5f) * 60) + 60;
		float threshold = noise.thresholdNoise(new Vec3f(x, 0, z), 0.002f);
		return (int) (normal + (mountains * threshold));
	}

	@Override
	public Class<? extends BiomePlanet> getBiome() {
		return BiomeDesert.class;
	}
}
