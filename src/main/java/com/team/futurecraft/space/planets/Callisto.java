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

public class Callisto extends Planet {

	public Callisto(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 16.689f, 1.882974825f, 0.007f, 0.281f, 323.265f, 12.668f, -250.842f);
		this.physical = new PhysicalParameters(0.12612f, 4820.6f, 0, 16.689f, 0, 0.281f, 323.265f);
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
		return "Callisto";
	}

	@Override
	public String getTexture() {
		return "callisto";
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
