package com.team.futurecraft.space;

import com.team.futurecraft.space.planets.Earth;
import com.team.futurecraft.space.planets.Jupiter;
import com.team.futurecraft.space.planets.Mars;
import com.team.futurecraft.space.planets.Mercury;
import com.team.futurecraft.space.planets.Neptune;
import com.team.futurecraft.space.planets.Saturn;
import com.team.futurecraft.space.planets.Uranus;
import com.team.futurecraft.space.planets.Venus;

/**
 * It's the sun!
 * 
 * @author Joseph
 */
public class Sol extends Star {
	public Sol(CelestialObject parent) {
		super(parent);
		this.add(new Mercury(this));
		this.add(new Venus(this));
		//this.add(new Terra(this));
		this.add(new Earth(this));
		this.add(new Mars(this));
		this.add(new Jupiter(this));
		this.add(new Saturn(this));
		this.add(new Uranus(this));
		this.add(new Neptune(this));
	}
	
	@Override
	public int getTemperature() {
		return 10088;
	}
	
	@Override
	public String getName() {
		return "sol";
	}

	@Override
	public float getGravity() {
		return 500;
	}

	@Override
	public float getDiameter() {
		return 1525580f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return null;
	}
}
