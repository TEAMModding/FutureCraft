package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeDesert;

public class Venus extends Planet {

	public Venus(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 224.697f, 107.727f, 0.007f, 3.395f, 76.681f, 54.852f, 50.446f);
		this.physical = new PhysicalParameters(0.90521f, 12104f, 0, 243.02f, 137.45f, 178.78f, 300.22f);
		this.type = new PlanetTypeDesert();
		this.name = "Venus";
	}
}
