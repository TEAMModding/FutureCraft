package com.team.futurecraft.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.SpaceRegistry;
import com.team.futurecraft.StartupCommon;
import com.team.futurecraft.network.TeleportMessage;
import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Planet;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/**
 * This is the gui screen class of the navigation gui.
 * 
 * @author Joseph
 */
public class GuiNavigation extends GuiScreen {
	private float time = 0;
	private float zPos = -0.3f;
	private float xPos = 0;
	private float yPos = 0;
	private float xRot = 0;
	private float yRot = 30;
	private float zRot = 0;
	private float movementSpeed = 0.01f;
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	private static final float TIME_SCALE = 0.0003f;
	private int selectedPlanet = 0;
	private SpaceRenderer spaceRender;
	
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
			this.buttonList.add(new GuiPlanetButton(buttonCount, 0, buttonCount * 50, objects[i]));
			planets.add((Planet)objects[i]);
			buttonCount++;
			
			CelestialObject[] moons = objects[i].getChildren();
			for (int j = 0; j < moons.length; j++) {
				this.buttonList.add(new GuiPlanetButton(buttonCount, 40, buttonCount * 50, moons[j]));
				planets.add((Planet)moons[j]);
				buttonCount++;
			}
		}
		this.buttonList.add(new GuiSpaceButton(1000, this.width - 110, this.height - 30, 100, 20, "travel"));
		
		
		
		spaceRender = new SpaceRenderer(this.mc, true);
	}
	
	/**
	 * Handles the mouse input to rotate the camera.
	 */
	public void handleMouseInput() throws IOException
    {
		if (Mouse.isButtonDown(1)) {
			Mouse.setGrabbed(true);
			float mouseSpeed = 0.1f;
			double mouseX = Mouse.getEventX();
			double mouseY = Mouse.getEventY();
			xRot -= (float) (mouseSpeed * (this.mc.displayWidth /2 - mouseX));
			yRot += (float) (mouseSpeed * (this.mc.displayHeight /2 - mouseY));
			Mouse.setCursorPosition(this.mc.displayWidth / 2, this.mc.displayHeight / 2);
		}
		else {
			Mouse.setGrabbed(false);
		}
		//this.movementSpeed += ((float)Mouse.getEventDWheel() / 120f) * 0.001f;
		
		int mouseMove = Mouse.getEventDWheel();
		if (mouseMove != 0) {
			@SuppressWarnings("rawtypes")
			Iterator iterator = this.buttonList.iterator();
			
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				if (obj instanceof GuiPlanetButton) {
					GuiPlanetButton button = (GuiPlanetButton)obj;
					button.yPosition += mouseMove / 10;
				}
			}
		}
		
		if (this.movementSpeed < 0f)
			this.movementSpeed = 0;
		
		super.handleMouseInput();
    }
	
	
	
	/**
	 * Handles keyboard input to move the player.
	 */
	public void handleKeyboardInput() throws IOException
    {
		double horizontal = Math.toRadians(xRot);
		double vertical = Math.toRadians(yRot);
		
		Vec3 direction = new Vec3(
			Math.cos(vertical) * Math.sin(horizontal),
			Math.sin(vertical),
			Math.cos(vertical) * Math.cos(horizontal)
		);
		
		Vec3 right = new Vec3(
			Math.sin(horizontal - 3.14f/2.0f),
			0,
			Math.cos(horizontal - 3.14f/2.0f)
		);
		
		Keyboard.enableRepeatEvents(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			FutureCraft.timeOffset += 100f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			FutureCraft.timeOffset -= 100f;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.xPos -= (float) direction.xCoord * movementSpeed;
			this.yPos += (float) direction.yCoord * movementSpeed;
			this.zPos += (float) direction.zCoord * movementSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.xPos += (float) direction.xCoord * movementSpeed;
			this.yPos -= (float) direction.yCoord * movementSpeed;
			this.zPos -= (float) direction.zCoord * movementSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.xPos -= (float) right.xCoord * movementSpeed;
			this.zPos += (float) right.zCoord * movementSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.xPos += (float) right.xCoord * movementSpeed;
			this.zPos -= (float) right.zCoord * movementSpeed;
		}
		
		super.handleKeyboardInput();
    }
	
	/**
	 * draws the buttons and planet. Called every tick.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {		
		this.spaceRender.render(new Vec3(this.xRot, this.yRot, this.zRot), new Vec3(this.xPos, this.yPos, this.zPos), planets.get(this.selectedPlanet), time, false);
        time = (float) (System.nanoTime() * 0.000000001 * TIME_SCALE)  + FutureCraft.timeOffset;
		
        
        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	/**
	 * This gui pauses the game.
	 */
	public boolean doesGuiPauseGame() {
		return true;
    }

	/**
	 * Sends a teleport message to the server when a button is pressed.
	 */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 1000) {
			if (planets.get(this.selectedPlanet).isLandable()) {
				System.out.println(planets.get(this.selectedPlanet));
				TeleportMessage airstrikeMessageToServer = new TeleportMessage(SpaceRegistry.getDimensionForPlanet(planets.get(this.selectedPlanet)));
				StartupCommon.simpleNetworkWrapper.sendToServer(airstrikeMessageToServer);
			}
		}
		else {
			this.selectedPlanet = button.id;
		}
	}
}
