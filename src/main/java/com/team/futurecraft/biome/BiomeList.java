package com.team.futurecraft.biome;

import com.team.futurecraft.space.planets.Europa;
import com.team.futurecraft.space.planets.Mars;
import com.team.futurecraft.space.planets.Moon;
import com.team.futurecraft.space.planets.Terra;

import net.minecraft.world.biome.BiomeGenBase;

/**
 * This is the class that holds all biomes that will be in use.
 * If you wish to add another biome, specify it here and then use it in your generation.
 * 
 * @author Joseph
 *
 */
public class BiomeList {
	public static final BiomeSelena SELENA_MOON = new BiomeSelena(100, new Moon(null));
	public static final BiomeDesert DESERT_MARS = new BiomeDesert(101, new Mars(null));
	public static final BiomeFrozen FROZEN_EUROPA = new BiomeFrozen(102, new Europa(null));
	public static final BiomeTerra TERRA = new BiomeTerra(103, new Terra(null));
}