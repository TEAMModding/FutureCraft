package com.team.futurecraft.space;

import net.minecraft.util.Vec3;

public class Moon extends Planet {

	public Moon(CelestialObject parent) {
		super(parent);
	}

	@Override
	public WorldType getWorldType() {
		return new WorldTypeSelena();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(1, 1, 1);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.0f;
	}

	@Override
	public String getName() {
		return "Moon";
	}

	@Override
	public String getTexture() {
		return "moon";
	}

	@Override
	public float getGravity() {
		return 0.3f;
	}

	@Override
	public float getDiameter() {
		return 0.04f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(3.0f, 27f, 27f);
	}
}
