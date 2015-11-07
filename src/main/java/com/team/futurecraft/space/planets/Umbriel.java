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

public class Umbriel extends Planet {

	public Umbriel(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 4.144f, 0.265996444f, 0.005f, 0.36f, 0, 0, 280f);
		this.physical = new PhysicalParameters(0.023323f, 1169.4f, 0, 4.133f, 0, 0.36f, 0);
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
		return "Umbriel";
	}

	@Override
	public String getTexture() {
		return "umbriel";
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
