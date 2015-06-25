package com.team.futurecraft.space;

public abstract class Star extends CelestialObject {
	public EnumCelestialType getType() {
		return EnumCelestialType.STAR;
	}
	
	public abstract int getTemperature();
}
