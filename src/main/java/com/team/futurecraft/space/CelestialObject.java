package com.team.futurecraft.space;

import java.util.HashMap;

/**
 * Represents any celestial object (stars, planets, moons, asteroids, etc).
 * 
 * @author Joseph
 */
public abstract class CelestialObject {
	private HashMap<CelestialObject, OrbitalParameters> children = new HashMap<CelestialObject, OrbitalParameters>();
	
	public void add(CelestialObject object, OrbitalParameters orbit) {
		children.put(object, orbit);
	}
	
	public abstract EnumCelestialType getType();
	
	public abstract String getName();
	
	public abstract String getTexture();
	
	public abstract float getGravity();
	
	public abstract float getDiameter();
	
	public abstract void addChildren();
}
