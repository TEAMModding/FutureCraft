package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;

import net.minecraft.util.Vec3;

public class Earth extends Planet {

	public Earth(CelestialObject parent) {
		super(parent);
		this.add(new Moon(this));
	}

	public BiomePlanet getBiome() {
		return null;
	}
	
	@Override
	public PlanetType getWorldType() {
		return null;
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
		return "Earth";
	}

	@Override
	public String getTexture() {
		return "earth";
	}

	@Override
	public float getGravity() {
		return 1.0f;
	}

	@Override
	public float getDiameter() {
		return 12756.28f;
	}

	@Override
	public OrbitalParameters getOrbit() {
		return new OrbitalParameters(149f, 365f, 1);
	}

	@Override
	public String getColormap() {
		return null;
	}
}
