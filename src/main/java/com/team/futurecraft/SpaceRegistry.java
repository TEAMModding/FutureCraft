package com.team.futurecraft;

import java.util.HashMap;

import org.apache.logging.log4j.Level;

import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Earth;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.world.WorldProviderPlanet;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.common.DimensionManager;

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
	private static int dimensionIndex = 100;
	
	private static void registerPlanet(Planet provider) {
		int id = dimensionIndex;
		try {
		} catch (Exception e) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.ERROR, "An error occurred while registering a planet id: " + id);
			throw new RuntimeException(e);
		}
		DimensionManager.registerProviderType(id, WorldProviderPlanet.class, true);
		DimensionManager.registerDimension(id, id);
		planets.put(id, provider);
		ids.put(provider, id);
		dimensionIndex++;
	}
	
	public static void registerSystem(CelestialObject object) {
		CelestialObject[] objects = object.getChildren();
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof Earth){}
			else {
				registerPlanet((Planet)objects[i]);
			}
			
			CelestialObject[] moons = objects[i].getChildren();
			for (int j = 0; j < moons.length; j++) {
				registerPlanet((Planet)moons[i]);
			}
		}
	}
	
	/**
	 * Returns an ArrayList of all the registered planets.
	 */
	public static Planet[] getRegisteredPlanets() {
		return planets.values().toArray(new Planet[] {});
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
