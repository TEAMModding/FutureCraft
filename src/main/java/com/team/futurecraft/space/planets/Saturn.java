package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;

public class Saturn extends GasGiant {

	public Saturn(CelestialObject parent) {
		super(parent);
		this.add(new Mimas(this));
		this.add(new Enceladus(this));
		this.add(new Tethys(this));
		this.add(new Dione(this));
		this.add(new Rhea(this));
		this.add(new Titan(this));
		this.add(new Iapetus(this));
	}

	@Override
	public String getTexture() {
		return "saturn";
	}

	@Override
	public String getName() {
		return "Saturn";
	}

	@Override
	public float getGravity() {
		return 1;
	}

	@Override
	public float getDiameter() {
		return 120536f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		//TODO: rotation time not accurate
		return new OrbitalParameters(1421.013f, 10752.17f, 0.4166f);
	}

}
