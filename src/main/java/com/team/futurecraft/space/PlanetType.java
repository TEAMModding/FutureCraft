package com.team.futurecraft.space;

import com.team.futurecraft.Noise;

import net.minecraft.block.Block;

/**
 * This is the WorldType class, all planets must specify a class that extends this.
 * This pretty much specifies all world generation. Yeah we need more options in here...
 * 
 * @author Joseph
 */
public abstract class PlanetType {
	public abstract Block getStoneBlock();
	
	public abstract int getHeight(int x, int z, Noise noise);
	
	public boolean hasWater() {
		return false;
	}
}
