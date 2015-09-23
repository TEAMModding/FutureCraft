package com.team.futurecraft.space;

public class OrbitalParameters {
	public float period;
	public float semiMajorAxis;
	public float eccentricity;
	public float inclination;
	public float ascendingNode;
	public float argOfPericenter;
	public float meanAnomaly;
	public float rotationPeriod;
	
	public OrbitalParameters(float period, float semiMajorAxis, float eccentricity, float inclination, float ascendingNode, float argOfPericenter, float meanAnomaly, float rotationPeriod) {
		this.period = period;
		this.semiMajorAxis = semiMajorAxis;
		this.eccentricity = eccentricity;
		this.inclination = inclination;
		this.ascendingNode = ascendingNode;
		this.argOfPericenter = argOfPericenter;
		this.meanAnomaly = meanAnomaly;
		this.rotationPeriod = 1 / rotationPeriod;
	}
	
	/**
	 * extra constructor for legacy orbits.
	 */
	public OrbitalParameters(float semiMajorAxis, float period, float rotationPeriod) {
		this(period, semiMajorAxis, 0, 0, 0, 0, 0, rotationPeriod);
	}
}
