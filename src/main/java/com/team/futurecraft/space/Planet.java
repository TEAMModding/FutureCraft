package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import scala.actors.threadpool.Arrays;

import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.rendering.ModelLoader;
import com.team.futurecraft.rendering.SpaceRenderer;

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
	public int[] turfmap;
	public int[] stonemap;
	
	public Planet(CelestialObject parent) {
		super(parent);
		this.readColormap();
	}
	
	/**
	 * Internal stuff that sets up the terrain colormap
	 */
	private void readColormap() {
		InputStream in;
		BufferedImage image;
		String mapPath = this.getColormap();
		if (mapPath != null) {
			try {
				in = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("futurecraft", "textures/planets/" + mapPath +".png")).getInputStream();
				image = ImageIO.read(in);
			
				AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
				transform.translate(0, -image.getHeight());
				AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = operation.filter(image, null);
		
				int height = image.getHeight();
				int width = image.getWidth();

				int[] pixels = new int[height];
				image.getRGB(0, 0, 1, height, pixels, 0, 1);
				turfmap = pixels;
				
				int[] pixels2 = new int[height];
				image.getRGB(1, 0, 1, height, pixels2, 0, 1);
				stonemap = pixels2;
			} catch (IOException e) {
				turfmap = new int[] {0xFFFFFF};
			}
		}
		else {
			turfmap = new int[] {0xFFFFFF};
		}
	}
	
	public abstract BiomePlanet getBiome();
	
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
	
	public boolean hasRings() {
		return false;
	}
	
	/**
	 * Returns the name of the colormap image located in textures/planets
	 */
	public abstract String getColormap();
	
	/**
	 * Planets should return a WorldType object for this.
	 * There are currently Selena, and Desert world types. Much much more to come.
	 */
	public abstract PlanetType getWorldType();
	
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
	public void render(Vec3 rotation, Vec3 pos, Minecraft mc, float time, boolean showOrbit) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        ResourceLocation img = new ResourceLocation("futurecraft", "textures/planets/" + this.getTexture() + ".jpg");
        ResourceLocation ringImg = new ResourceLocation("futurecraft", "textures/planets/saturn_rings.png");
        Vec3 PlanetPos = this.getPosition(time);
        GlStateManager.enableLighting();
		
		glPushMatrix();
        mc.getTextureManager().bindTexture(img);
		glColor3f(1, 1, 1);
		
        this.renderChildren(rotation, pos, mc, time, showOrbit);
        
        GL11.glTranslated(PlanetPos.xCoord, PlanetPos.yCoord, PlanetPos.zCoord);
        GL11.glRotatef(time * this.getOrbit().getRotation(), 0F, 1F, 0F);
        GL11.glRotatef(90, 1F, 0F, 0F);
        
        mc.getTextureManager().bindTexture(img);
        glColor3f(1, 1, 1);
        
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.draw(this.getDiameter(), 50, 50);
        
        mc.getTextureManager().bindTexture(ringImg);
        GlStateManager.disableLighting();
        Disk disc = new Disk();
        disc.setTextureFlag(true);
        
        disc.setOrientation(GLU.GLU_INSIDE);
        disc.draw(this.getDiameter() * 2, this.getDiameter() * 2 + 0.3f, 50, 50);
        
        disc.setOrientation(GLU.GLU_OUTSIDE);
        disc.draw(this.getDiameter() * 2, this.getDiameter() * 2 + 0.3f, 100, 100);
        
        glPopMatrix();
	}
}