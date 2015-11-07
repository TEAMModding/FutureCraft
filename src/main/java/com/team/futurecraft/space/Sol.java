package com.team.futurecraft.space;

import com.team.futurecraft.space.planets.EarthMoon;
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
		
		this.orbit = null;
		this.physical = new PhysicalParameters(500, 152550f, 0, 0, 0, 0, 0);
		
		this.add(new Mercury(this));
		this.add(new Venus(this));
		this.add(new EarthMoon(this));
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
}
