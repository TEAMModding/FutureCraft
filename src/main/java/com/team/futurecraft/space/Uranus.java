package com.team.futurecraft.space;

public class Uranus extends GasGiant {

	public Uranus(CelestialObject parent) {
		super(parent);
	}

	@Override
	public String getTexture() {
		return "uranus";
	}

	@Override
	public String getName() {
		return "Uranus";
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
		return new OrbitalParameters(2000.0f, 1f, 365f);
	}

}
