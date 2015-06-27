package com.team.futurecraft.gui;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.team.futurecraft.SpaceRegistry;
import com.team.futurecraft.StartupCommon;
import com.team.futurecraft.network.TeleportMessage;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.Sol;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * This is the gui screen class of the navigation gui.
 * 
 * @author Joseph
 */
public class GuiNavigation extends GuiScreen {
	private float time = 0;
	private float zPos = -400;
	private float xPos = -10;
	private float yPos = -200;
	private float xRot = 0;
	private float yRot = 30;
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	private float speed = 0.01f;
	
	/**
	 * No idea why this is even here, i guess we might need it someday.
	 * 
	 * @param player
	 */
	public GuiNavigation(EntityPlayer player) {
		
	}
	
	/**
	 * Adds the buttons.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		CelestialObject[] objects = StartupCommon.SOL.getChildren();
		int buttonCount = 0;
		for (int i = 0; i < objects.length; i++) {
			this.buttonList.add(new GuiSpaceButton(buttonCount, 10, 10 + buttonCount * 30, 100, 20, objects[i].getName()));
			planets.add((Planet)objects[i]);
			buttonCount++;
			
			CelestialObject[] moons = objects[i].getChildren();
			for (int j = 0; j < moons.length; j++) {
				this.buttonList.add(new GuiSpaceButton(buttonCount, 50, 10 + buttonCount * 30, 100, 20, moons[i].getName()));
				planets.add((Planet)moons[i]);
				buttonCount++;
			}
		}
	}
	
	/**
	 * Handles the mouse input to rotate the camera.
	 */
	public void handleMouseInput() throws IOException
    {
		if (Mouse.isGrabbed()) {
			float mouseSpeed = 0.1f;
			double mouseX = Mouse.getEventX();
			double mouseY = Mouse.getEventY();
			xRot -= (float) (mouseSpeed * (this.mc.displayWidth /2 - mouseX));
			yRot += (float) (mouseSpeed * (this.mc.displayHeight /2 - mouseY));
			Mouse.setCursorPosition(this.mc.displayWidth / 2, this.mc.displayHeight / 2);
		}
		super.handleMouseInput();
    }
	
	/**
	 * Handles keyboard input to move the player.
	 */
	public void handleKeyboardInput() throws IOException
    {
		Keyboard.enableRepeatEvents(true);
		double radians = Math.toRadians(xRot);
		if (Keyboard.getEventKey() == Keyboard.KEY_S) {
			xPos += Math.sin(radians) * speed;
			zPos -= Math.cos(radians) * speed;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_W) {
			xPos -= Math.sin(radians) * speed;
			zPos += Math.cos(radians) * speed;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_A) {
			zPos += Math.sin(radians) * speed;
			xPos += Math.cos(radians) * speed;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_D) {
			zPos -= Math.sin(radians) * speed;
			xPos -= Math.cos(radians) * speed;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
			yPos -= speed;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT) {
			yPos += speed;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
			speed *= 2;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
			speed /= 2;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
			Mouse.setGrabbed(false);
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_E) {
			Mouse.setGrabbed(true);
		}
		super.handleKeyboardInput();
    }
	
	/**
	 * draws the buttons and planet. Called every tick.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		setupRendering();
		new Sol(null).render(this.mc, this.time);
		revertRendering();
        
        time += 0.001f;
        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	/**
	 * Sets up 3d projection, shadowing, skybox, all that good stuff.
	 */
	private void setupRendering() {
		//sets up the camera projection and position
		GlStateManager.matrixMode(GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GLU.gluPerspective(45.0F, 1, 0.001F, 10000000.0F);
        GL11.glRotatef(yRot, 1F, 0F, 0F);
        GL11.glRotatef(xRot, 0F, 1F, 0F);
        GL11.glTranslatef(xPos, yPos, zPos);
        //sets up the model space
        GlStateManager.matrixMode(GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        glScalef(0.6f, 1f, 0.6f);
        
        //enables depth
        glEnable(GL_DEPTH_TEST);
        
        GlStateManager.pushMatrix();
        
        renderSkybox();
        
        //sets up shadowing
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(-1f, 0f, 0f, 0.0f));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(0.6f, 0.6f, 0.6f, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(0f, 0f, 0f, 1.0F));
        
        GlStateManager.shadeModel(GL_SMOOTH);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(0f, 0f, 0f, 1.0F));
	}
	
	/**
	 * Gets rid of all our good stuff so minecraft can go happilly along with bad graphics.
	 */
	private void revertRendering() {
		GlStateManager.disableLighting();
		glPopMatrix();
        GlStateManager.matrixMode(GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL_MODELVIEW);
        GlStateManager.popMatrix();
	}
	
	/**
	 * Renders the skybox.....
	 */
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
	
	/**
	 * Turns an RGBA color into a buffer for use in shadowing.
	 */
	private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
    {
        colorBuffer.clear();
        colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        colorBuffer.flip();
        /** Float buffer used to set OpenGL material colors */
        return colorBuffer;
    }

	/**
	 * Sends a teleport message to the server when a button is pressed.
	 */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		TeleportMessage airstrikeMessageToServer = new TeleportMessage(SpaceRegistry.getDimensionForPlanet(planets.get(button.id)));
	    StartupCommon.simpleNetworkWrapper.sendToServer(airstrikeMessageToServer);
	}
}
