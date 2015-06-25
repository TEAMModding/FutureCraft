package com.team.futurecraft.space;

public class Sol extends Star {
	@Override
	public int getTemperature() {
		return 10088;
	}

	@Override
	public String getName() {
		return "sol";
	}

	@Override
	public String getTexture() {
		return null;
	}

	@Override
	public float getGravity() {
		return 500;
	}

	@Override
	public float getDiameter() {
		return 762801.541f;
	}

	@Override
	public void addChildren() {
		this.add(new Earth(), new OrbitalParameters());
	}
}
