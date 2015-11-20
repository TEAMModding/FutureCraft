package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Rhea extends Planet {

	public Rhea(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 4.5175f, 0.527032954f, 0.001f, 0.327f, 1.095f, 205.869f, 109.204f);
		this.physical = new PhysicalParameters(0.026833f, 1528f, 0, 4.175f, 0, 0.327f, 1.095f);
		this.type = new PlanetTypeFrozen();
		this.name = "Rhea";
	}
}
