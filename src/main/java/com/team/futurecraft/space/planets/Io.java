package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Io extends Planet {

	public Io(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 1.769f, 0.421594364f, 0.004f, 0.04f, 312.981f, -215.246f, 8.989f);
		this.physical = new PhysicalParameters(0.18328f, 3643.2f, 0, 1.7691f, 0, 0.04f, 312.981f);
		this.type = new PlanetTypeFrozen();
		this.name = "Io";
	}
}

