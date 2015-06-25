package com.team.futurecraft.space;

public class Earth extends Planet {

	@Override
	public WorldType getWorldType() {
		return null;
	}

	@Override
	public int getAtmosphericColor() {
		return 0;
	}

	@Override
	public int getAtmosphericDensity() {
		return 0;
	}

	@Override
	public String getName() {
		return "Earth";
	}

	@Override
	public String getTexture() {
		return "earth";
	}

	@Override
	public float getGravity() {
		return 1.0f;
	}

	@Override
	public float getDiameter() {
		return 0;
	}

	@Override
	public void addChildren() {
		
	}
}
