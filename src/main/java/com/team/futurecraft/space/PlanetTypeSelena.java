package com.team.futurecraft.space;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.Noise;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.biome.BiomeList;

public class PlanetTypeSelena extends PlanetType {
	@Override
	public Block getStoneBlock() {
		return BlockList.selena_stone;
	}
	
	@Override
	public int getHeight(int x, int z, Noise noise) {
		float normal = (noise.defaultNoise(new Vec3(x, 0, z), 7, 0.0016f, 0.5f) * 40) + 80;
		return (int)normal;
	}
}
