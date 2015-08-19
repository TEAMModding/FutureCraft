package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeSelena;

import net.minecraft.util.Vec3;

public class Venus extends Planet {

	public Venus(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.DESERT_MARS;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeSelena();
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
		return "Venus";
	}

	@Override
	public String getTexture() {
		return "venus";
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
		return new OrbitalParameters(300.0f, 1f, 365f);
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
