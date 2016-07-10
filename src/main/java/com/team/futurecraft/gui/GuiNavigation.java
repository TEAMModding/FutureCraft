package com.team.futurecraft.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.SpaceRegistry;
import com.team.futurecraft.StartupCommon;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.network.TeleportMessage;
import com.team.futurecraft.rendering.Assets;
import com.team.futurecraft.rendering.Camera;
import com.team.futurecraft.rendering.ShaderOld;
import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.SpaceJSONLoader;

import net.minecraft.client.Minecraft;
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
	private static float movementSpeed = 1000f;
	private ArrayList<CelestialObject> planets = new ArrayList<CelestialObject>();
	private int selectedPlanet = 0;
	private Camera cam = new Camera();
	
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
		CelestialObject[] objects = SpaceJSONLoader.starSystem.getChildren();
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
		
		//cam.position = FutureCraft.SOL.getChildren()[2].getPosition(time);
	}
	
	
	public void doInput()
    {
		//keyboard input
		cam.front.x = (float) (Math.cos(Math.toRadians(cam.pitch)) * Math.cos(Math.toRadians(cam.yaw)));
		cam.front.y = (float) Math.sin(Math.toRadians(cam.pitch));
		cam.front.z = (float) (Math.cos(Math.toRadians(cam.pitch)) * Math.sin(Math.toRadians(cam.yaw)));
		
		cam.front = cam.front.normalize();
		//1391400000
		float cameraSpeed = 100000f;
	    if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
	    	cam.position = cam.position.add((cam.front.multiply(cameraSpeed)));
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
	    	cam.position = cam.position.subtract((cam.front.multiply(cameraSpeed)));
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
	    	cam.position = cam.position.subtract(((cam.front.cross(cam.up)).normalize()).multiply(cameraSpeed));
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
	    	cam.position = cam.position.add(((cam.front.cross(cam.up)).normalize()).multiply(cameraSpeed));
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
	    	//roll += 0.5f;
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
	    	///roll -= 0.5f;
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
	    	cam.position = cam.position.add(new Vec3f(0, cameraSpeed, 0));
	    }
	    if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
	    	cam.position = cam.position.add(new Vec3f(0, -cameraSpeed, 0));
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
	    	System.out.println("teleporting");
	    	cam.position.y = 10000000000000000000000000000000000000f;
	    }
	    
	    if (Keyboard.isKeyDown(Keyboard.KEY_F8)) {
	    	Assets.loadShaders();
	    }
	    
	    if (Mouse.isButtonDown(1)) {
			Mouse.setGrabbed(true);
			float mouseSpeed = 0.1f;
			double mouseX = Mouse.getEventX();
			double mouseY = Mouse.getEventY();
			cam.yaw -= (float) (mouseSpeed * (this.mc.displayWidth /2 - mouseX));
			cam.pitch -= (float) (mouseSpeed * (this.mc.displayHeight /2 - mouseY));
			Mouse.setCursorPosition(this.mc.displayWidth / 2, this.mc.displayHeight / 2);
		}
		else {
			Mouse.setGrabbed(false);
		}
		
		cam.front.x = (float) (Math.cos(Math.toRadians(cam.pitch)) * Math.cos(Math.toRadians(cam.yaw)));
		cam.front.y = (float) Math.sin(Math.toRadians(cam.pitch));
		cam.front.z = (float) (Math.cos(Math.toRadians(cam.pitch)) * Math.sin(Math.toRadians(cam.yaw)));
		
		cam.front = cam.front.normalize();
		//Mouse.setCursorPosition(10, 10);
    }
	
	/**
	 * draws the buttons and planet. Called every tick.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		doInput();
		FutureCraft.spacerenderer.render(cam, time, true);
        
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
        this.drawCenteredString(this.fontRendererObj, "X: " + this.cam.position.x, (this.width / 2) + 250, 130, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Y: " + this.cam.position.y, (this.width / 2) + 250, 140, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Z: " + this.cam.position.z, (this.width / 2) + 250, 150, 0xFFFFFF);
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
			cam.position = ((Planet)planets.get(this.selectedPlanet)).getPosition(time);
		}
	}
}
