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
		this.add(new Earth(this));
		this.add(new Mars(this));
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
