package com.team.futurecraft.space.planets;

import com.team.futurecraft.Vec4f;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;

public class Neptune extends GasGiant {

	public Neptune(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 60149.445f, 4480.281f, 0.009f, 1.769f, 131.722f, -89.751f, 259.909f);
		this.physical = new PhysicalParameters(1.1373f, 99063.998f, 0, 0.67125f, 228.65f, 28.03f, 49.235f);
		this.name = "Neptune";
		this.atmosphere = new Vec4f(0.0f, 0.0f, 0.5f, 0.1f);
		
		this.add(new Triton(this));
	}
}
