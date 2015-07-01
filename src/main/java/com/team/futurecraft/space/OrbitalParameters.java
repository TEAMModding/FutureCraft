package com.team.futurecraft.space;

public class OrbitalParameters {
	private float distance;
	private float yearScale;
	private float rotation;
	
	public OrbitalParameters(float distance, float yearScale, float rotation) {
		this.distance = distance;
		this.yearScale = yearScale;
		this.rotation = rotation;
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
