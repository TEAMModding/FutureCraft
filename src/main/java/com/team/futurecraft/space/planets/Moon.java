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

public class Moon extends Planet {

	public Moon(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 27.322f, 0.379666035f, 0.055f, 5.15f, 125.08f, 318.15f, 135.27f);
		this.physical = new PhysicalParameters(0.16579f, 3474.8f, 0, 27.322f, 0, 5.15f, 125.08f);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.SELENA_MOON;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeSelena(0.0f);
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
	public String getColormap() {
		return "moon_colormap";
	}
}
