package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeSelena;

public class Moon extends Planet {

	public Moon(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 27.322f, 0.379666035f, 0.055f, 5.15f, 125.08f, 318.15f, 135.27f);
		this.physical = new PhysicalParameters(0.16579f, 3474.8f, 0, 27.322f, 0, 5.15f, 125.08f);
		this.type = new PlanetTypeSelena(0);
		this.name = "Moon";
	}
}
