package com.team.futurecraft.space;

/**
 * All gas giants subclass this.
 * 
 * @author Joseph
 */
public abstract class GasGiant extends Planet {

	public GasGiant(CelestialObject parent) {
		super(parent);
	}
	
	public boolean isLandable() {
		return false;
	}
}
