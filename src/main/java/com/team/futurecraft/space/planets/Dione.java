package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Dione extends Planet {

	public Dione(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 2.7369f, 0.377394954f, 0f, 0.002f, 57.741f, 173.999f, 109.204f);
		this.physical = new PhysicalParameters(0.023954f, 1118f, 0f, 2.7369f, 0f, 0.002f, 57.741f);
		this.type = new PlanetTypeFrozen();
		this.name = "Dione";
	}
}
