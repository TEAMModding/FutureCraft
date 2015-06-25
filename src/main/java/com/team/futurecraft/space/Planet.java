package com.team.futurecraft.space;

public abstract class Planet extends CelestialObject {
	public EnumCelestialType getType() {
		return EnumCelestialType.PLANET;
	}
	
	public abstract WorldType getWorldType();
	
	public abstract int getAtmosphericColor();
	
	public abstract int getAtmosphericDensity();
}