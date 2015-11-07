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

public class Miranda extends Planet {

	public Miranda(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 1.413f, 0.129798265f, 0.003f, 4.220f, 0, 0, 120f);
		this.physical = new PhysicalParameters(0.0080481f, 471f, 0, 1.413f, 0, 4.22f, 0);
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
		return "Miranda";
	}

	@Override
	public String getTexture() {
		return "miranda";
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
