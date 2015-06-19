package com.team.futurecraft;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.block.BlockAlloyFurnace;
import com.team.futurecraft.block.BlockBattery;
import com.team.futurecraft.block.BlockGenerator;
import com.team.futurecraft.block.BlockNavigator;
import com.team.futurecraft.block.BlockSimpleOre;
import com.team.futurecraft.block.BlockTeleporter;
import com.team.futurecraft.block.BlockWire;
import com.team.futurecraft.block.SimpleBlock;
import com.team.futurecraft.event.CoreEventHandler;
import com.team.futurecraft.gui.GuiHandler;
import com.team.futurecraft.item.ItemLaser;
import com.team.futurecraft.item.ItemMultimeter;
import com.team.futurecraft.item.SimpleAxe;
import com.team.futurecraft.item.SimpleItem;
import com.team.futurecraft.item.SimplePickaxe;
import com.team.futurecraft.recipe.AlloyRegistry;
import com.team.futurecraft.tileentity.TileEntityAlloyFurnace;
import com.team.futurecraft.tileentity.TileEntityGenerator;
import com.team.futurecraft.tileentity.TileEntityMachine;
import com.team.futurecraft.tileentity.TileEntityWire;
import com.team.futurecraft.world.OreSpawn;
import com.team.futurecraft.world.WorldProviderMars;
import com.team.futurecraft.world.WorldProviderMoon;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class is for initializing objects on the server-side and client-side
 * of the game, blocks/items/data etc.
 * 
 * @author Joseph
 *
 */
public class StartupCommon {
	/**
	 * Initialize blocks, items, and just about everything else here.
	 */
	public static void preInit() {
		
		registerItems();
		registerBlocks();
		
		CoreEventHandler events = new CoreEventHandler();
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
		
		GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, "futurecraft:alloy_furnace");
		GameRegistry.registerTileEntity(TileEntityMachine.class, "futurecraft:machine");
		GameRegistry.registerTileEntity(TileEntityWire.class, "futurecraft:wire");
		GameRegistry.registerTileEntity(TileEntityGenerator.class, "futurecraft:generator");
		
