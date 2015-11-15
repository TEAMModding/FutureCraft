package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Titania extends Planet {

	public Titania(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 8.706f, 0.435794174f, 0.002f, 0.1f, 0, 0, 30f);
		this.physical = new PhysicalParameters(0.038631f, 1577.8f, 0, 8.706f, 0, 0.1f, 0);
		this.type = new PlanetTypeFrozen();
		this.name = "Titania";
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}
}
