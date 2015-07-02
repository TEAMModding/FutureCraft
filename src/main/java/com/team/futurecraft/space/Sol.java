package com.team.futurecraft.space;

/**
 * It's the sun!
 * 
 * @author Joseph
 */
public class Sol extends Star {
	/**
	 * This is where the magic happens, to add a new planet to the solar system
	 * put a new this.add line with your planet in it.
	 */
	public Sol(CelestialObject parent) {
		super(parent);
		this.add(new Mercury(this));
		this.add(new Venus(this));
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
		return 10.0f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return null;
	}
}
