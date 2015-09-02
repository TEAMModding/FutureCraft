package com.team.futurecraft.space.planets;

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
		return new OrbitalParameters(775.247f, 4329.63f, 0.4166f);
	}

}
