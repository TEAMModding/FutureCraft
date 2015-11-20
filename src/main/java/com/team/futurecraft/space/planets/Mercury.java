package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeSelena;

public class Mercury extends Planet {

	public Mercury(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 87.95f, 57.663f, 0.206f, 7.005f, 48.332f, 29.124f, 174.795f);
		this.physical = new PhysicalParameters(0.37773f, 4480f, 0, 58.646f, 291.2f, 7.01f, 48.42f);
		this.type = new PlanetTypeSelena(0.5f);
		this.name = "Mercury";
	}
}
