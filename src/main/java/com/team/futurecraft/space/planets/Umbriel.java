package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Umbriel extends Planet {

	public Umbriel(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 4.144f, 0.265996444f, 0.005f, 0.36f, 0, 0, 280f);
		this.physical = new PhysicalParameters(0.023323f, 1169.4f, 0, 4.133f, 0, 0.36f, 0);
		this.type = new PlanetTypeFrozen();
		this.name = "Umbriel";
	}
}
