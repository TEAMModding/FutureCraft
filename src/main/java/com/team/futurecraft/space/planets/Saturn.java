package com.team.futurecraft.space.planets;

import com.team.futurecraft.Vec4;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.GasGiant;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;

public class Saturn extends GasGiant {

	public Saturn(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 10752.17f, 1421.013f, 0.054f, 2.484f, 113.715f, -21.283f, -42.488f);
		this.physical = new PhysicalParameters(1.0658f, 120536f, 0f, 0.44401f, 358.922f, 28.049f, 169.53f);
		this.ringSize = 120536f * 1.3f;
		this.atmosphere = new Vec4(0.2, 0.4, 0.7, 0.2);
		this.name = "Saturn";
		
		this.add(new Mimas(this));
		this.add(new Enceladus(this));
		this.add(new Tethys(this));
		this.add(new Dione(this));
		this.add(new Rhea(this));
		this.add(new Titan(this));
		this.add(new Iapetus(this));
	}
}
