package com.team.futurecraft.space;

public class Neptune extends GasGiant {

	public Neptune(CelestialObject parent) {
		super(parent);
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
		return 0.5f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(2500.0f, 1f, 365f);
	}

}
