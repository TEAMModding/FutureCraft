package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

/**
 * Represents any planet (this includes moons too... there was no point making a Moon class... oh wait... I did... but that's THE moon).
 * 
 * @author Joseph
 */
public abstract class Planet extends CelestialObject {
	
	public Planet(CelestialObject parent) {
		super(parent);
	}
	
	/**
	 * Tells everything that this is indeed a planet.
	 */
	public EnumCelestialType getType() {
		return EnumCelestialType.PLANET;
	}
	
	/**
	 * Yes you can land on it...
	 */
	public boolean isLandable() {
		return true;
	}
	
	/**
	 * Planets should return a WorldType object for this.
	 * There are currently Selena, and Desert world types. Much much more to come.
	 */
	public abstract WorldType getWorldType();
	
	/**
	 * Return's a Vec3 for the atmospheric color, colors range from 0 to 1, not 0 to 255.
	 */
	public abstract Vec3 getAtmosphericColor();
	
	/**
	 * Returns the density of the atmosphere (basically the opacity). ranges from 0 to 1.
	 */
	public abstract float getAtmosphericDensity();
	
	/**
	 * Gets the texture of this planet in space, located in textures/environment.
	 */
	public abstract String getTexture();
	
	/**
	 * This covers the render function from Celestial object. But if you have a SUPER
	 * special planet, you could override it and have custom rendering.
	 * 
	 * Also renders the orbit path, probably need to put that somewhere else.
	 */
	public void render(Minecraft mc, float time, boolean showOrbit) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        ResourceLocation img = new ResourceLocation("futurecraft", "textures/planets/" + this.getTexture() + ".jpg");
		
		glPushMatrix();
        mc.getTextureManager().bindTexture(img);
		glColor3f(1, 1, 1);
		
        this.renderChildren(mc, time, showOrbit);
        Vec3 pos = this.getPosition(time);
        GL11.glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);
        GL11.glRotatef(time * this.getOrbit().getRotation(), 0F, 1F, 0F);
        GL11.glRotatef(90, 1F, 0F, 0F);
        
        mc.getTextureManager().bindTexture(img);
        glColor3f(1, 1, 1);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw(this.getDiameter(), 100, 100);
        glPopMatrix();
        
        if (showOrbit) {
        	glPushMatrix();
        	GL11.glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);
        	GlStateManager.disableTexture2D();
        	GlStateManager.disableLighting();
        	Vec3 direction = this.getDirection(time);
        	glLineWidth(1);
        	renderer.startDrawing(3);
        	renderer.setColorOpaque_I(0x00FF00);
        	renderer.addVertex(0, 0, 0);
        	renderer.addVertex(direction.xCoord * this.getDiameter() * 1.1, direction.yCoord * this.getDiameter() * 1.1, direction.zCoord * this.getDiameter() * 1.1);
        	tessellator.draw();
        	glPopMatrix();
        	GlStateManager.enableTexture2D();
        	GlStateManager.enableLighting();
        }
        
        if (showOrbit) {
        	glPushMatrix();
        	GlStateManager.disableTexture2D();
        	GlStateManager.disableLighting();
        
        	Vec3 parentPos = this.getParent().getPosition(time);
        	GL11.glTranslated(parentPos.xCoord, parentPos.yCoord, parentPos.zCoord);
        	GL11.glRotatef(time * this.getOrbit().getYearScale(), 0F, 1F, 0F);
        	GlStateManager.enableAlpha();
        	glLineWidth(1);
        	renderer.startDrawing(3);
        	for (int i = 0; i < 360; i++) {
        		renderer.setColorRGBA(255, 0, 0, (int)(((360 - i) / 200.0f) * 255));
        		double radians = Math.toRadians(i);
        		renderer.addVertex(Math.cos(radians) * this.getOrbit().getDistance(), 0, Math.sin(radians) * this.getOrbit().getDistance());
        	}
        	tessellator.draw();
        	GlStateManager.enableLighting();
        	GlStateManager.enableTexture2D();
        
        	glPopMatrix();
        }
	}
	
	public Vec3 getDirection(float time) {
		return new Vec3(-1, 1, 0).rotateYaw((float) Math.toRadians(time * this.getOrbit().getRotation()));
	}
}