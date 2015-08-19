package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeDesert;

import net.minecraft.util.Vec3;

public class Mars extends Planet {

	public Mars(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.DESERT_MARS;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeDesert();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.703, 0.467, 0.271);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.7f;
	}

	@Override
	public String getName() {
		return "Mars";
	}

	@Override
	public String getTexture() {
		return "mars";
	}

	@Override
	public float getGravity() {
		return 0.5f;
	}

	@Override
	public float getDiameter() {
		return 0.1f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(600.0f, 1.881f, 365f);
	}
	
	@Override
	public String getColormap() {
		return "mars_colormap";
	}
}
