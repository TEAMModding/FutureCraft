package com.team.futurecraft.space.planets;

import net.minecraft.util.Vec3;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;

public class Uranus extends GasGiant {

	public Uranus(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 30665.11f, 2859.459f, 0.047f, 0.77f, 74.23f, 96.734f, 142.268f);
		this.physical = new PhysicalParameters(0.9052f, 51118f, 0, 0.71833f, 331.18f, 97.810f, 167.760f);
		
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
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.2, 0.4, 0.7);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.1f;
	}
}
