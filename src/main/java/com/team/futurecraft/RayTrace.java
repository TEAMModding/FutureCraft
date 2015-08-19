package com.team.futurecraft;

public class RayTrace {
	public EnumAxis axis;
	public double distance;
	
	public RayTrace(EnumAxis axis, double distance) {
		this.axis = axis;
		this.distance = distance;
	}
	
	public enum EnumAxis {
		X(),
		Y(),
		Z();
	}
}
