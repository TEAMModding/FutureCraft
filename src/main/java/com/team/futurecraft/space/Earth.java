package com.team.futurecraft.space;

import net.minecraft.util.Vec3;

public class Earth extends Planet {

	public Earth(CelestialObject parent) {
		super(parent);
		this.add(new Moon(this));
	}

	@Override
	public WorldType getWorldType() {
		return null;
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0, 0, 0);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0f;
	}

	@Override
	public String getName() {
		return "Earth";
	}

	@Override
	public String getTexture() {
		return "earth.png";
	}

	@Override
	public float getGravity() {
		return 1.0f;
	}

	@Override
	public float getDiameter() {
		return 0.1f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(500.0f, 1f, 365f);
	}

	public int getDimension() {
		return 0;
	}
}
