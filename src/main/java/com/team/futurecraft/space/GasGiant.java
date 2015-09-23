package com.team.futurecraft.space;

import com.team.futurecraft.biome.BiomePlanet;

import net.minecraft.util.Vec3;

public abstract class GasGiant extends Planet {

	public GasGiant(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return null;
	}
	
	public boolean isLandable() {
		return false;
	}
	
	public PlanetType getWorldType() {
		return null;
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
