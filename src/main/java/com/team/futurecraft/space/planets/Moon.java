package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeSelena;

import net.minecraft.util.Vec3;

public class Moon extends Planet {

	public Moon(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.SELENA_MOON;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeSelena();
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
		return "Moon";
	}

	@Override
	public String getTexture() {
		return "moon";
	}

	@Override
	public float getGravity() {
		return 0.3f;
	}

	@Override
	public float getDiameter() {
		return 0.04f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(3.0f, 27f, 27f);
	}
	
	@Override
	public String getColormap() {
		return "moon_colormap";
	}
}
