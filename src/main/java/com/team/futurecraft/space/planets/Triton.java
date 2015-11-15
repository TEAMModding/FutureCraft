package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Triton extends Planet {

	public Triton(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62755776000L, 5.8769f, 0.354760543f, 0, 156.826f, 147.899f, 293.092f, 315.726f);
		this.physical = new PhysicalParameters(0.078066f, 2706.8f, 0, 5.8769f, 0, 156.826f, 147.899f);
		this.type = new PlanetTypeFrozen();
		this.name = "Triton";
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}
}
