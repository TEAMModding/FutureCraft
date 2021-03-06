package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3d;

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
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.Vec4f;
import com.team.futurecraft.rendering.Camera;
import com.team.futurecraft.rendering.Shader;
import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.rendering.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Represents any planet (this includes moons too... there was no point making a Moon class... oh wait... I did... but that's THE moon).
 * 
 * @author Joseph
 */
public abstract class Planet extends CelestialObject {
	public int[] turfmap;
	public int[] stonemap;
	
	/** The ring diameter (in kilometers)*/
	public float ringSize = 0;
	public PlanetType type = new PlanetTypeFrozen();
	public Vec4f atmosphere = new Vec4f(1, 1, 1, 0);
	
	private boolean hasSurfacemap = false;
	private boolean hasCloudmap = false;
	private boolean hasLightmap = false;
	private boolean hasOceanmap = false;
	private boolean hasRingmap = false;
	private IntBuffer intBuf;
	
	private String surfacePath;
	private String nightPath;
	private String oceanPath;
	private String cloudPath;
	private String ringPath;
	
	private Shader planetShader;
	private Shader atmosphereShader;
	private Shader cloudShader;
	private Shader ringShader;
	
	public Planet(CelestialObject parent) {
		super(parent);
		
		intBuf = BufferUtils.createIntBuffer(3); intBuf.put(0); intBuf.put(1); intBuf.put(2); intBuf.flip();
	}
	
	public void init() {
		planetShader = Shader.loadShader("planet");
		atmosphereShader = Shader.loadShader("atmosphere");
		cloudShader = Shader.loadShader("clouds");
		ringShader = Shader.loadShader("rings");
		
		this.readColormap();
		
		surfacePath = "textures/planets/" + this.name + "/surface.png";
		nightPath = "textures/planets/" + this.name + "/night.png";
		oceanPath = "textures/planets/" + this.name + "/ocean.png";
		cloudPath = "textures/planets/" + this.name + "/clouds.png";
		ringPath = "textures/planets/" + this.name + "/rings.png";
		
		if (Textures.exists(surfacePath))
			hasSurfacemap = true;
		if (Textures.exists(nightPath))
			hasLightmap = true;
		if (Textures.exists(oceanPath))
			hasOceanmap = true;
		if (Textures.exists(cloudPath))
			hasCloudmap = true;
		if (Textures.exists(ringPath))
			hasRingmap = true;
		
		super.init();
	}
	
	/**
	 * Internal stuff that sets up the terrain colormap
	 */
	private void readColormap() {
		InputStream in;
		BufferedImage image;
		String mapPath = "textures/planets/" + this.name + "/colormap.png";
		if (Textures.exists(mapPath)) {
			try {
				in = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("futurecraft", mapPath)).getInputStream();
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
	
	public float getNightMultiplier() {
		return 1.0f;
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
        if (hasSurfacemap) {
        	GL13.glActiveTexture(GL13.GL_TEXTURE0);
        	Textures.loadTexture(surfacePath);
        }
        else {
        	GL13.glActiveTexture(GL13.GL_TEXTURE1);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
	}
	
	/**
	 * This covers the render function from Celestial object. But if you have a SUPER
	 * special planet, you could override it and have custom rendering.
	 */
	public void render(Camera cam, float time) {
        Vec3f PlanetPos = this.getPosition(time);
        this.renderChildren(cam, time);
        
        double distance = cam.pos.distanceTo(PlanetPos);
        int lod = 100;
        
        if (distance < 5) {
        	//bind all the needed textures
        	bindTextures();
        
        	Mat4f model = new Mat4f();
        	model = model.multiply(Mat4f.translate(PlanetPos.x - cam.pos.x, PlanetPos.y - cam.pos.y, PlanetPos.z - cam.pos.z));
        	model = model.multiply(Mat4f.rotate(this.physical.eqAscNode, 0F, 1F, 0F));
        	model = model.multiply(Mat4f.rotate(this.physical.obliquity, 0F, 0F, 1F));
        	model = model.multiply(Mat4f.rotate(90, 1F, 0F, 0F));
        	model = model.multiply(Mat4f.rotate(-90, 0F, 0F, 1F));
        	model = model.multiply(Mat4f.rotate(-(((time - this.orbit.epoch) / 86400) / this.physical.rotationPeriod * 360) - this.physical.rotationOffset, 0F, 0F, 1F));
        	
        	Mat4f realModel = new Mat4f();
        	realModel = realModel.multiply(Mat4f.translate(PlanetPos.x, PlanetPos.y, PlanetPos.z));
        	realModel = realModel.multiply(Mat4f.rotate(this.physical.eqAscNode, 0F, 1F, 0F));
        	realModel = realModel.multiply(Mat4f.rotate(this.physical.obliquity, 0F, 0F, 1F));
        	realModel = realModel.multiply(Mat4f.rotate(90, 1F, 0F, 0F));
        	realModel = realModel.multiply(Mat4f.rotate(-90, 0F, 0F, 1F));
        	realModel = realModel.multiply(Mat4f.rotate(-(((time - this.orbit.epoch) / 86400) / this.physical.rotationPeriod * 360) - this.physical.rotationOffset, 0F, 0F, 1F));
        
        	
        	planetShader.bind();
        	planetShader.uniformMat4(model, "model");
        	planetShader.uniformMat4(realModel, "realModel");
        	planetShader.uniformMat4(SpaceRenderer.getViewMatrix(new Vec3f(0, 0, 0), cam.rot), "view");
        	planetShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "realView");
        	planetShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        	planetShader.uniformVec3f(cam.pos, "eyePos");
        	planetShader.uniformVec3f(new Vec3f(atmosphere.x, atmosphere.y, atmosphere.z), "atmosphereColor");
        	planetShader.uniformFloat((float)atmosphere.w, "atmosphereDensity");
        	planetShader.uniformFloat(this.getNightMultiplier(), "nightMultiplier");
        	
        	int uniform = GL20.glGetUniformLocation(planetShader.id, "texture");
        	GL20.glUniform1(uniform, intBuf);
        
        	Sphere sphere = new Sphere();
        	sphere.setTextureFlag(true);
        	sphere.draw((this.physical.diameter / 1000000) / 2, lod, lod);
        
        	atmosphereShader.bind();
        	atmosphereShader.uniformMat4(model, "model");
        	atmosphereShader.uniformMat4(realModel, "realModel");
        	atmosphereShader.uniformMat4(SpaceRenderer.getViewMatrix(new Vec3f(0, 0, 0), cam.rot), "view");
        	atmosphereShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "realView");
        	atmosphereShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        	atmosphereShader.uniformVec3f(new Vec3f(atmosphere.x, atmosphere.y, atmosphere.z), "atmosphereColor");
        	atmosphereShader.uniformFloat((float)atmosphere.w, "atmosphereDensity");
        	atmosphereShader.uniformFloat(this.getNightMultiplier(), "nightMultiplier");
        
        	GL11.glBlendFunc(GL_ONE, GL_ONE);
        	Sphere sphere2 = new Sphere();
        	sphere2.setTextureFlag(true);
        	sphere2.setOrientation(GLU.GLU_INSIDE);
        	sphere2.draw(((this.physical.diameter / 1000000) / 2) * 1.02f, lod, lod);
        	GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        	if (hasCloudmap) {
        		cloudShader.bind();
        		cloudShader.uniformMat4(model, "model");
        		cloudShader.uniformMat4(realModel, "realModel");
        		cloudShader.uniformMat4(SpaceRenderer.getViewMatrix(new Vec3f(0, 0, 0), cam.rot), "view");
        		cloudShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "realView");
        		cloudShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        		cloudShader.uniformVec3f(new Vec3f(atmosphere.x, atmosphere.y, atmosphere.z), "atmosphereColor");
        		cloudShader.uniformFloat((float)atmosphere.w, "atmosphereDensity");
        		cloudShader.uniformFloat(this.getNightMultiplier(), "nightMultiplier");
        
        		Textures.loadTexture(cloudPath);
        		
        		Sphere sphere3 = new Sphere();
        		sphere3.setTextureFlag(true);
        		sphere3.draw(((this.physical.diameter / 1000000) / 2) * 1.01f, lod, lod);
        	}
        	
        	if (this.ringSize > 0 && this.hasRingmap) {
        		ringShader.bind();
        		ringShader.uniformMat4(model, "model");
        		ringShader.uniformMat4(realModel, "realModel");
        		ringShader.uniformMat4(SpaceRenderer.getViewMatrix(new Vec3f(0, 0, 0), cam.rot), "view");
        		ringShader.uniformMat4(SpaceRenderer.getViewMatrix(cam.pos, cam.rot), "realView");
        		ringShader.uniformMat4(SpaceRenderer.getProjectionMatrix(), "projection");
        		
        		Textures.loadTexture(this.ringPath);
    		
        		drawRing(0, this.ringSize / 1000000, 1, 300);
        	}
        	GL20.glUseProgram(0);
        }
	}
	
	@SuppressWarnings("unused")
	private void drawPlanet(float scale) {
		glDisable(GL_CULL_FACE);
		for (int i = 0; i < 6; i++) {	
			if ( i == 1 )
				glRotatef(90, 0, 1, 0);
			if ( i == 2 )
				glRotatef(-90, 0, 1, 0);
			if ( i == 3 )
				glRotatef(180, 0, 1, 0);
			if ( i == 4 )
				glRotatef(90, 1, 0, 0);
			if ( i == 5 )
				glRotatef(-90, 1, 0, 0);
			
			glScalef(scale - (1/5), scale - (1/5), scale - (1/5));
			glBegin(GL_TRIANGLES);
			for (int x = -5; x < 5; x++) {
				for (int y = -5; y < 5; y++) {
					Vec3f corner1 = new Vec3f(x, y, 5).normalize();
					Vec3f normal1 = new Vec3f(x, y, 5).normalize();
				
					Vec3f corner2 = new Vec3f(x + 1, y, 5).normalize();
					Vec3f normal2 = new Vec3f(x + 1, y, 5).normalize();
						
					Vec3f corner3 = new Vec3f(x + 1, y + 1, 5).normalize();
					Vec3f normal3 = new Vec3f(x + 1, y + 1, 5).normalize();
				
				
					Vec3f corner4 = new Vec3f(x, y + 1, 5).normalize();
					Vec3f normal4 = new Vec3f(x, y + 1, 5).normalize();
				
					glVertex3d(corner1.x, corner1.y, corner1.z);
					glTexCoord2d(x + 5, y + 5);
					glNormal3d(-normal1.x, -normal1.y, -normal1.z);
					glVertex3d(corner2.x, corner2.y, corner2.z);
					glTexCoord2d(x + 5, y + 5);
					glNormal3d(-normal2.x, -normal2.y, -normal2.z);
					glVertex3d(corner3.x, corner3.y, corner3.z);
					glTexCoord2d(x + 5, y + 5);
					glNormal3d(-normal3.x, -normal3.y, -normal3.z);
					
					glVertex3d(corner1.x, corner1.y, corner1.z);
					glTexCoord2d(x + 5, y + 5);
					glNormal3d(-normal1.x, -normal1.y, -normal1.z);
					glVertex3d(corner3.x, corner3.y, corner3.z);
					glTexCoord2d(x + 5, y + 5);
					glNormal3d(-normal3.x, -normal3.y, -normal3.z);
					glVertex3d(corner4.x, corner4.y, corner4.z);
					glTexCoord2d(x + 5, y + 5);
					glNormal3d(-normal4.x, -normal4.y, -normal4.z);
				}
 			}
			glEnd();
		}
	}
        
	private void drawRing(float innerRadius, float outerRadius, int loops, int slices) {
		float da, dr;
		da = (float) (2.0f * Math.PI / slices);
		dr = (outerRadius - innerRadius) / loops;

		/*
		 * texture of a gluDisk is a cut out of the texture unit square x, y in
		 * [-outerRadius, +outerRadius]; s, t in [0, 1] (linear mapping)
		 */
		float sa, ca;
		float r1 = innerRadius;
		int l;
		for (l = 0; l < loops; l++) {
			float r2 = r1 + dr;
			if (true) {
				int s;
				glBegin(GL_QUAD_STRIP);
				for (s = 0; s <= slices; s++) {
					float a;
					if (s == slices)
						a = 0.0f;
					else
						a = s * da;
					sa = (float) Math.sin(a);
					ca = (float) Math.cos(a);
					glTexCoord2f(1, 0);
					glVertex2f(r2 * sa, r2 * ca);
					glTexCoord2f(0, 0);
					glVertex2f(r1 * sa, r1 * ca);
				}
				glEnd();
			}
			if (true) {
				int s;
				glBegin(GL_QUAD_STRIP);
				for (s = slices; s >= 0; s--) {
					float a;
					if (s == slices)
						a = 0.0f;
					else
						a = s * da;
					sa = (float) Math.sin(a);
					ca = (float) Math.cos(a);
					glTexCoord2f(1, 0);
					glVertex2f(r2 * sa, r2 * ca);
					glTexCoord2f(0, 0);
					glVertex2f(r1 * sa, r1 * ca);
				}
				glEnd();
			}
			r1 = r2;
		}
	}
}