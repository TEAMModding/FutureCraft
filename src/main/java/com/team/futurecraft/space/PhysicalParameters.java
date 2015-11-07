package com.team.futurecraft.space;

public class PhysicalParameters {
	public float gravity;
	public float diameter;
	public float oblateness;
	public float rotationPeriod;
	public float rotationOffset;
	public float obliquity;
	public float eqAscNode;
	
	/**
	 * A parameter class used for physical properties of each planet.
	 * 
	 * @param gravity  The gravity of the planet i(n G's, 1 = earth).
	 * @param diameter  The diameter (in kilometers) of the planet.
	 * @param oblateness The oblong-ness of the planet (currently not implemented).
	 * @param rotationPeriod The time (in days) that this planet makes a full rotation.
	 * @param rotationOffset The offset (in degrees) that this planet was facing when it started.
	 * @param obliquity The tilt of the planet (in degrees) rotated around the Z axis.
	 * @param eqAscNode The tilt of the planet (in degrees) rotated around the Y axis.
	 */
	public PhysicalParameters(float gravity, float diameter, float oblateness, float rotationPeriod, float rotationOffset, float obliquity, float eqAscNode) {
		this.gravity = gravity;
		this.diameter = diameter;
		this.oblateness = oblateness;
		this.rotationPeriod = rotationPeriod;
		this.rotationOffset = rotationOffset;
		this.obliquity = obliquity;
		this.eqAscNode = eqAscNode;
	}
}
