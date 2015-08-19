package com.team.futurecraft.space;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;

import com.team.futurecraft.Noise;
import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;

public class PlanetTypeTerra extends PlanetType {
	@Override
	public Block getStoneBlock() {
		return Blocks.stone;
	}
	
	@Override
	public int getHeight(int x, int z, Noise noise) {
		float normal = (noise.defaultNoise(new Vec3(x, 0, z), 10, 0.0016f, 0.5f) * 40) + 80;
		float mountains = (noise.ridgedNoise(new Vec3(x, 0, z), 10, 0.005f, 0.5f) * 60) + 60;
		float threshold = noise.thresholdNoise(new Vec3(x, 0, z), 0.002f);
		return (int) (normal + (mountains * threshold));
	}
}
