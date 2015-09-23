package com.team.futurecraft.space.planets;

import net.minecraft.util.Vec3;

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
		return new OrbitalParameters(60149.445f, 4480.281f, 0.009f, 1.769f, 131.722f, -89.751f, 259.909f, 0.6666f);
	}
	
	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0, 0, 0.5);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.1f;
	}
}
