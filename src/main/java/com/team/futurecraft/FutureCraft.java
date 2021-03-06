package com.team.futurecraft;

import com.team.futurecraft.space.Sol;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = FutureCraft.MODID, version = FutureCraft.VERSION)
/**
 * This is the main class for Futurecraft. Forge finds it, and loads it's methods during setup.
 * 
 * @author Joseph
 *
 */
public class FutureCraft {
	public static final String MODID = "futurecraft";
	public static final String VERSION = "0.2";
	public static long timeOffset = 0;
	public static final Sol SOL = new Sol(null);
	
	//creative tab
	public static final CreativeTabs tabFutureCraft = new CreativeTabs("futurecraft") {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ItemList.creative_tab;
		}
		
		@SideOnly(Side.CLIENT)
		public int func_151243_f() {
			return 5;
		}
	};
	
	/**
	 * Called during pre-initialization, passes it on to either
	 * client or common handlers.
	 * 
	 * @param event info about the preInit event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		StartupCommon.preInit();
		if (event.getSide() == Side.CLIENT) {
			StartupClientOnly.preInit();
		}
	}
  
	/**
	 * Called during normal initializtion. Also passes to
	 * client/common handlers.
	 * 
	 * @param event info about the init event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event)  {
		StartupCommon.init();
		if (event.getSide() == Side.CLIENT) {
			StartupClientOnly.init();
		}
	}
}
