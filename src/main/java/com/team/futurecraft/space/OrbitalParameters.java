package com.team.futurecraft.space;

public class OrbitalParameters {
	private float distance;
	private float yearScale;
	
	public OrbitalParameters(float distance, float yearScale) {
		this.distance = distance;
		this.yearScale = yearScale;
	}
	
	public float getDistance() {
		return this.distance;
	}
	
	public float getYearScale() {
		return this.yearScale;
	}
}
