package com.team.futurecraft.space;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.Noise;

public class PlanetTypeSelena extends PlanetType {
	float roughness;
	
	public PlanetTypeSelena(float roughness) {
		this.roughness = roughness;
	}
	
	@Override
	public Block getStoneBlock() {
		return BlockList.selena_stone;
	}
	
	@Override
	public int getHeight(int x, int z, Noise noise) {
		float normal = (noise.defaultNoise(new Vec3(x, 0, z), 7, 0.0016f, 0.5f) * 40) + 80;
		float rough = (noise.ridgedNoise(new Vec3(x, 0, z), 10, 0.005f, 0.5f) * 40);
		return (int)(normal + (rough * roughness));
	}
}
