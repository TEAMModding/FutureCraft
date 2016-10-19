package com.team.futurecraft;

import com.team.futurecraft.entity.ChunkEntity;
import com.team.futurecraft.rendering.Assets;
import com.team.futurecraft.rendering.entity.RenderChunkEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StartupClientOnly {
	public static void preInit() {
		
	}
	
	/**
	 * Initializes the client side.
	 * Register Item rendering and any other rendering here.
	 */
	public static void init() {	
		RenderingRegistry.registerEntityRenderingHandler(ChunkEntity.class, new RenderChunkEntity(Minecraft.getMinecraft().getRenderManager()));
		Assets.loadAssets();
		
		//metal blocks
		registerItemRendering("steel_plating");
		registerItemRendering("steel_vent");
		
		//earth ores
		registerItemRendering("malachite");
		registerItemRendering("cassiterite");
		registerItemRendering("bauxite");
		
		//selena blocks
		registerMetaItemRendering("selena_stone", 
				"selena_stone",
				"selena_bricks",
				"selena_cobblestone",
				"selena_malachite",
				"selena_cassiterite",
				"selena_bauxite");
		registerItemRendering("selena_dirt");
		
		//desert blocks
		registerMetaItemRendering("desert_stone", 
				"desert_stone",
				"desert_bricks",
				"desert_cobblestone",
				"desert_malachite",
				"desert_cassiterite",
				"desert_bauxite");
		registerItemRendering("desert_dirt");
		
		//machines
		registerItemRendering("alloy_furnace");
		registerItemRendering("alloy_furnace_lit");
		registerItemRendering("generator");
		registerItemRendering("generator_lit");
		registerItemRendering("battery");
		registerItemRendering("creative_battery");
		
		//rocket parts
		registerItemRendering("rocket_core");
		
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
		
		//space suit
		registerItemRendering("space_suit_helmet");
		registerItemRendering("space_suit_chestplate");
		registerItemRendering("space_suit_leggings");
		registerItemRendering("space_suit_boots");
		
		//misc
		registerItemRendering("stone_channel");
		registerItemRendering("stone_cast");
		registerItemRendering("multimeter");
		registerItemRendering("laser");
		registerItemRendering("creative_tab");
	}
	
	/**Registers a new ItemModel.
	 * 
	 * Will only work if the itemModel json
	 * file has the same name as the registered
	 * blocks/items id.
	 * 
	 * @param name The itemModel/block name
	 */
	private static void registerItemRendering(Item item, int meta, String modelName) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(FutureCraft.MODID + ":" + modelName, "inventory"));
	}
	
	private static void registerMetaItemRendering(String name, String... modelNames) {
		Item item = GameRegistry.findItem(FutureCraft.MODID, name);
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		String[] newModelNames = new String[modelNames.length];
		for (int i = 0; i < modelNames.length; i++) {
			newModelNames[i] = FutureCraft.MODID + ":" + modelNames[i];
		}
		
		ModelBakery.addVariantName(item, newModelNames);
		
		for (int i = 0; i < modelNames.length; i++) {
			mesher.register(item, i, new ModelResourceLocation(FutureCraft.MODID + ":" + modelNames[i], "inventory"));
		}
	}
	
	private static void registerItemRendering(String name) {
		registerItemRendering(GameRegistry.findItem(FutureCraft.MODID, name), 0, name);
	}
}
