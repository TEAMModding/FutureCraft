package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Oberon extends Planet {

	public Oberon(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 13.463f, 0.583592198f, 0.001f, 0.1f, 0, 0, 150f);
		this.physical = new PhysicalParameters(0.035367f, 1522.8f, 0, 13.463f, 0, 0.1f, 0);
		this.type = new PlanetTypeFrozen();
		this.name = "oberon";
	}
}
