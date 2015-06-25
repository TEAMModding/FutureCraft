package com.team.futurecraft.gui;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.PlanetRegistry;
import com.team.futurecraft.world.WorldProviderPlanet;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

/**
 * This is the gui screen class of the navigation gui.
 * 
 * @author Joseph
 *
 */
public class GuiNavigation extends GuiScreen {
	private ArrayList<WorldProviderPlanet> planets;
	private WorldProviderPlanet currentPlanet;
	private int position = 0;
	private float planetRotation = 0;
	private float zPos = -3;
	private float xPos = 0;
	private float yPos = 0;
	private float xRot = 0;
	private float yRot = 0;
	private float zRot = 0;
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	
	public GuiNavigation(EntityPlayer player) {
		this.planets = PlanetRegistry.getRegisteredPlanets();
	}
	
	/**
	 * Adds the buttons and sets the planet.
	 */
	@Override
	public void initGui() {
		this.currentPlanet = planets.get(0);
	}
	
	public void handleMouseInput() throws IOException
    {
		if (Mouse.isButtonDown(0)) {
			float mouseSpeed = 0.1f;
			double mouseX = Mouse.getEventX();
			double mouseY = Mouse.getEventY();
			xRot -= (float) (mouseSpeed * (this.mc.displayWidth /2 - mouseX));
			yRot += (float) (mouseSpeed * (this.mc.displayHeight /2 - mouseY));
			Mouse.setGrabbed(true);
			//System.out.println(xRot + ", " + yRot);
			Mouse.setCursorPosition(this.mc.displayWidth / 2, this.mc.displayHeight / 2);
		}
		if (!Mouse.isButtonDown(0)) {
			//System.out.println("test");
			Mouse.setGrabbed(false);
		}
    }
	
	public void handleKeyboardInput() throws IOException
    {
		Keyboard.enableRepeatEvents(true);
		double radians = Math.toRadians(xRot);
		if (Keyboard.getEventKey() == Keyboard.KEY_S) {
			xPos += Math.sin(radians) * 0.01;
			zPos -= Math.cos(radians) * 0.01;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_W) {
			xPos -= Math.sin(radians) * 0.01;
			zPos += Math.cos(radians) * 0.01;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_A) {
			zPos += Math.sin(radians) * 0.01;
			xPos += Math.cos(radians) * 0.01;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_D) {
			zPos -= Math.sin(radians) * 0.01;
			xPos -= Math.cos(radians) * 0.01;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
			yPos -= 0.01;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT) {
			yPos += 0.01;
		}
		super.handleKeyboardInput();
    }
	
	/**
	 * draws the buttons and planet. Called every tick.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GLU.gluPerspective(45.0F, this.mc.displayWidth / this.mc.displayHeight, 0.001F, 10000000.0F);
        GL11.glRotatef(yRot, 1F, 0F, 0F);
        GL11.glRotatef(xRot, 0F, 1F, 0F);
        GL11.glTranslatef(xPos, yPos, zPos);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        glScalef(0.6f, 1f, 0.6f);
        
        glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);

        GlStateManager.pushMatrix();
        
        renderSkybox();
        
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        float f = 0.0F;
        float f1 = 0.6F;
        float f2 = 0.0F;
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(1f, 0f, 0f, 0.0f));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
        
        GlStateManager.shadeModel(GL_SMOOTH);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(f, f, f, 1.0F));
        
        glTranslatef(0, 0, -3);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        renderPlanet(new ResourceLocation("futurecraft", "textures/environment/earth.png"), 0, 0, 0.2f);
        renderPlanet(new ResourceLocation("futurecraft", "textures/blocks/selena_dirt.png"), 1, 0, 0.1f);
        renderStar(100, 0, 2);
        GlStateManager.popMatrix();
        
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	/**
	 * Renders the planet with the specified image.
	 */
	private void renderPlanet(ResourceLocation img, float x, float y, float size) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
		
		glPushMatrix();
        this.mc.getTextureManager().bindTexture(img);
		glColor3f(1, 1, 1);
        
		GL11.glRotatef(10, 0F, 0F, 1F);
        GL11.glRotatef(planetRotation, 0F, 1F, 0F);
        GL11.glTranslatef(x, y, 0);
        
        this.mc.getTextureManager().bindTexture(img);
        glColor3f(1, 1, 1);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw(size, 100, 100);
        glPopMatrix();
        
        glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glRotatef(10, 0F, 0F, 1F);
        
        glColor3f(1, 0, 0);
        glLineWidth(3);
        renderer.startDrawing(3);
        for (int i = 0; i < 370; i++) {
        	double radians = Math.toRadians(i);
        	renderer.addVertex(Math.cos(radians) * 1, 0, Math.sin(radians) * 1);
        }
        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        
        
        planetRotation += 0.01F;
        glPopMatrix();
	}
	
	private void renderStar(float x, float y, float size) {
		GlStateManager.disableLighting();
		glPushMatrix();
		glTranslatef(x, y, 0);
		glColor3f(2.0f, 2.0f, 2.0f);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(false);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw(size, 100, 100);
        
        glPopMatrix();
        GlStateManager.enableLighting();
	}
	
	private void renderSkybox() {
		ResourceLocation SKY_POS_X = new ResourceLocation("futurecraft","textures/environment/sky_pos_x.png");
		ResourceLocation SKY_NEG_X = new ResourceLocation("futurecraft","textures/environment/sky_neg_x.png");
		ResourceLocation SKY_POS_Y = new ResourceLocation("futurecraft","textures/environment/sky_pos_y.png");
		ResourceLocation SKY_NEG_Y = new ResourceLocation("futurecraft","textures/environment/sky_neg_y.png");
		ResourceLocation SKY_POS_Z = new ResourceLocation("futurecraft","textures/environment/sky_pos_z.png");
		ResourceLocation SKY_NEG_Z = new ResourceLocation("futurecraft","textures/environment/sky_neg_z.png");
		
		this.mc.getTextureManager().bindTexture(SKY_NEG_Y);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();

        for (int i = 0; i < 6; ++i) {
            GL11.glPushMatrix();
            GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);

            if (i == 1) {
            	this.mc.getTextureManager().bindTexture(SKY_POS_Z);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
            	this.mc.getTextureManager().bindTexture(SKY_NEG_Z);
            	GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
            	this.mc.getTextureManager().bindTexture(SKY_POS_Y);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
            	this.mc.getTextureManager().bindTexture(SKY_POS_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
            	this.mc.getTextureManager().bindTexture(SKY_NEG_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            renderer.startDrawingQuads();
            renderer.setColorOpaque_I(0xFFFFFF);
            renderer.addVertexWithUV(-10000.0D, -10000.0D, -10000.0D, 0.0D, 0.0D);
            renderer.addVertexWithUV(-10000.0D, -10000.0D, 10000.0D, 0.0D, 1.0D);
            renderer.addVertexWithUV(10000.0D, -10000.0D, 10000.0D, 1.0D, 1.0D);
            renderer.addVertexWithUV(10000.0D, -10000.0D, -10000.0D, 1.0D, 0.0D);
            tessellator.draw();
            GL11.glPopMatrix();
        }
	}
	
	/**
	 * This gui pauses the game.
	 */
	public boolean doesGuiPauseGame() {
		return true;
    }
	
	private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
    {
        colorBuffer.clear();
        colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        colorBuffer.flip();
        /** Float buffer used to set OpenGL material colors */
        return colorBuffer;
    }
}
