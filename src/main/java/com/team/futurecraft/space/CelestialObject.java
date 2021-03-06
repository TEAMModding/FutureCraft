package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.Vec4f;
import com.team.futurecraft.rendering.Camera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

/**
 * Represents any celestial object (stars, planets, moons, asteroids, etc).
 * 
 * @author Joseph
 */
public abstract class CelestialObject {
	private ArrayList<CelestialObject> children = new ArrayList<CelestialObject>();
	private CelestialObject parent;
	
	public OrbitalParameters orbit;
	public PhysicalParameters physical;
	public String name = "Planet";
	
	public CelestialObject(CelestialObject parent) {
		this.parent = parent;
		this.orbit = new OrbitalParameters(63082454400L, 0, 149f, 0, 0, 0, 0, 0);
		this.physical = new PhysicalParameters(1, 0.00470989f, 0, 1, 0, 0, 0);
	}
	
	/**
	 * Adds an object to orbit this object. This should be called inside the constructor.
	 */
	public void add(CelestialObject object) {
		children.add(object);
	}
	
	/**
	 * Gets the type of this object. Either PLANET, or STAR.
	 */
	public abstract EnumCelestialType getType();
	
	/**
	 * Renders the planet by itself without calculating orbits or anything.
	 * Used for rendering planets on gui buttons.
	 */
	public abstract void renderStatic(Minecraft mc);
	
	/**
	 * Called when this object is rendered in space.
	 */
	public abstract void render(Camera cam, float time);
	
	
	
	/**
	 * Returns if this object has a dimension you can travel to.
	 */
	public abstract boolean isLandable();
	
	public void renderOrbits(float time) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        
        GL11.glDepthMask(false);
        GlStateManager.disableTexture2D();
    	GlStateManager.disableLighting();
        if (this.getParent() != null) {
        	glPushMatrix();
        	
        	float parentObliquity = this.getParent().physical.obliquity;
        	float parentEqAscNode = this.getParent().physical.eqAscNode;
        
        	Vec3f parentPos = this.getParent().getPosition(time);
        	GL11.glTranslated(parentPos.x, parentPos.y, parentPos.z);
        	GL11.glRotatef(parentEqAscNode, 0F, 1F, 0F);
        	GL11.glRotatef(parentObliquity, 0F, 0F, 1F);
        	
        	GL11.glRotatef(this.orbit.ascendingNode, 0, 1, 0);
        	GL11.glRotatef(this.orbit.inclination, 0, 0, 1);
        	GL11.glRotatef(this.orbit.argOfPericenter, 0, 1, 0);
        	GL11.glTranslated(0, 0, - (this.orbit.semiMajorAxis * this.orbit.eccentricity));
        	GL11.glScalef((1 - this.orbit.eccentricity), 1, 1);
        	GL11.glRotatef((((time - this.orbit.epoch) / 86400) / this.orbit.period * 360) + this.orbit.meanAnomaly - 90, 0F, 1F, 0F);
        	GlStateManager.enableAlpha();
        	glLineWidth(1);
        	renderer.startDrawing(3);
        	Vec3f color = new Vec3f(0, 150, 0);
        	
        	if (this.parent.getType() == EnumCelestialType.PLANET || this.parent.getType() == EnumCelestialType.BARYCENTER) {
        		color = new Vec3f(150, 75, 0);
        	}
        	
        	for (int k = 0; k < 360; k++) {
        		renderer.setColorRGBA((int)color.x, (int)color.y, (int)color.z, (int)(((330 - k) / 200.0f) * 255));
        		double radians = Math.toRadians(k);
        		renderer.addVertex((Math.cos(radians) * this.orbit.semiMajorAxis), 0, Math.sin(radians) * this.orbit.semiMajorAxis);
        	}
        	tessellator.draw();
        
        	glPopMatrix();
        }
        
        Vec3f pos = this.getPosition(time);
        glPushMatrix();
        GL11.glTranslated(pos.x, pos.y, pos.z);
        renderer.startDrawing(3);
    	renderer.setColorRGBA(150, 0, 0, 255);
    	renderer.addVertex(0, 0, 0);
    	renderer.addVertex(0, 0, this.physical.diameter / 1000000);
    	tessellator.draw();
    	glPopMatrix();
        
        GlStateManager.enableTexture2D();
    	GlStateManager.enableLighting();
        
        CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].renderOrbits(time);
		}
		GL11.glDepthMask(true);
	}
	
	public Vec3f getPosition(float time) {
		Vec3f offsetPos = new Vec3f(0, 0, 0);
		if (this.orbit != null && this.parent != null) {
			Vec3f parentPos = this.getParent().getPosition(time);
			
			float parentObliquity = this.getParent().physical.obliquity;
        	float parentEqAscNode = this.getParent().physical.eqAscNode;
			
			Mat4f mat = new Mat4f();
			mat = mat.multiply(Mat4f.translate(parentPos.x, parentPos.y, parentPos.z));
			
			mat = mat.multiply(Mat4f.rotate(parentEqAscNode, 0F, 1F, 0F));
	        mat = mat.multiply(Mat4f.rotate(parentObliquity, 0F, 0F, 1F));
			
			mat = mat.multiply(Mat4f.rotate(this.orbit.ascendingNode, 0, 1, 0));
			mat = mat.multiply(Mat4f.rotate(this.orbit.inclination, 0, 0, 1));
			mat = mat.multiply(Mat4f.rotate(this.orbit.argOfPericenter, 0, 1, 0));
			mat = mat.multiply(Mat4f.translate(0, 0, -(this.orbit.semiMajorAxis * this.orbit.eccentricity)));
			mat = mat.multiply(Mat4f.scale((1 - this.orbit.eccentricity), 1, 1));
			
			
			double radians = Math.toRadians(-(((time - this.orbit.epoch) / 86400) / this.orbit.period * 360) - this.orbit.meanAnomaly + 90);
			Vec3f orbitPos = new Vec3f(Math.cos(radians) * this.orbit.semiMajorAxis, 0, Math.sin(radians) * this.orbit.semiMajorAxis);
			
			Vec4f finalPos = mat.multiply(new Vec4f(orbitPos, 1));
			
			return new Vec3f(finalPos.x, finalPos.y, finalPos.z);
		}
		return offsetPos;
	}
	
	//TODO: this is soooo outdated now
	public Vec3f getDirection(float time) {
		if (this.orbit != null)
			return new Vec3f(-1, 1, 0).rotateYaw((float) Math.toRadians(time * this.physical.rotationPeriod));
		else 
			return new Vec3f(-1, 1, 0);
	}
	
	public void init() {
		this.initChildren();
	}
	
	public void initChildren() {
		CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].init();
		}
	}
	
	/**
	 * Calls render for all this object's children.
	 */
	public void renderChildren(Camera cam, float time) {
		CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].render(cam, time);
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
