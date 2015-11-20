package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Iapetus extends Planet {

	public Iapetus(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 79.33f, 3.561252389f, 0.028f, 7.57f, 75.583f, 275.85f, 350.306f);
		this.physical = new PhysicalParameters(0.026435f, 1436f, 0, 79.33f, 0, 15.5f, 75.583f);
		this.type = new PlanetTypeFrozen();
		this.name = "Iapetus";
	}
}
