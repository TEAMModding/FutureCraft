package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeDesert;

import net.minecraft.util.Vec3;

public class Mars extends Planet {

	public Mars(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 686.565f, 227.076f, 0.093f, 1.851f, 49.479f, 286.562f, 19.412f);
		this.physical = new PhysicalParameters(0.37902f, 6792f, 0, 1.026f, 136.005f, 26.72f, 82.91f);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.DESERT_MARS;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeDesert();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.703, 0.467, 0.271);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.3f;
	}

	@Override
	public String getName() {
		return "Mars";
	}

	@Override
	public String getTexture() {
		return "mars";
	}
	
	@Override
	public String getColormap() {
		return "mars_colormap";
	}
}
