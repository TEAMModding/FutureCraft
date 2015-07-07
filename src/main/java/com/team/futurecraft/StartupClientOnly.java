package com.team.futurecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClientOnly {
	public static void preInit() {
		
	}
	
	/**
	 * Initializes the client side.
	 * Register Item rendering and any other rendering here.
	 */
	public static void init() {	
		//metal blocks
		registerItemRendering("steel_plating");
		registerItemRendering("steel_vent");
		
		//earth ores
		registerItemRendering("malachite");
		registerItemRendering("cassiterite");
		registerItemRendering("bauxite");
		
		//desert ores
		registerItemRendering("desert_malachite");
		registerItemRendering("desert_cassiterite");
		registerItemRendering("desert_bauxite");
		
		//selena ores
		registerItemRendering("selena_malachite");
		registerItemRendering("selena_cassiterite");
		registerItemRendering("selena_bauxite");
		
		//selena blocks
		registerItemRendering("selena_stone");
		registerItemRendering("selena_dirt");
		registerItemRendering("selena_cobblestone");
		registerItemRendering("selena_stonebrick");
		
		//desert blocks
		registerItemRendering("desert_stone");
		registerItemRendering("desert_dirt");
		registerItemRendering("desert_cobblestone");
		registerItemRendering("desert_stonebrick");
		
		//machines
		registerItemRendering("alloy_furnace");
		registerItemRendering("alloy_furnace_lit");
		registerItemRendering("generator");
		registerItemRendering("generator_lit");
		registerItemRendering("battery");
		registerItemRendering("creative_battery");
		
		//misc
		registerItemRendering("navigator");
		registerItemRendering("dirty_ice");
		
		//<=======Items=======>
		
		//ingots
		registerItemRendering("copper_ingot");
		registerItemRendering("tin_ingot");
		registerItemRendering("bronze_ingot");
		registerItemRendering("steel_ingot");
		
		//bronze tools
		registerItemRendering("bronze_sword");
		registerItemRendering("bronze_shovel");
		registerItemRendering("bronze_axe");
		registerItemRendering("bronze_pickaxe");
		registerItemRendering("bronze_hoe");
		
		//steel tools
		registerItemRendering("steel_sword");
		registerItemRendering("steel_shovel");
		registerItemRendering("steel_axe");
		registerItemRendering("steel_pickaxe");
		registerItemRendering("steel_hoe");
		
		//misc
		registerItemRendering("stone_channel");
		registerItemRendering("stone_cast");
		registerItemRendering("multimeter");
	}
	
	/**Registers a new ItemModel.
	 * 
	 * Will only work if the itemModel json
	 * file has the same name as the registered
	 * blocks/items id.
	 * 
	 * @param name The itemModel/block name
	 */
	private static void registerItemRendering(String name) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.findItem(FutureCraft.MODID, name), 0, new ModelResourceLocation(FutureCraft.MODID + ":" + name, "inventory"));
	}
}
