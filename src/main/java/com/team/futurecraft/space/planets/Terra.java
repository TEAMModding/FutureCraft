package com.team.futurecraft.space.planets;

import net.minecraft.util.Vec3;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeTerra;

public class Terra extends Planet {
	public Terra(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.TERRA;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeTerra();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.4, 0.4, 1);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.8f;
	}

	@Override
	public String getName() {
		return "Terra";
	}

	@Override
	public String getTexture() {
		return "earth";
	}

	@Override
	public float getGravity() {
		return 1.0f;
	}

	@Override
	public float getDiameter() {
		return 0.1f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(400.0f, 1f, 365f);
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
