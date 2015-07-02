package com.team.futurecraft.space;

import net.minecraft.util.Vec3;

public abstract class GasGiant extends Planet {

	public GasGiant(CelestialObject parent) {
		super(parent);
	}
	
	public boolean isLandable() {
		return false;
	}
	
	public WorldType getWorldType() {
		return null;
	}
	
	@Override
	public Vec3 getAtmosphericColor() {
		return null;
	}

	@Override
	public float getAtmosphericDensity() {
		return 0;
	}
}
