package com.team.futurecraft;

import java.util.HashMap;

import org.apache.logging.log4j.Level;

import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.planets.Earth;
import com.team.futurecraft.world.WorldProviderPlanet;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * This is the registry for all planets in this mod.
 * Similar to block and recipe registries.
 * 
 * @author Joseph
 *
 */
public class SpaceRegistry {
	private static HashMap<Integer, Planet> planets = new HashMap<Integer, Planet>();
	private static HashMap<Planet, Integer> ids = new HashMap<Planet, Integer>();
	private static HashMap<Planet, BiomePlanet> biomes = new HashMap<Planet, BiomePlanet>();
	private static int dimensionIndex = 100;
	
	private static void registerPlanet(Planet provider) {
		int id = dimensionIndex;
		
		try {
			BiomePlanet biome = provider.type.getBiome().getDeclaredConstructor(int.class, Planet.class).newInstance(id, provider);
			biomes.put(provider, biome);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		} catch (Exception e) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.ERROR, "An error occurred while registering a planet id: " + id);
			throw new RuntimeException(e);
		}
		DimensionManager.registerProviderType(id, WorldProviderPlanet.class, false);
		DimensionManager.registerDimension(id, id);
		planets.put(id, provider);
		ids.put(provider, id);
		dimensionIndex++;
	}
	
	public static void registerSystem(CelestialObject object) {
		CelestialObject[] objects = object.getChildren();
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].isLandable()) {
				if (objects[i] instanceof Earth){}
				else {
					registerPlanet((Planet)objects[i]);
				}
			}
			CelestialObject[] moons = objects[i].getChildren();
			for (int j = 0; j < moons.length; j++) {
				if (moons[j] instanceof Earth){}
				else {
					registerPlanet((Planet)moons[j]);
				}
			}
		}
	}
	
	/**
	 * Returns an ArrayList of all the registered planets.
	 */
	public static Planet[] getRegisteredPlanets() {
		return planets.values().toArray(new Planet[] {});
	}
	
	public static BiomePlanet getBiomeForPlanet(Planet planet) {
		return biomes.get(planet);
	}
	
	public static Planet getPlanetForDimension(int id) {
		return planets.get(id);
	}
	
	public static int getDimensionForPlanet(Planet planet) {
		if (planet instanceof Earth)
			return 0;
		
		return ids.get(planet);
	}
}
