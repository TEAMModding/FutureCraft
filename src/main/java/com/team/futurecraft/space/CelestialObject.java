package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;

/**
 * Represents any celestial object (stars, planets, moons, asteroids, etc).
 * 
 * @author Joseph
 */
public abstract class CelestialObject {
	private ArrayList<CelestialObject> children = new ArrayList<CelestialObject>();
	private CelestialObject parent;
	
	/**
	 * Adds an object to orbit this object. This should be called inside the constructor.
	 */
	public void add(CelestialObject object) {
		children.add(object);
	}
	
	public CelestialObject(CelestialObject parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the type of this object. Either PLANET, or STAR.
	 */
	public abstract EnumCelestialType getType();
	
	/**
	 * Gets this object's name, used for save folders and gui's.
	 */
	public abstract String getName();
	
	/**
	 * Gets the gravity of this object, anything under 0.2 has bugs, 1.0 is earth gravity.
	 */
	public abstract float getGravity();
	
	/**
	 * Gets the diameter of this planet. no equal scale for this yet.
	 */
	public abstract float getDiameter();
	
	/**
	 * Gets the orbital parameter's of this object, can return null if it's the main object, such as the sun.
	 */
	public abstract OrbitalParameters getOrbit();
	
	/**
	 * Called when this object is rendered in space.
	 */
	public abstract void render(Vec3 rotation, Vec3 pos, Minecraft mc, float time, boolean showOrbit);
	
	/**
	 * Returns if this object has a dimension you can travel to.
	 */
	public abstract boolean isLandable();
	
	public void renderOrbits(float time) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
		
        GlStateManager.disableTexture2D();
    	GlStateManager.disableLighting();
        if (this.getParent() != null) {
        	glPushMatrix();
        
        	Vec3 parentPos = this.getParent().getPosition(time);
        	GL11.glTranslated(parentPos.xCoord, parentPos.yCoord, parentPos.zCoord);
        	GL11.glRotatef(time / this.getOrbit().getYearScale(), 0F, 1F, 0F);
        	GlStateManager.enableAlpha();
        	glLineWidth(1);
        	renderer.startDrawing(3);
        	Vec3 color = new Vec3(0, 150, 0);
        	
        	if (this.parent.getType() == EnumCelestialType.PLANET) {
        		color = new Vec3(150, 75, 0);
        	}
        	
        	for (int k = 0; k < 360; k++) {
        		renderer.setColorRGBA((int)color.xCoord, (int)color.yCoord, (int)color.zCoord, (int)(((330 - k) / 200.0f) * 255));
        		double radians = Math.toRadians(k);
        		renderer.addVertex((Math.cos(radians) * this.getOrbit().getDistance()), 0, Math.sin(radians) * this.getOrbit().getDistance());
        	}
        	tessellator.draw();
        
        	glPopMatrix();
        }
        
        //render landing spot on planet
        /*if (this.isLandable()) {
        	Vec3 PlanetPos = this.getPosition(time);
        	
        	glPushMatrix();
        	GL11.glTranslated(PlanetPos.xCoord, PlanetPos.yCoord, PlanetPos.zCoord);
        	Vec3 direction = this.getDirection(time);
        	glLineWidth(1);
        	renderer.startDrawing(3);
        	renderer.setColorOpaque_I(0x00FF00);
        	renderer.addVertex(0, 0, 0);
        	renderer.addVertex(direction.xCoord * (this.getDiameter() / 1000000) * 1.0001, direction.yCoord * (this.getDiameter() / 1000000) * 1.0001, direction.zCoord * this.getDiameter() * 1.1);
        	tessellator.draw();
        	glPopMatrix();
        }*/
        GlStateManager.enableTexture2D();
    	GlStateManager.enableLighting();
        
        CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].renderOrbits(time);
		}
	}
	
	/**
	 * Calls render for all this object's children.
	 */
	public void renderChildren(Vec3 rotation, Vec3 pos, Minecraft mc, float time, boolean showOrbit) {
		CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].render(rotation, pos, mc, time, showOrbit);
		}
	}
	
	public Vec3 getPosition(float time) {
		Vec3 offsetPos = new Vec3(0, 0, 0);
		if (this.getOrbit() != null && this.parent != null) {
			double radians = Math.toRadians(-time / this.getOrbit().getYearScale());
			Vec3 orbitPos = new Vec3(Math.cos(radians) * this.getOrbit().getDistance(), 0, Math.sin(radians) * this.getOrbit().getDistance());
			offsetPos = this.parent.getPosition(time);
			return orbitPos.add(offsetPos);
		}
		return offsetPos;
	}
	
	public Vec3 getDirection(float time) {
		if (this.getOrbit() != null)
			return new Vec3(-1, 1, 0).rotateYaw((float) Math.toRadians(time * this.getOrbit().getRotation()));
		else 
			return new Vec3(-1, 1, 0);
	}
	
	/**
	 * Gets a list of all children.
	 */
	public CelestialObject[] getChildren() {
		return this.children.toArray(new CelestialObject[] {});
	}
	
	/**
	 * Gets this object's parent.
	 */
	public CelestialObject getParent() {
		return this.parent;
	}
}
