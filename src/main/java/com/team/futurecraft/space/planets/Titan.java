package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Titan extends Planet {

	public Titan(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 15.945f, 1.221833655f, 0.029f, 1.634f, 44.046f, 172.749f, 192.132f);
		this.physical = new PhysicalParameters(0.13351f, 5149.82f, 0, 15.945f, 0, 1.634f, 44.046f);
		this.type = new PlanetTypeFrozen();
		this.name = "Titan";
	}
}
