package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.Barycenter;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;

public class EarthMoon extends Barycenter {

	public EarthMoon(CelestialObject parent) {
		super(parent);
		this.add(new Earth(this));
		this.add(new Moon(this));
		
		this.orbit = new OrbitalParameters(63082497600L, 365f, 149f, 0.017f, 0f, 348.739f, -245.802f, -2.471f);
	}

	@Override
	public String getName() {
		return "Earth-Moon";
	}
}
