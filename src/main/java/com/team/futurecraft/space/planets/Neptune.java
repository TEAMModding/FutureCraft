package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;

public class Neptune extends GasGiant {

	public Neptune(CelestialObject parent) {
		super(parent);
		this.add(new Triton(this));
	}

	@Override
	public String getTexture() {
		return "neptune";
	}

	@Override
	public String getName() {
		return "Neptune";
	}

	@Override
	public float getGravity() {
		return 1;
	}

	@Override
	public float getDiameter() {
		return 99063.998f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		//TODO: rotation time not accurate
		return new OrbitalParameters(4480.281f, 60149.445f, 0.6666f);
	}

}
