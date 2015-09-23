package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.Mat4;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.rendering.ShaderList;
import com.team.futurecraft.rendering.ShaderUtil;
import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.space.planets.Earth;

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
	
	public float getNightMultiplier() {
		return 1.0f;
	}
	
	/**
	 * Gets the texture of this planet in space, located in textures/environment.
	 */
	public abstract String getTexture();
	
	public String getNightTexture() {
		return null;
	}
	
	public String getOceanTexture() {
		return null;
	}
	
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
        this.renderChildren(rotation, pos, mc, time, showOrbit);
		glPushMatrix();
        
        //bind all the needed textures
        if (this.getNightTexture() != null) {
        	GL13.glActiveTexture(GL13.GL_TEXTURE2);
        	ResourceLocation img2 = new ResourceLocation("futurecraft", "textures/planets/" + this.getNightTexture() + ".jpg");
        	mc.getTextureManager().bindTexture(img2);
        }
        else {
        	GL13.glActiveTexture(GL13.GL_TEXTURE2);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
        if (this.getOceanTexture() != null) {
        	GL13.glActiveTexture(GL13.GL_TEXTURE1);
        	ResourceLocation img1 = new ResourceLocation("futurecraft", "textures/planets/" + this.getOceanTexture() + ".jpg");
        	mc.getTextureManager().bindTexture(img1);
        }
        else {
        	GL13.glActiveTexture(GL13.GL_TEXTURE1);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        ResourceLocation img0 = new ResourceLocation("futurecraft", "textures/planets/" + this.getTexture() + ".jpg");
        mc.getTextureManager().bindTexture(img0);
        
        //use the planet shader
        GL20.glUseProgram(ShaderList.planetShader);
        
        Mat4 model = Mat4.translate(PlanetPos.xCoord, PlanetPos.yCoord, PlanetPos.zCoord).multiply(Mat4.rotate(time * this.getOrbit().rotationPeriod, 0F, 1F, 0F)).multiply(Mat4.rotate(90, 1F, 0F, 0F));
        
        //specify uniforms
        ShaderUtil.uniformMat4(model, "model", ShaderList.planetShader);
        ShaderUtil.uniformMat4(SpaceRenderer.getViewMatrix(pos, rotation), "view", ShaderList.planetShader);
        ShaderUtil.uniformMat4(SpaceRenderer.getProjectionMatrix(mc), "projection", ShaderList.planetShader);
        ShaderUtil.uniformVec3(this.getAtmosphericColor(), "atmosphereColor", ShaderList.planetShader);
        ShaderUtil.uniformFloat(this.getAtmosphericDensity(), "atmosphereDensity", ShaderList.planetShader);
        ShaderUtil.uniformFloat(this.getNightMultiplier(), "nightMultiplier", ShaderList.planetShader);
        
        IntBuffer intBuf = BufferUtils.createIntBuffer(3); intBuf.put(0); intBuf.put(1); intBuf.put(2); intBuf.flip();
        int uniform = GL20.glGetUniformLocation(ShaderList.planetShader, "texture");
		GL20.glUniform1(uniform, intBuf);
        
        int lod = 8;
        
        double distance = pos.distanceTo(new Vec3(-PlanetPos.xCoord, -PlanetPos.yCoord, -PlanetPos.zCoord));
        
        if (distance < 2)
        	lod = 50;
        
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.draw((this.getDiameter() / 1000000) / 2, lod, lod);
        GL20.glUseProgram(0);
        
        glPopMatrix();
	}
}