package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

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
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.Mat4;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.rendering.Camera;
import com.team.futurecraft.rendering.Shader;
import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.rendering.Textures;

import net.minecraft.client.Minecraft;
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
	
	private boolean hasCloudmap = false;
	private boolean hasLightmap = false;
	private boolean hasOceanmap = false;
	private IntBuffer intBuf;
	
	private String surfacePath;
	private String nightPath;
	private String oceanPath;
	private String cloudPath;
	
	private Shader planetShader;
	private Shader atmosphereShader;
	private Shader cloudShader;
	private Shader ringShader;
	
	public Planet(CelestialObject parent) {
		super(parent);
		this.readColormap();
		
		intBuf = BufferUtils.createIntBuffer(3); intBuf.put(0); intBuf.put(1); intBuf.put(2); intBuf.flip();
		
		surfacePath = "textures/planets/" + this.getName() + "/surface.png";
		nightPath = "textures/planets/" + this.getName() + "/night.png";
		oceanPath = "textures/planets/" + this.getName() + "/ocean.png";
		cloudPath = "textures/planets/" + this.getName() + "/clouds.png";
		
		if (Textures.exists(nightPath))
			hasLightmap = true;
		if (Textures.exists(oceanPath))
			hasOceanmap = true;
		if (Textures.exists(cloudPath))
			hasCloudmap = true;
		
		planetShader = Shader.loadShader("planet");
		atmosphereShader = Shader.loadShader("atmosphere");
		cloudShader = Shader.loadShader("clouds");
		ringShader = Shader.loadShader("rings");
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
				in = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("futurecraft", "textures/planets/" + this.getName() + "/colormap.png")).getInputStream();
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
	
	public String getCloudTexture() {
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
		ResourceLocation img = new ResourceLocation("futurecraft", surfacePath);
		mc.getTextureManager().bindTexture(img);
		
		glColor3f(1, 1, 1);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.draw(10, 25, 25);
	}
	
	private void bindTextures() {
		
		
		if (hasLightmap) {
        	GL13.glActiveTexture(GL13.GL_TEXTURE2);
        	Textures.loadTexture(nightPath);
        }
        else {
        	GL13.glActiveTexture(GL13.GL_TEXTURE2);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
        if (hasOceanmap) {
        	GL13.glActiveTexture(GL13.GL_TEXTURE1);
        	Textures.loadTexture(oceanPath);
        }
        else {
        	GL13.glActiveTexture(GL13.GL_TEXTURE1);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        Textures.loadTexture(surfacePath);
	}
	
	/**
	 * This covers the render function from Celestial object. But if you have a SUPER
	 * special planet, you could override it and have custom rendering.
	 */
	public void render(Camera cam, float time) {
        Vec3 PlanetPos = this.getPosition(time);
        this.renderChildren(cam, time);
        
        double distance = cam.pos.distanceTo(PlanetPos);
        
        if (distance < 5) {
        	//bind all the needed textures
        	bindTextures();
        
        	Mat4 model = new Mat4();
        	model = model.multiply(Mat4.translate(PlanetPos.xCoord, PlanetPos.yCoord, PlanetPos.zCoord));
        	model = model.multiply(Mat4.rotate(this.physical.eqAscNode, 0F, 1F, 0F));
        	model = model.multiply(Mat4.rotate(this.physical.obliquity, 0F, 0F, 1F));
        	model = model.multiply(Mat4.rotate(90, 1F, 0F, 0F));
        	model = model.multiply(Mat4.rotate(-90, 0F, 0F, 1F));
        	model = model.multiply(Mat4.rotate(-(((time - this.orbit.epoch) / 86400) / this.physical.rotationPeriod * 360) - this.physical.rotationOffset, 0F, 0F, 1F));
        
        	
        	planetShader.bind();
        	planetShader.uniformMat4(model, "model");
        	planetShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "view");
        	planetShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        	planetShader.uniformVec3(this.getAtmosphericColor(), "atmosphereColor");
        	planetShader.uniformFloat(this.getAtmosphericDensity(), "atmosphereDensity");
        	planetShader.uniformFloat(this.getNightMultiplier(), "nightMultiplier");
        	
        	int uniform = GL20.glGetUniformLocation(planetShader.id, "texture");
        	GL20.glUniform1(uniform, intBuf);
        
        	Sphere sphere = new Sphere();
        	sphere.setTextureFlag(true);
        	sphere.draw((this.physical.diameter / 1000000) / 2, 50, 50);
        
        	atmosphereShader.bind();
        	atmosphereShader.uniformMat4(model, "model");
        	atmosphereShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "view");
        	atmosphereShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        	atmosphereShader.uniformVec3(this.getAtmosphericColor(), "atmosphereColor");
        	atmosphereShader.uniformFloat(this.getAtmosphericDensity(), "atmosphereDensity");
        	atmosphereShader.uniformFloat(this.getNightMultiplier(), "nightMultiplier");
        
        	GL11.glBlendFunc(GL_ONE, GL_ONE);
        	Sphere sphere2 = new Sphere();
        	sphere2.setTextureFlag(true);
        	sphere2.setOrientation(GLU.GLU_INSIDE);
        	sphere2.draw(((this.physical.diameter / 1000000) / 2) * 1.02f, 50, 50);
        	GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        	if (hasCloudmap) {
        		cloudShader.bind();
        		cloudShader.uniformMat4(model, "model");
        		cloudShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "view");
        		cloudShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        		cloudShader.uniformVec3(this.getAtmosphericColor(), "atmosphereColor");
        		cloudShader.uniformFloat(this.getAtmosphericDensity(), "atmosphereDensity");
        		cloudShader.uniformFloat(this.getNightMultiplier(), "nightMultiplier");
        
        		Textures.loadTexture(cloudPath);
        		
        		Sphere sphere3 = new Sphere();
        		sphere3.setTextureFlag(true);
        		sphere3.draw(((this.physical.diameter / 1000000) / 2) * 1.01f, 50, 50);
        	}
        	
        	/*ringShader.bind();
        	ringShader.uniformMat4(model, "model");
    		ringShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "view");
    		ringShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
    		
    		Textures.loadTexture("textures/planets/saturn/rings.png");
    		
    		Disk disk = new Disk();
    		disk.setTextureFlag(true);
    		disk.setOrientation(GLU.GLU_INSIDE);
    		disk.draw((this.physical.diameter / 1000000), (this.physical.diameter / 1000000) * 2, 50, 50);*/
        
        	GL20.glUseProgram(0);
        }
	}
}