package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.WorldType;
import com.team.futurecraft.space.WorldTypeFrozen;

import net.minecraft.util.Vec3;

public class Europa extends Planet {

	public Europa(CelestialObject parent) {
		super(parent);
	}

	@Override
	public WorldType getWorldType() {
		return new WorldTypeFrozen();
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
		return "Europa";
	}

	@Override
	public String getTexture() {
		return "europa";
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
