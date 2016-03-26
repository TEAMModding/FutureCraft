package com.team.futurecraft.space.planets;

import com.team.futurecraft.Vec4f;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetTypeDesert;

public class Mars extends Planet {

	public Mars(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 686.565f, 227.076f, 0.093f, 1.851f, 49.479f, 286.562f, 19.412f);
		this.physical = new PhysicalParameters(0.37902f, 6792f, 0, 1.026f, 136.005f, 26.72f, 82.91f);
		this.name = "Mars";
		this.type = new PlanetTypeDesert();
		this.atmosphere = new Vec4f(0.703f, 0.467f, 0.271f, 0.3f);
	}
}
