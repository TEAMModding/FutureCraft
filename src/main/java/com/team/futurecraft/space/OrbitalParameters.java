package com.team.futurecraft.space;

public class OrbitalParameters {
	private float distance;
	private float yearScale;
	private float rotation;
	
	public OrbitalParameters(float distance, float orbital, float rotation) {
		this.distance = distance;
		this.yearScale = orbital;
		this.rotation = 1 / rotation;
	}
	
	public float getDistance() {
		return this.distance;
	}
	
	public float getYearScale() {
		return this.yearScale;
	}
	
	public float getRotation() {
		return this.rotation;
	}
}
