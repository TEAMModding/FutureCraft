package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Mimas extends Planet {

	public Mimas(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 0.94242f, 0.18552752f, 0.021f, 1.566f, 177.459f, 332.864f, 100.173f);
		this.physical = new PhysicalParameters(0.005156f, 404.5f, 0f, 0.94242f, 0f, 1.566f, 177.459f);
		this.type = new PlanetTypeFrozen();
		this.name = "Mimas";
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}
}
