package com.team.futurecraft;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.team.futurecraft.world.WorldProviderEarth;
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
public class PlanetRegistry {
	private static ArrayList<WorldProviderPlanet> planets = new ArrayList<WorldProviderPlanet>();
	
	/**
	 * Registers earth, instead of using the normal method, or else it would
	 * make it's own dimension.
	 */
	public static void registerEarth() {
		planets.add(new WorldProviderEarth());
	}
	
	/**
	 * Registers a new planet.
	 * 
	 * @param id The Dimension Id
	 * @param provider The WorldProviderPlanet class defining the planet
	 */
	public static void registerPlanet(int id, Class<?extends WorldProviderPlanet> provider) {
		WorldProviderPlanet planet;
		try {
			planet = provider.newInstance();
		} catch (Exception e) {
			FMLCommonHandler.instance().getFMLLogger().log(Level.ERROR, "An error occurred while registering a planet id: " + id);
			throw new RuntimeException(e);
		} 
		DimensionManager.registerProviderType(id, provider, true);
		DimensionManager.registerDimension(id, id);
		planets.add(planet);
	}
	
	/**
	 * Returns an ArrayList of all the registered planets.
	 */
	public static ArrayList<WorldProviderPlanet> getRegisteredPlanets() {
		return planets;
	}
}
