package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Ganymede extends Planet {

	public Ganymede(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 7.155f, 1.069985695f, 0.002f, 0.195f, 119.841f, 68.99f, -67.625f);
		this.physical = new PhysicalParameters(0.14572f, 5262.4f, 0, 7.1546f, 0, 0.195f, 119.841f);
		this.type = new PlanetTypeFrozen();
		this.name = "Ganymede";
	}
}
