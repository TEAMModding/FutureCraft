package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeDesert;

import net.minecraft.util.Vec3;

public class Venus extends Planet {

	public Venus(CelestialObject parent) {
		super(parent);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.DESERT_VENUS;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeDesert();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.62, 0.54, 0.4);
	}

	@Override
	public float getAtmosphericDensity() {
		return 1f;
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
		return 12104f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(224.697f, 107.727f, 0.007f, 3.395f, 76.681f, 54.852f, 50.446f, 243.020f);
	}
	
	@Override
	public String getColormap() {
		return "venus_colormap";
	}
}
