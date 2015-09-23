package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeSelena;

import net.minecraft.util.Vec3;

public class Mercury extends Planet {

	public Mercury(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.SELENA_MERCURY;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeSelena(0.5f);
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0, 0, 0);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0f;
	}

	@Override
	public String getName() {
		return "Mercury";
	}

	@Override
	public String getTexture() {
		return "mercury";
	}

	@Override
	public float getGravity() {
		return 1.0f;
	}

	@Override
	public float getDiameter() {
		return 4480;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(87.95f, 57.663f, 0.206f, 7.005f, 48.332f, 29.124f, 174.795f, 58.646f);
	}
	
	@Override
	public String getColormap() {
		return "mercury_colormap";
	}
}
