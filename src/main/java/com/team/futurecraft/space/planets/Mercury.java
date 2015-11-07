package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeSelena;

import net.minecraft.util.Vec3;

public class Mercury extends Planet {

	public Mercury(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 87.95f, 57.663f, 0.206f, 7.005f, 48.332f, 29.124f, 174.795f);
		this.physical = new PhysicalParameters(0.37773f, 4480f, 0, 58.646f, 291.2f, 7.01f, 48.42f);
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
	public String getColormap() {
		return "mercury_colormap";
	}
}
