package com.team.futurecraft.space.planets;

import net.minecraft.util.Vec3;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;

public class Jupiter extends GasGiant {

	public Jupiter(CelestialObject parent) {
		super(parent);
		this.add(new Io(this));
		this.add(new Europa(this));
		this.add(new Ganymede(this));
		this.add(new Callisto(this));
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
		return 142984f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		//TODO: rotation time not accurate
		return new OrbitalParameters(4329.63f, 775.247f, 0.048f, 1.305f, 100.556f, -85.802f, 19.650f, 0.4166f);
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.5, 0.5, 0.5);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.1f;
	}
}
