package com.team.futurecraft.space.planets;

import net.minecraft.util.Vec3;

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
		return new OrbitalParameters(10752.17f, 1421.013f, 0.054f, 2.484f, 113.715f, -21.283f, -42.488f, 0.4166f);
	}
	
	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.2, 0.4, 0.7);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.2f;
	}
}
