package com.team.futurecraft.tick;

import org.lwjgl.input.Keyboard;

import com.team.futurecraft.gui.GuiNavigation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TickHandlerClient {
	public static void onTickEvent(ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		GuiScreen openGui = FMLClientHandler.instance().getClient().currentScreen;
		
		if (openGui == null || openGui instanceof GuiNavigation) {
			if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
				mc.thePlayer.openGui("futurecraft", 101, mc.thePlayer.worldObj, 0, 0, 0);
			}
		}
	}
}
