package com.team.futurecraft.space.planets;

import com.team.futurecraft.Vec4;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;

public class Jupiter extends GasGiant {

	public Jupiter(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 4329.63f, 775.247f, 0.048f, 1.305f, 100.556f, -85.802f, 19.650f);
		this.physical = new PhysicalParameters(2.5297f, 142984f, 0, 0.41351f, 305.4f, 2.222f, -22.203f);
		this.name = "Jupiter";
		this.atmosphere = new Vec4(0.5, 0.5, 0.5, 0.1);
		
		this.add(new Io(this));
		this.add(new Europa(this));
		this.add(new Ganymede(this));
		this.add(new Callisto(this));
	}
}
