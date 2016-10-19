package com.team.futurecraft.space;

public class OrbitalParameters {
	public long epoch;
	public float period;
	public float semiMajorAxis;
	public float eccentricity;
	public float inclination;
	public float ascendingNode;
	public float argOfPericenter;
	public float meanAnomaly;
	
	/**
	 * A parameter class for storing the orbits of each planet.
	 * 
	 * @param epoch  The date (in seconds) from year 0, that the orbit is at it's mean anomaly.
	 * @param period  The amount of time (in days) for a full year, eg. 365 for earth.
	 * @param semiMajorAxis  The average distance from the orbit to it's parent (in TerraMeters, 1,000,000 kilometers).
	 * @param eccentricity  The round-ness of the orbit, eg. 1 is a perfect circle, and 0.5 is an oval.
	 * @param inclination The tilt (in degrees) of the orbit around the Z axis.
	 * @param ascendingNode The rotation (in degrees) of the whole orbit around the Y axis, unaffected by the inclination.
	 * @param argOfPericenter The rotation (in degrees) of the whole orbit around the Y axis, as rotated by the inclination
	 * @param meanAnomaly The orbital position (in degrees) of the planet at it's epoch (it's starting point).
	 */
	public OrbitalParameters(long epoch, float period, float semiMajorAxis, float eccentricity, float inclination, float ascendingNode, float argOfPericenter, float meanAnomaly) {
		this.epoch = epoch;
		this.period = period;
		this.semiMajorAxis = semiMajorAxis;
		this.eccentricity = eccentricity;
		this.inclination = inclination;
		this.ascendingNode = ascendingNode;
		this.argOfPericenter = argOfPericenter;
		this.meanAnomaly = meanAnomaly;
	}
}