		GameRegistry.registerWorldGenerator(new OreSpawn(), 1);
	}
	
	/**
	 * Initialize recipe's and anything block/item-dependent, since this is after blocks/items
	 * are registered.
	 */
	public static void init() {	
		registerRecipes();
		
		NetworkRegistry.INSTANCE.registerGuiHandler("futurecraft", new GuiHandler());
		
		PlanetRegistry.registerEarth();
		PlanetRegistry.registerPlanet(-10, WorldProviderMoon.class);
		PlanetRegistry.registerPlanet(-11, WorldProviderMars.class);
	}
	
	/**
	 * Just a shortcut method to register items and keep code cleaner.
	 */
	public static void registerItems() {
		ToolMaterial bronzeMaterial = EnumHelper.addToolMaterial("bronze", 2, 350, 6.5F, 2.2F, 10);
		ToolMaterial steelMaterial = EnumHelper.addToolMaterial("steel", 3, 700, 7.0F, 2.7F, 12);
		
		//ingots
		ItemList.copper_ingot = new SimpleItem("copper_ingot");
		GameRegistry.registerItem(ItemList.copper_ingot, "copper_ingot");
		
		ItemList.tin_ingot = new SimpleItem("tin_ingot");
		GameRegistry.registerItem(ItemList.tin_ingot, "tin_ingot");
		
		ItemList.bronze_ingot = new SimpleItem("bronze_ingot");
		GameRegistry.registerItem(ItemList.bronze_ingot, "bronze_ingot");
		
		ItemList.steel_ingot = new SimpleItem("steel_ingot");
		GameRegistry.registerItem(ItemList.steel_ingot, "steel_ingot");
		
		//bronze tools
		ItemList.bronze_sword = (ItemSword) new ItemSword(bronzeMaterial).setUnlocalizedName("bronze_sword").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.bronze_sword, "bronze_sword");
		
		ItemList.bronze_shovel = (ItemSpade) new ItemSpade(bronzeMaterial).setUnlocalizedName("bronze_shovel").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.bronze_shovel, "bronze_shovel");
		
		ItemList.bronze_axe = (ItemAxe) new SimpleAxe(bronzeMaterial).setUnlocalizedName("bronze_axe").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.bronze_axe, "bronze_axe");
		
		ItemList.bronze_pickaxe = (ItemPickaxe) new SimplePickaxe(bronzeMaterial).setUnlocalizedName("bronze_pickaxe").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.bronze_pickaxe, "bronze_pickaxe");
		
		ItemList.bronze_hoe = (ItemHoe) new ItemHoe(bronzeMaterial).setUnlocalizedName("bronze_hoe").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.bronze_hoe, "bronze_hoe");
		
		//steel tools
		ItemList.steel_sword = (ItemSword) new ItemSword(steelMaterial).setUnlocalizedName("steel_sword").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.steel_sword, "steel_sword");
				
		ItemList.steel_shovel = (ItemSpade) new ItemSpade(steelMaterial).setUnlocalizedName("steel_shovel").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.steel_shovel, "steel_shovel");
				
		ItemList.steel_axe = (ItemAxe) new SimpleAxe(steelMaterial).setUnlocalizedName("steel_axe").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.steel_axe, "steel_axe");
				
		ItemList.steel_pickaxe = (ItemPickaxe) new SimplePickaxe(steelMaterial).setUnlocalizedName("steel_pickaxe").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.steel_pickaxe, "steel_pickaxe");
				
		ItemList.steel_hoe = (ItemHoe) new ItemHoe(steelMaterial).setUnlocalizedName("steel_hoe").setCreativeTab(FutureCraft.tabFutureCraft);
		GameRegistry.registerItem(ItemList.steel_hoe, "steel_hoe");
		
		//misc
		ItemList.stone_channel = new SimpleItem("stone_channel");
		GameRegistry.registerItem(ItemList.stone_channel, "stone_channel");
		
		ItemList.stone_cast = new SimpleItem("stone_cast");
		GameRegistry.registerItem(ItemList.stone_cast, "stone_cast");
		
		ItemList.multimeter = new ItemMultimeter("multimeter");
		GameRegistry.registerItem(ItemList.multimeter, "multimeter");
		ItemList.itemLaser = new ItemLaser();
		GameRegistry.registerItem(ItemList.itemLaser, "itemLaser");
	}
	
	/**
	 * Just a shortcut method to register blocks and keep code cleaner.
	 */
	private static void registerBlocks() {
		//metal blocks
		BlockList.steel_plating = new SimpleBlock(Material.iron, "steel_plating");
		GameRegistry.registerBlock(BlockList.steel_plating, "steel_plating");
				
		BlockList.steel_vent = new SimpleBlock(Material.iron, "steel_vent");
		GameRegistry.registerBlock(BlockList.steel_vent, "steel_vent");
				
		//earth ores
		BlockList.malachite = new BlockSimpleOre("malachite");
		GameRegistry.registerBlock(BlockList.malachite, "malachite");
				
		BlockList.cassiterite = new BlockSimpleOre("cassiterite");
		GameRegistry.registerBlock(BlockList.cassiterite, "cassiterite");
				
		BlockList.bauxite = new BlockSimpleOre("bauxite");
		GameRegistry.registerBlock(BlockList.bauxite, "bauxite");
				
		//selena ores
		BlockList.selena_malachite = new BlockSimpleOre("selena_malachite");
		GameRegistry.registerBlock(BlockList.selena_malachite, "selena_malachite");
				
		BlockList.selena_cassiterite = new BlockSimpleOre("selena_cassiterite");
		GameRegistry.registerBlock(BlockList.selena_cassiterite, "selena_cassiterite");
				
		BlockList.selena_bauxite = new BlockSimpleOre("selena_bauxite");
		GameRegistry.registerBlock(BlockList.selena_bauxite, "selena_bauxite");
				
		//desert ores
		BlockList.desert_malachite = new BlockSimpleOre("desert_malachite");
		GameRegistry.registerBlock(BlockList.desert_malachite, "desert_malachite");
				
		BlockList.desert_cassiterite = new BlockSimpleOre("desert_cassiterite");
		GameRegistry.registerBlock(BlockList.desert_cassiterite, "desert_cassiterite");
				
		BlockList.desert_bauxite = new BlockSimpleOre("desert_bauxite");
		GameRegistry.registerBlock(BlockList.desert_bauxite, "desert_bauxite");
				
		//selena blocks
		BlockList.selena_stone = new SimpleBlock(Material.rock, "selena_stone");
		GameRegistry.registerBlock(BlockList.selena_stone, "selena_stone");
				
		BlockList.selena_dirt = new SimpleBlock(Material.rock, "selena_dirt");
		GameRegistry.registerBlock(BlockList.selena_dirt, "selena_dirt");
				
		BlockList.selena_cobblestone = new SimpleBlock(Material.rock, "selena_cobblestone");
		GameRegistry.registerBlock(BlockList.selena_cobblestone, "selena_cobblestone");
				
		BlockList.selena_stonebrick = new SimpleBlock(Material.rock, "selena_stonebrick");
		GameRegistry.registerBlock(BlockList.selena_stonebrick, "selena_stonebrick");
				
		//desert blocks
		BlockList.desert_stone = new SimpleBlock(Material.rock, "desert_stone");
		GameRegistry.registerBlock(BlockList.desert_stone, "desert_stone");
				
		BlockList.desert_dirt = new SimpleBlock(Material.rock, "desert_dirt");
		GameRegistry.registerBlock(BlockList.desert_dirt, "desert_dirt");
						
		BlockList.desert_cobblestone = new SimpleBlock(Material.rock, "desert_cobblestone");
		GameRegistry.registerBlock(BlockList.desert_cobblestone, "desert_cobblestone");
						
		BlockList.desert_stonebrick = new SimpleBlock(Material.rock, "desert_stonebrick");
		GameRegistry.registerBlock(BlockList.desert_stonebrick, "desert_stonebrick");
		
		//machines
		BlockList.alloy_furnace = new BlockAlloyFurnace(false, "alloy_furnace");
		GameRegistry.registerBlock(BlockList.alloy_furnace, "alloy_furnace");
				
		BlockList.alloy_furnace_lit = new BlockAlloyFurnace(true, "alloy_furnace_lit");
		GameRegistry.registerBlock(BlockList.alloy_furnace_lit, "alloy_furnace_lit");
		
		BlockList.generator = new BlockGenerator(false, "generator");
		GameRegistry.registerBlock(BlockList.generator, "generator");
				
		BlockList.generator_lit = new BlockGenerator(true, "generator_lit");
		GameRegistry.registerBlock(BlockList.generator_lit, "generator_lit");
		
		BlockList.battery = new BlockBattery(false, "battery");
		GameRegistry.registerBlock(BlockList.battery, "battery");
		
		BlockList.creative_battery = new BlockBattery(true, "creative_battery");
		GameRegistry.registerBlock(BlockList.creative_battery, "creative_battery");
		
		BlockList.wire = new BlockWire("copper_wire");
		GameRegistry.registerBlock(BlockList.wire, "copper_wire");
		
		//misc
		BlockList.navigator = new BlockNavigator("navigator");
		GameRegistry.registerBlock(BlockList.navigator, "navigator");
		
		BlockList.earth_teleporter = new BlockTeleporter(0, "earth_teleporter");
		GameRegistry.registerBlock(BlockList.earth_teleporter, "earth_teleporter");
		
		BlockList.moon_teleporter = new BlockTeleporter(-10, "moon_teleporter");
		GameRegistry.registerBlock(BlockList.moon_teleporter, "moon_teleporter");
		
		BlockList.mars_teleporter = new BlockTeleporter(-11, "mars_teleporter");
		GameRegistry.registerBlock(BlockList.mars_teleporter, "mars_teleporter");
	}
	
	/**
	 * Just a shortcut method to register recipes and keep code cleaner.
	 */
	private static void registerRecipes() {
		CraftingManager recipe = CraftingManager.getInstance();
		
		GameRegistry.addSmelting(BlockList.malachite, new ItemStack(ItemList.copper_ingot, 1), 0.7F);
		GameRegistry.addSmelting(BlockList.cassiterite, new ItemStack(ItemList.tin_ingot, 1), 0.7F);
		GameRegistry.addSmelting(BlockList.selena_malachite, new ItemStack(ItemList.copper_ingot, 1), 0.7F);
		GameRegistry.addSmelting(BlockList.selena_cassiterite, new ItemStack(ItemList.tin_ingot, 1), 0.7F);
		GameRegistry.addSmelting(BlockList.desert_malachite, new ItemStack(ItemList.copper_ingot, 1), 0.7F);
		GameRegistry.addSmelting(BlockList.desert_cassiterite, new ItemStack(ItemList.tin_ingot, 1), 0.7F);
		
		AlloyRegistry.registerAlloyRecipe(new ItemStack(ItemList.copper_ingot), new ItemStack(ItemList.tin_ingot), new ItemStack(ItemList.bronze_ingot));
		AlloyRegistry.registerAlloyRecipe(new ItemStack(BlockList.malachite), new ItemStack(BlockList.cassiterite), new ItemStack(ItemList.bronze_ingot, 2));
		AlloyRegistry.registerAlloyRecipe(new ItemStack(BlockList.selena_malachite), new ItemStack(BlockList.selena_cassiterite), new ItemStack(ItemList.bronze_ingot, 2));
		AlloyRegistry.registerAlloyRecipe(new ItemStack(BlockList.desert_malachite), new ItemStack(BlockList.desert_cassiterite), new ItemStack(ItemList.bronze_ingot, 2));
		
		recipe.addRecipe(new ItemStack(ItemList.stone_cast), new Object[] { "A A", "AAA",'A', Blocks.stone});
		recipe.addRecipe(new ItemStack(ItemList.stone_channel), new Object[] { "A A", " A ",'A', Blocks.stone});
		//recipe.addRecipe(new ItemStack(BlockList.alloy_furnace), new Object[] {"AAA", "B B", "ACA", 'A', Blocks.cobblestone, 'B', ItemList.stone_channel, 'C', ItemList.stone_cast});
		
		//bronze tool recipes
		recipe.addRecipe(new ItemStack(ItemList.bronze_sword, 1), new Object[] { "A", "A","B",'A', ItemList.bronze_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.bronze_shovel, 1), new Object[] { "A", "B","B",'A', ItemList.bronze_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.bronze_hoe, 1), new Object[] { "AA", " B"," B",'A', ItemList.bronze_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.bronze_pickaxe, 1), new Object[] { "AAA", " B "," B ",'A', ItemList.bronze_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.bronze_axe, 1), new Object[] { "AA", "AB"," B",'A', ItemList.bronze_ingot, 'B', Items.stick });
		
		//steel tool recipes
		recipe.addRecipe(new ItemStack(ItemList.steel_sword, 1), new Object[] { "A", "A","B",'A', ItemList.steel_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.steel_shovel, 1), new Object[] { "A", "B","B",'A', ItemList.steel_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.steel_hoe, 1), new Object[] { "AA", " B"," B",'A', ItemList.steel_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.steel_pickaxe, 1), new Object[] { "AAA", " B "," B ",'A', ItemList.steel_ingot, 'B', Items.stick });
		recipe.addRecipe(new ItemStack(ItemList.steel_axe, 1), new Object[] { "AA", "AB"," B",'A', ItemList.steel_ingot, 'B', Items.stick });
	}
}
