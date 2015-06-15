package com.team;

import com.team.block.BlockNavigator;
import com.team.block.BlockSimpleOre;
import com.team.block.BlockTeleporter;
import com.team.block.SimpleBlock;

/**
 * This class holds all the Block types for the mod.
 * A block type should be instantiated in StartupCommon and then registered.
 * Whenever referencing a block for use such as setting a block, reference it here.
 * It is also handy to name the actual type of the block such as SimpleBlock instead of just Block.
 * 
 * @author Joseph
 *
 */
public class BlockList 
{
	//metal blocks
	public static SimpleBlock steel_plating;
	public static SimpleBlock steel_vent;
	
	//earth ores
	public static BlockSimpleOre malachite;
	public static BlockSimpleOre cassiterite;
	public static BlockSimpleOre bauxite;
	
	//selena ores
	public static BlockSimpleOre selena_malachite;
	public static BlockSimpleOre selena_cassiterite;
	public static BlockSimpleOre selena_bauxite;
	
	//desert ores
	public static BlockSimpleOre desert_malachite;
	public static BlockSimpleOre desert_cassiterite;
	public static BlockSimpleOre desert_bauxite;
	
	//selena blocks
	public static SimpleBlock selena_stone;
	public static SimpleBlock selena_dirt;
	public static SimpleBlock selena_cobblestone;
	public static SimpleBlock selena_stonebrick;
	
	//desert blocks
	public static SimpleBlock desert_stone;
	public static SimpleBlock desert_dirt;
	public static SimpleBlock desert_cobblestone;
	public static SimpleBlock desert_stonebrick;
	
	//misc
	public static BlockNavigator navigator;
	public static BlockTeleporter earth_teleporter;
	public static BlockTeleporter moon_teleporter;
	public static BlockTeleporter mars_teleporter;
}
