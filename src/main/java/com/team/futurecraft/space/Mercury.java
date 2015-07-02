package com.team.futurecraft.space;

import net.minecraft.util.Vec3;

public class Mercury extends Planet {

	public Mercury(CelestialObject parent) {
		super(parent);
	}

	@Override
	public WorldType getWorldType() {
		return new WorldTypeSelena();
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
		return "Mercury";
	}

	@Override
	public String getTexture() {
		return "mercury";
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
		return new OrbitalParameters(200.0f, 1f, 365f);
	}
}
