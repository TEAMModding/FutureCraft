package com.team.futurecraft.space;

import com.team.futurecraft.rendering.Camera;

import net.minecraft.client.Minecraft;

/**
 * This class represents a barycenter, a representation of the center of gravity between two celestial objects that orbit
 * each other and not just one orbiting the other, eg. earth-moon.
 * @author Joseph
 *
 */
public abstract class Barycenter extends CelestialObject {
	public Barycenter(CelestialObject parent) {
		super(parent);
		
		this.physical = new PhysicalParameters(0, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public EnumCelestialType getType() {
		return EnumCelestialType.BARYCENTER;
	}
	
	public PhysicalParameters getPhysical() {
		return new PhysicalParameters(0, 0, 0, 0, 0, 0, 0);
	}
	
	public void renderStatic(Minecraft mc) {
		
	}

	@Override
	public void render(Camera cam, float time) {
		this.renderChildren(cam, time);
	}

	@Override
	public boolean isLandable() {
		return false;
	}
}
