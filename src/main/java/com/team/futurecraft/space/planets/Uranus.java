package com.team.futurecraft.space.planets;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;

public class Uranus extends GasGiant {

	public Uranus(CelestialObject parent) {
		super(parent);
		this.add(new Miranda(this));
		this.add(new Ariel(this));
		this.add(new Umbriel(this));
		this.add(new Titania(this));
		this.add(new Oberon(this));
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
		return 51118f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		//TODO: rotation time not accurate
		return new OrbitalParameters(2859.459f, 30665.11f, 0.7083f);
	}

}
