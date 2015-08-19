package com.team.futurecraft.space;

import com.team.futurecraft.biome.BiomePlanet;

import net.minecraft.block.Block;

/**
 * This is the WorldType class, all planets must specify a class that extends this.
 * This pretty much specifies all world generation. Yeah we need more options in here...
 * 
 * @author Joseph
 */
public abstract class WorldType {
	public abstract Block getStoneBlock();
	
	public abstract BiomePlanet getBiome();
}
