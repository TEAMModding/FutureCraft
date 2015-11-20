package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeFrozen;

public class Europa extends Planet {

	public Europa(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 3.552f, 0.670891031f, 0.01f, 0.470f, 101.087f, 54.425f, 20.865f);
		this.physical = new PhysicalParameters(0.13418f, 6243.2f, 0, 3.5518f, 0, 0.47f, 101.087f);
		this.type = new PlanetTypeFrozen();
		this.name = "Europa";
	}
}
