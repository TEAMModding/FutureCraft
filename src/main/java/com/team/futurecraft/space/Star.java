package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

/**
 * The class that all stars should extend.
 * 
 * @author Joseph
 */
public abstract class Star extends CelestialObject {
	public Star(CelestialObject parent) {
		super(parent);
	}

	/**
	 * This is definitely a star.
	 */
	public EnumCelestialType getType() {
		return EnumCelestialType.STAR;
	}
	
	/**
	 * No way we're landing on it.
	 */
	public boolean isLandable() {
		return true;
	}
	
	/**
	 * This will determine the star's color in the future,
	 * for now stars are just white, and this is useless.
	 */
	public abstract int getTemperature();
	
	/**
	 * Render's a boring white ball.
	 */
	public void render(Minecraft mc, float time) {
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		glPushMatrix();
		glTranslatef(0, 0, 0);
		glColor3f(2.0f, 2.0f, 2.0f);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(false);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw(this.getDiameter(), 100, 100);
        
        glPopMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        this.renderChildren(mc, time);
	}
}
