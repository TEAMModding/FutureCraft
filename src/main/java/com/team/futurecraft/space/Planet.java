package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.biome.BiomePlanet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
	 * Renders the planet by itself without calculating orbits or anything.
	 * Used for rendering planets on gui buttons.
	 */
	public void renderStatic(Minecraft mc) {
		ResourceLocation img = new ResourceLocation("futurecraft", "textures/planets/" + this.getTexture() + ".jpg");
		mc.getTextureManager().bindTexture(img);
		
		glColor3f(1, 1, 1);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.draw(10, 25, 25);
	}
	
	/**
	 * This covers the render function from Celestial object. But if you have a SUPER
	 * special planet, you could override it and have custom rendering.
	 */
	public void render(Vec3 rotation, Vec3 pos, Minecraft mc, float time, boolean showOrbit) {
        Vec3 PlanetPos = this.getPosition(time);
        GlStateManager.enableLighting();
		
		glPushMatrix();
		
        this.renderChildren(rotation, pos, mc, time, showOrbit);
        
        GL11.glTranslated(PlanetPos.xCoord, PlanetPos.yCoord, PlanetPos.zCoord);
        GL11.glRotatef(time * this.getOrbit().getRotation(), 0F, 1F, 0F);
        GL11.glRotatef(90, 1F, 0F, 0F);
        
        int lod = 8;
        if (pos.distanceTo(PlanetPos) < 10) {
			ResourceLocation img = new ResourceLocation("futurecraft", "textures/planets/" + this.getTexture() + ".jpg");
			mc.getTextureManager().bindTexture(img);
			lod = 50;
		}
        else {
        	GlStateManager.disableTexture2D();
        }
		glColor3f(1, 1, 1);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.draw((this.getDiameter() / 1000000) / 2, lod, lod);
        
        glPopMatrix();
        GlStateManager.enableTexture2D();
	}
}