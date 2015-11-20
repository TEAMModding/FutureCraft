package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Miranda extends Planet {

	public Miranda(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 1.413f, 0.129798265f, 0.003f, 4.220f, 0, 0, 120f);
		this.physical = new PhysicalParameters(0.0080481f, 471f, 0, 1.413f, 0, 4.22f, 0);
		this.type = new PlanetTypeFrozen();
		this.name = "Miranda";
	}
}
