package com.team.futurecraft.biome;

import net.minecraft.world.biome.BiomeGenBase;

/**
 * This is the class that holds all biomes that will be in use.
 * If you wish to add another biome, specify it here and then use it in your generation.
 * 
 * @author Joseph
 *
 */
public class BiomeList {
	private static final BiomeGenBase.Height height_flat = new BiomeGenBase.Height(0.1F, 0.02F);
	private static final BiomeGenBase.Height height_rough = new BiomeGenBase.Height(0.1F, 0.4F);
	
	public static final BiomeGenBase moon = (new BiomeGenMoon(100)).setColor(16421912).setBiomeName("moon").setDisableRain().setTemperatureRainfall(100.0F, 0.0F).setHeight(height_flat);
	public static final BiomeGenBase mars = (new BiomeGenMars(101)).setColor(16421912).setBiomeName("mars").setDisableRain().setTemperatureRainfall(100.0F, 0.0F).setHeight(height_rough);
}