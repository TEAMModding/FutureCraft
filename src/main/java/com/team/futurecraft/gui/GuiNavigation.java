package com.team.futurecraft.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.SpaceRegistry;
import com.team.futurecraft.StartupCommon;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.network.TeleportMessage;
import com.team.futurecraft.rendering.Camera;
import com.team.futurecraft.rendering.ShaderOld;
import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Planet;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

/**
 * This is the gui screen class of the navigation gui.
 * 
 * @author Joseph
 */
public class GuiNavigation extends GuiScreen {
	private long time = 62755776000L;
	private float zPos = 0.1f;
	private float xPos = 0;
	private float yPos = 0.05f;
	private float xRot = 0;
	private float yRot = 30;
	private float zRot = 0;
	private static float movementSpeed = 0.001f;
	private ArrayList<CelestialObject> planets = new ArrayList<CelestialObject>();
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
		CelestialObject[] objects = FutureCraft.SOL.getChildren();
		int buttonCount = 0;
		for (int i = 0; i < objects.length; i++) {
			this.buttonList.add(new GuiPlanetButton(buttonCount, 0, buttonCount * 50, objects[i]));
			planets.add(objects[i]);
			buttonCount++;
			
			CelestialObject[] moons = objects[i].getChildren();
			for (int j = 0; j < moons.length; j++) {
				this.buttonList.add(new GuiPlanetButton(buttonCount, 40, buttonCount * 50, moons[j]));
				planets.add((Planet)moons[j]);
				buttonCount++;
			}
		}
		this.buttonList.add(new GuiSpaceButton(1000, this.width - 110, this.height - 30, 100, 20, "travel"));
		
		spaceRender = new SpaceRenderer();
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
		
		if (movementSpeed < 0f)
			movementSpeed = 0;
		
		super.handleMouseInput();
    }
	
	
	
	/**
	 * Handles keyboard input to move the player.
	 */
	public void handleKeyboardInput() throws IOException
    {
		double horizontal = Math.toRadians(xRot);
		double vertical = Math.toRadians(yRot);
		
		Vec3f direction = new Vec3f(
			Math.cos(vertical) * Math.sin(horizontal),
			Math.sin(vertical),
			Math.cos(vertical) * Math.cos(horizontal)
		);
		
		Vec3f right = new Vec3f(
			Math.sin(horizontal - 3.14f/2.0f),
			0,
			Math.cos(horizontal - 3.14f/2.0f)
		);
		
		Keyboard.enableRepeatEvents(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_N))
				time += (3600L * 24L);
			else if (Keyboard.isKeyDown(Keyboard.KEY_M))
				time += (3600L * 24L * 365L);
			else
				time += 3600L;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_N))
				time -= (3600L * 24L);
			else if (Keyboard.isKeyDown(Keyboard.KEY_M))
				time -= (3600L * 24L * 365L);
			else
				time -= 3600L;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.xPos += (float) direction.x * movementSpeed;
			this.yPos -= (float) direction.y * movementSpeed;
			this.zPos -= (float) direction.z * movementSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.xPos -= (float) direction.x * movementSpeed;
			this.yPos += (float) direction.y * movementSpeed;
			this.zPos += (float) direction.z * movementSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.xPos += (float) right.x * movementSpeed;
			this.zPos -= (float) right.z * movementSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.xPos -= (float) right.x * movementSpeed;
			this.zPos += (float) right.z * movementSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			this.zRot += 1;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			this.zRot -= 1;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
			movementSpeed *= 1.1f;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_F10)) {
			ShaderOld.reloadShaders();
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
			movementSpeed *= 0.9f;
		}
		super.handleKeyboardInput();
    }
	
	/**
	 * draws the buttons and planet. Called every tick.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Camera cam = new Camera(new Vec3f(this.xPos, this.yPos, this.zPos).add(planets.get(this.selectedPlanet).getPosition(time)), new Vec3f(this.xRot, this.yRot, this.zRot));
		this.spaceRender.render(cam, time, true);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        //java's dating system starts in 1970 while ours starts in 0, so we have to subtract a constant for the year 1970.
        Date date = new Date((time  - 62135769600L) * 1000);
        String timeDisplay;
        
        if (time >= 0)
        	timeDisplay = "raw time: " + time + ", real time: " + date.toGMTString();
        else 
        	timeDisplay = "raw time: " + time + ", real time: " + date.toGMTString() + " BC";
        
        this.drawCenteredString(this.fontRendererObj, timeDisplay, this.width / 2, 10, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "speed: " + String.valueOf(movementSpeed), (this.width / 2) + 250, 10, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, this.mc.debug, (this.width / 2) + 250, 110, 0xFFFFFF);
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
				TeleportMessage messageToServer = new TeleportMessage(SpaceRegistry.getDimensionForPlanet((Planet)planets.get(this.selectedPlanet)));
				StartupCommon.simpleNetworkWrapper.sendToServer(messageToServer);
			}
		}
		else {
			this.selectedPlanet = button.id;
		}
	}
}
