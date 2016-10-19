package com.team.futurecraft.space.planets;

import com.team.futurecraft.Vec4f;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;

public class Uranus extends GasGiant {

	public Uranus(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 30665.11f, 2859.459f, 0.047f, 0.77f, 74.23f, 96.734f, 142.268f);
		this.physical = new PhysicalParameters(0.9052f, 51118f, 0, 0.71833f, 331.18f, 97.810f, 167.760f);
		this.atmosphere = new Vec4f(0.2f, 0.4f, 0.7f, 0.1f);
		this.name = "Uranus";
		
		this.add(new Miranda(this));
		this.add(new Ariel(this));
		this.add(new Umbriel(this));
		this.add(new Titania(this));
		this.add(new Oberon(this));
	}
}
