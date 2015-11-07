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

public class Enceladus extends Planet {

	public Enceladus(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 1.3702f, 0.238016f,  0f, 0.01f, 137.077f, 334.689f, 162.044f);
		this.physical = new PhysicalParameters(0.012041f, 498.8f, 0f, 1.3702f, 0, 0.01f, 137.077f);
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
	public String getColormap() {
		return null;
	}
}
