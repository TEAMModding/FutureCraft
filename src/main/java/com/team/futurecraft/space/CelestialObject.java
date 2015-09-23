package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.Mat4;
import com.team.futurecraft.Vec4;

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
        	GL11.glRotatef(this.getOrbit().ascendingNode, 0, 1, 0);
        	GL11.glRotatef(this.getOrbit().inclination, 0, 0, 1);
        	GL11.glRotatef(this.getOrbit().argOfPericenter, 0, 1, 0);
        	GL11.glTranslated(0, 0,  - (this.getOrbit().semiMajorAxis * this.getOrbit().eccentricity));
        	GL11.glScalef((1 - this.getOrbit().eccentricity), 1, 1);
        	GL11.glRotatef(time / this.getOrbit().period - 90 + this.getOrbit().meanAnomaly, 0F, 1F, 0F);
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
        		renderer.addVertex((Math.cos(radians) * this.getOrbit().semiMajorAxis), 0, Math.sin(radians) * this.getOrbit().semiMajorAxis);
        	}
        	tessellator.draw();
        
        	glPopMatrix();
        }
        
        Vec3 pos = this.getPosition(time);
        glPushMatrix();
        GL11.glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);
        renderer.startDrawing(3);
    	renderer.setColorRGBA(150, 0, 0, 255);
    	renderer.addVertex(0, 0, 0);
    	renderer.addVertex(0, 0, this.getDiameter() / 1000000);
    	tessellator.draw();
    	glPopMatrix();
        
        GlStateManager.enableTexture2D();
    	GlStateManager.enableLighting();
        
        CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].renderOrbits(time);
		}
	}
	
	public Vec3 getPosition(float time) {
		Vec3 offsetPos = new Vec3(0, 0, 0);
		if (this.getOrbit() != null && this.parent != null) {
			Vec3 parentPos = this.getParent().getPosition(time);
			
			Mat4 mat = new Mat4();
			mat = mat.multiply(Mat4.translate(parentPos.xCoord, parentPos.yCoord, parentPos.zCoord));
			mat = mat.multiply(Mat4.rotate(this.getOrbit().ascendingNode, 0, 1, 0));
			mat = mat.multiply(Mat4.rotate(this.getOrbit().inclination, 0, 0, 1));
			mat = mat.multiply(Mat4.rotate(this.getOrbit().argOfPericenter, 0, 1, 0));
			mat = mat.multiply(Mat4.translate(0, 0,  -(this.getOrbit().semiMajorAxis * this.getOrbit().eccentricity)));
			mat = mat.multiply(Mat4.scale((1 - this.getOrbit().eccentricity), 1, 1));
			
			
			double radians = Math.toRadians(-time / this.getOrbit().period) + (Math.PI / 2) - (Math.toRadians(this.getOrbit().meanAnomaly));
			Vec3 orbitPos = new Vec3(Math.cos(radians) * this.getOrbit().semiMajorAxis, 0, Math.sin(radians) * this.getOrbit().semiMajorAxis);
			
			Vec4 finalPos = mat.multiply(new Vec4(orbitPos, 1));
			
			return new Vec3(finalPos.xCoord, finalPos.yCoord, finalPos.zCoord);
		}
		return offsetPos;
	}
	
	public Vec3 getDirection(float time) {
		if (this.getOrbit() != null)
			return new Vec3(-1, 1, 0).rotateYaw((float) Math.toRadians(time * this.getOrbit().rotationPeriod));
		else 
			return new Vec3(-1, 1, 0);
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
