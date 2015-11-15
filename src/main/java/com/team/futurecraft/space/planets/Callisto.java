package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;

public class Callisto extends Planet {

	public Callisto(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 16.689f, 1.882974825f, 0.007f, 0.281f, 323.265f, 12.668f, -250.842f);
		this.physical = new PhysicalParameters(0.12612f, 4820.6f, 0, 16.689f, 0, 0.281f, 323.265f);
		this.name = "Callisto";
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}
}
