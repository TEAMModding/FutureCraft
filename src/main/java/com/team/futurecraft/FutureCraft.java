package com.team.futurecraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = FutureCraft.MODID, version = FutureCraft.VERSION)
/**
 * This is the main class for Futurecraft. Forge finds it, and loads it's methods during setup.
 * 
 * @author Joseph
 *
 */
public class FutureCraft 
{
	public static final String MODID = "futurecraft";
	public static final String VERSION = "Alpha 0.2";
	
	//creative tab
	public static final CreativeTabs tabFutureCraft = new CreativeTabs("futurecraft")
	{
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(BlockList.steel_plating);
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
	public void preInit(FMLPreInitializationEvent event) 
	{
		/*CoreEventHandler events = new CoreEventHandler();
		
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
		Registry.RegisterRendering();
		Registry.registerBlocks();
		Registry.registerItems();
		Registry.registerWorldHandlers();
		Registry.registerTileEntities();
		Registry.registerEntities();*/
		
		if (event.getSide() == Side.CLIENT) {
			StartupClientOnly.preInit();
		}
		StartupCommon.preInit();
	}
  
	/**
	 * Called during normal initializtion. Also passes to
	 * client/common handlers.
	 * 
	 * @param event info about the init event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
		/*Registry.registerRecipes();
		Registry.registerPlanets();
		NetworkRegistry.INSTANCE.registerGuiHandler("futurecraft", new GuiHandler());
		teleporter = NetworkRegistry.INSTANCE.newSimpleChannel("futurecraft:teleporter");
		teleporter.registerMessage(TeleportPacketHandler.class, TeleportPacket.class, 1, Side.SERVER);*/
		
		if (event.getSide() == Side.CLIENT) {
			StartupClientOnly.init();
		}
		StartupCommon.init();
	}
	
	@EventHandler
	public void init(FMLServerStartingEvent event)
	{
		/*MinecraftServer minecraftserver = MinecraftServer.getServer();
        WorldServer worldServer = minecraftserver.worldServerForDimension(-10);
        worldServer.customTeleporters.add(new TeleportHandler(worldServer));*/
	}
}
