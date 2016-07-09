package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.Vec4f;
import com.team.futurecraft.rendering.Assets;
import com.team.futurecraft.rendering.Camera;
import com.team.futurecraft.rendering.Shader;

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
	
	public void renderOrbits(Camera cam, float time) {
        
        //glDepthMask(false);
        if (this.getParent() != null) {
        	//System.out.println("rendering orbit");
        	Assets.orbitShader.bind();
        	Mat4f model = new Mat4f();
        	
        	float scaling = 1000000000f;
        	float scaledAxis = this.orbit.semiMajorAxis / 1000000000f;
        	
        	//System.out.println("radius is: " + scaledAxis);
        	
        	float parentObliquity = this.getParent().physical.obliquity;
        	float parentEqAscNode = this.getParent().physical.eqAscNode;
        
        	Vec3f parentPos = this.getParent().getPosition(time);
        	model = model.translate(parentPos.x / scaling, parentPos.y / scaling, parentPos.z / scaling);
        	model = model.rotate(parentEqAscNode, 0F, 1F, 0F);
        	model = model.rotate(parentObliquity, 0F, 0F, 1F);
        	
        	model = model.rotate(this.orbit.ascendingNode, 0, 1, 0);
        	model = model.rotate(this.orbit.inclination, 0, 0, 1);
        	model = model.rotate(this.orbit.argOfPericenter, 0, 1, 0);
        	model = model.translate(0, 0, - (scaledAxis * this.orbit.eccentricity));
        	model = model.scale((1 - this.orbit.eccentricity), 1, 1);
        	float rotation = (((time - this.orbit.epoch) / this.orbit.period) * 360) + (this.orbit.meanAnomaly - 90);
        	//System.out.println(this.orbit.period);
        	//System.out.println(rotation);
        	model = model.rotate(rotation, 0F, 1F, 0F);
        	
        	Assets.orbitShader.uniformMat4("model", model);
        	Assets.orbitShader.uniformMat4("view", cam.getViewSkybox().translate(-cam.position.x / scaling, -cam.position.y / scaling, -cam.position.z / scaling));
        	Assets.orbitShader.uniformMat4("projection", cam.getProjection(0.1f, 1000f));
        	
        	glLineWidth(1);
        	
        	glBegin(GL_LINES);
        	Vec3f color = new Vec3f(0, 150, 0);
        	
        	if (this.parent.getType() == EnumCelestialType.PLANET || this.parent.getType() == EnumCelestialType.BARYCENTER) {
        		color = new Vec3f(150, 75, 0);
        	}
        	
        	for (int k = 0; k < 360; k++) {
        		//glColor4f((int)color.x, (int)color.y, (int)color.z, (int)(((330 - k) / 200.0f) * 255));
        		float opacity = ((330 - k) / 200.0f);
        		glColor4f(1.0f, 0.0f, 0.0f, opacity);
        		double radians = Math.toRadians(k);
        		glVertex3f((float)(Math.cos(radians) * scaledAxis), 0, (float)Math.sin(radians) * scaledAxis);
        		
        		float opacity2 = ((330 - (k + 1)) / 200.0f);
        		glColor4f(1.0f, 0.0f, 0.0f, opacity2);
        		double radians2 = Math.toRadians(k + 1);
        		glVertex3f((float)(Math.cos(radians2) * scaledAxis), 0, (float)Math.sin(radians2) * scaledAxis);
        	}
        	glEnd();
        	
        	Shader.unbind();
        }
        
        /*Vec3f pos = this.getPosition(time);
        glPushMatrix();
        glTranslated(pos.x, pos.y, pos.z);
        renderer.startDrawing(3);
    	renderer.setColorRGBA(150, 0, 0, 255);
    	renderer.addVertex(0, 0, 0);
    	renderer.addVertex(0, 0, this.physical.diameter / 1000000);
    	tessellator.draw();
    	glPopMatrix();*/
        
        CelestialObject[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].renderOrbits(cam, time);
		}
		//glDepthMask(true);
	}
	
	public Vec3f getPosition(float time) {
		Vec3f offsetPos = new Vec3f(0, 0, 0);
		if (this.orbit != null && this.parent != null) {
			Vec3f parentPos = this.getParent().getPosition(time);
			
			float parentObliquity = this.getParent().physical.obliquity;
        	float parentEqAscNode = this.getParent().physical.eqAscNode;
        	
			Mat4f mat = new Mat4f();
			mat = mat.translate(parentPos.x, parentPos.y, parentPos.z);
			
			mat = mat.rotate(parentEqAscNode, 0F, 1F, 0F);
			mat = mat.rotate(parentObliquity, 0F, 0F, 1F);
			
			mat = mat.rotate(this.orbit.ascendingNode, 0, 1, 0);
			mat = mat.rotate(this.orbit.inclination, 0, 0, 1);
			mat = mat.rotate(this.orbit.argOfPericenter, 0, 1, 0);
			mat = mat.translate(0, 0, -(this.orbit.semiMajorAxis * this.orbit.eccentricity));
			mat = mat.scale((1 - this.orbit.eccentricity), 1, 1);
			
			float rotation = (((time - this.orbit.epoch) / this.orbit.period) * 360) + (this.orbit.meanAnomaly - 90);
			double radians = Math.toRadians(-rotation);
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