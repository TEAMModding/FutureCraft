package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeFrozen;

import net.minecraft.util.Vec3;

public class Enceladus extends Planet {

	public Enceladus(CelestialObject parent) {
		super(parent);
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
		return "Enceladus";
	}

	@Override
	public String getTexture() {
		return "enceladus";
	}

	@Override
	public float getGravity() {
		return 0.3f;
	}

	@Override
	public float getDiameter() {
		return 498.8f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(0.238016f, 1.370f, 1.370f);
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}