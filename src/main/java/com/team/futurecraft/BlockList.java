package com.team.futurecraft;


import com.team.futurecraft.block.BlockAlloyFurnace;
import com.team.futurecraft.block.BlockBattery;
import com.team.futurecraft.block.BlockBatteryNew;
import com.team.futurecraft.block.BlockGenerator;
import com.team.futurecraft.block.BlockNavigator;
import com.team.futurecraft.block.BlockPlanetStone;
import com.team.futurecraft.block.BlockRocketCore;
import com.team.futurecraft.block.BlockSimpleOre;
import com.team.futurecraft.block.BlockWire;
import com.team.futurecraft.block.SimpleBlock;

/**
 * This class holds all the Block types for the mod.
 * A block type should be instantiated in StartupCommon and then registered.
 * Whenever referencing a block for use such as setting a block, reference it here.
 * It is also handy to name the actual type of the block such as SimpleBlock instead of just Block.
 * 
 * @author Joseph
 *
 */
public class BlockList {
	//metal blocks
	public static SimpleBlock steel_plating;
	public static SimpleBlock steel_vent;
	
	//earth ores
	public static BlockSimpleOre malachite;
	public static BlockSimpleOre cassiterite;
	public static BlockSimpleOre bauxite;
	
	//selena blocks
	public static SimpleBlock selena_dirt;
	public static BlockPlanetStone selena_stone;
	
	//desert blocks
	public static SimpleBlock desert_dirt;
	public static BlockPlanetStone desert_stone;
	
	//machines
	public static BlockAlloyFurnace alloy_furnace;
	public static BlockAlloyFurnace alloy_furnace_lit;
	public static BlockBattery battery;
	public static BlockBattery creative_battery;
	public static BlockWire wire;
	public static BlockGenerator generator;
	public static BlockGenerator generator_lit;
	
	public static BlockBatteryNew battery_new;
	
	//rocket parts
	public static BlockRocketCore rocket_core;
	
	//misc
	public static BlockNavigator navigator;
	public static SimpleBlock dirty_ice;
}
