package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeFrozen;

import net.minecraft.util.Vec3;

public class Ganymede extends Planet {

	public Ganymede(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 7.155f, 1.069985695f, 0.002f, 0.195f, 119.841f, 68.99f, -67.625f);
		this.physical = new PhysicalParameters(0.14572f, 5262.4f, 0, 7.1546f, 0, 0.195f, 119.841f);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeFrozen();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(1, 1, 1);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.0f;
	}

	@Override
	public String getName() {
		return "Ganymede";
	}

	@Override
	public String getTexture() {
		return "ganymede";
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
