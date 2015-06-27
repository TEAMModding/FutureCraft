package com.team.futurecraft.space;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

/**
 * Represents any celestial object (stars, planets, moons, asteroids, etc).
 * 
 * @author Joseph
 */
public abstract class CelestialObject {
	private ArrayList<CelestialObject> children = new ArrayList<CelestialObject>();
	private CelestialObject parent;
	
	/**
	 * Adds an object to orbit this object. This should be called inside the constructor.
	 */
	public void add(CelestialObject object) {
		children.add(object);
	}
	
	public CelestialObject(CelestialObject parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the type of this object. Either PLANET, or STAR.
	 */
	public abstract EnumCelestialType getType();
	
	/**
	 * Gets this object's name, used for save folders and gui's.
	 */
	public abstract String getName();
	
	/**
	 * Gets the gravity of this object, anything under 0.2 has bugs, 1.0 is earth gravity.
	 */
	public abstract float getGravity();
	
	/**
	 * Gets the diameter of this planet. no equal scale for this yet.
	 */
	public abstract float getDiameter();
	
	/**
	 * Gets the orbital parameter's of this object, can return null if it's the main object, such as the sun.
	 */
	public abstract OrbitalParameters getOrbit();
	
	/**
	 * Called when this object is rendered in space.
	 */
	public abstract void render(Minecraft mc, float time);
	
	/**
	 * Returns if this object has a dimension you can travel to.
	 */
	public abstract boolean isLandable();
	
	/**
	 * Calls render for all this object's children.
	 */
	public void renderChildren(Minecraft mc, float time) {
		CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].render(mc, time);
		}
	}
	
	/**
	 * Gets a list of all children.
	 */
	public CelestialObject[] getChildren() {
		return this.children.toArray(new CelestialObject[] {});
	}
	
	/**
	 * Gets this object's parent.
	 */
	public CelestialObject getParent() {
		return this.parent;
	}
}
