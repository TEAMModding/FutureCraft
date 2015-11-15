package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;

public class Ariel extends Planet {
	public Ariel(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 2.52f, 0.191197444f, 0.003f, 0.31f, 0, 0, 56f);
		this.physical = new PhysicalParameters(0.027434f, 1157.8f, 0, 2.52f, 0, 0.31f, 0);
		this.name = "Ariel";
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}
}
