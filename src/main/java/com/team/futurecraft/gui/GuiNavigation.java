package com.team.futurecraft.gui;


import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.PlanetRegistry;
import com.team.futurecraft.world.WorldProviderPlanet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * This is the gui screen class of the navigation gui.
 * 
 * @author Joseph
 *
 */
public class GuiNavigation extends GuiScreen {
	private static final ResourceLocation background = new ResourceLocation("futurecraft", "textures/gui/star_background.png");
	private ArrayList<WorldProviderPlanet> planets;
	private WorldProviderPlanet currentPlanet;
	private int position = 0;
	private float planetRotation = 0;
	private float zPos = 0;
	
	public GuiNavigation(EntityPlayer player) {
		this.planets = PlanetRegistry.getRegisteredPlanets();
	}
	
	/**
	 * Adds the buttons and sets the planet.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiSpaceButton(0, 15, this.height - 25, 100, 15, I18n.format("gui.cancel", new Object[0])));
		this.buttonList.add(new GuiSpaceButton(1, 135, this.height - 25, 100, 15, I18n.format("gui.travel", new Object[0])));
		this.buttonList.add(new GuiSpaceButton(2, 15, this.height / 2, 100, 15, "<"));
		this.buttonList.add(new GuiSpaceButton(3, 135, this.height / 2, 100, 15, ">"));
		this.currentPlanet = planets.get(0);
	}
	
	/**
	 * draws the buttons and planet. Called every tick.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().getResourceManager();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertexWithUV(0D, (double)this.height, zPos, 0D, 1D);
        worldrenderer.addVertexWithUV((double)this.width, (double)this.height, zPos, 1D, 1D);
        worldrenderer.addVertexWithUV((double)this.width, 0D, zPos, 1D, 0D);
        worldrenderer.addVertexWithUV(0D, 0D, zPos, 0D, 0D);
        tessellator.draw();
        
        renderPlanet(new ResourceLocation("futurecraft", currentPlanet.getImage()));
        this.drawComponents();
        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	/**
	 * Draws the labels. Not sure why this cant be in drawScreen, but it just is.
	 */
	private void drawComponents() {
		this.drawCenteredString(this.fontRendererObj, currentPlanet.getPlanetName(), this.width / 2, 10, 16777215);
	}
	
	/**
	 * Renders the planet with the specified image.
	 */
	private void renderPlanet(ResourceLocation img) {
		GL11.glPushMatrix();
		this.mc.getTextureManager().bindTexture(img);
        GL11.glScalef(150F, 150F, 150F);
        GL11.glTranslatef(3.5F, 1.2F, 3);
        GL11.glRotatef(planetRotation, 0F, 2.5F, 0F);
        GL11.glRotatef(270, 3.5F, 0F, 0F);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.draw(1.0F, 30, 30);
        planetRotation -= 0.01F;
        GL11.glPopMatrix();
	}
	
	/**
	 * This gui pauses the game.
	 */
	public boolean doesGuiPauseGame() {
		return true;
    }
	
	/**
	 * Handles the buttons being pressed.
	 */
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 0) {
				this.mc.displayGuiScreen((GuiScreen)null);
			}
			if (button.id == 2) {
				if (this.position + 1 < this.planets.size()) {
					this.position++;
					this.currentPlanet = this.planets.get(position);
				}
			}
			if (button.id == 3) {
				if (this.position > 0) {
					this.position--;
					this.currentPlanet = this.planets.get(position);
				}
			}
			if (button.id == 1) {
				
				System.out.println("travel was pressed, transferring to: " + this.currentPlanet.getDimensionId());
			}
		}
    }
}
