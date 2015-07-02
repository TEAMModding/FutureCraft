package com.team.futurecraft.space;

public class Jupiter extends GasGiant {

	public Jupiter(CelestialObject parent) {
		super(parent);
	}

	@Override
	public String getTexture() {
		return "jupiter";
	}

	@Override
	public String getName() {
		return "Jupiter";
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
		return new OrbitalParameters(1000.0f, 1f, 365f);
	}

}
