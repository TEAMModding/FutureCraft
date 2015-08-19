package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;

public class Saturn extends GasGiant {

	public Saturn(CelestialObject parent) {
		super(parent);
	}

	@Override
	public String getTexture() {
		return "saturn";
	}

	@Override
	public String getName() {
		return "Saturn";
	}

	@Override
	public float getGravity() {
		return 1;
	}

	@Override
	public float getDiameter() {
		return 0.5f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(1500.0f, 1f, 365f);
	}

}