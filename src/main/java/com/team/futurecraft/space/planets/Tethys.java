package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Tethys extends Planet {

	public Tethys(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 1.8878f, 0.294656061f, 0f, 0.168f, 149.174f, 149.166f, 28.755f);
		this.physical = new PhysicalParameters(0.015024f, 1059.8f, 0f, 1.8878f, 0f, 0.168f, 149.174f);
		this.type = new PlanetTypeFrozen();
		this.name = "Tethys";
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}
}
