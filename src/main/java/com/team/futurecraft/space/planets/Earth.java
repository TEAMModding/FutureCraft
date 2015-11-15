package com.team.futurecraft.space.planets;

import com.team.futurecraft.Vec4;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;

public class Earth extends Planet {

	public Earth(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 27.322f, 0.00470989f, 0.055f, 5.15f, 125.08f, 138.15f, 135.27f);
		this.physical = new PhysicalParameters(1, 12756.28f, 0, 1, -79.5f, 23.439f, 180f);
		this.name = "Earth";
		this.atmosphere = new Vec4(0.3, 0.6, 1, 0.7);
	}

	public BiomePlanet getBiome() {
		return null;
	}
}
