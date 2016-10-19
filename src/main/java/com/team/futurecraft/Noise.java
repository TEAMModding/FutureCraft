package com.team.futurecraft;

import java.util.Random;

import net.minecraft.world.gen.NoiseGeneratorSimplex;

/**
 * This class is a utility for any kind of procedural generation.
 * Currently being used by planet chunk providers.
 * 
 * @author Joseph
 *
 */
public class Noise {
	private NoiseGeneratorSimplex simplex;
	
	/**
	 * Creates a new Noise object with the specified seed. When doing 
	 * anything related to a world you want to use worldObj.getSeed().
	 */
	public Noise(long seed) {
		this.simplex = new NoiseGeneratorSimplex(new Random(seed));
	}
	
	public float defaultNoise(Vec3f position, int octaves, float frequency, float persistence) {
		float total = 0.0f;
	    float maxAmplitude = 0.0f;
	    float amplitude = 1.0f;
	    
	    for (int i = 0; i < octaves; i++) {

	        // Get the noise sample
	        total += simplex.func_151605_a(position.x * frequency, position.z * frequency) * amplitude;

	        // Make the wavelength twice as small
	        frequency *= 2.0;

	        // Add to our maximum possible amplitude
	        maxAmplitude += amplitude;

	        // Reduce amplitude according to persistence for the next octave
	        amplitude *= persistence;
	    }

	    // Scale the result by the maximum amplitude
	    return total / maxAmplitude;
	}
	
	public float ridgedNoise(Vec3f position, int octaves, float frequency, float persistence) {
		float total = 0.0f;
	    float maxAmplitude = 0.0f;
	    float amplitude = 1.0f;
	    
	    for (int i = 0; i < octaves; i++) {

	        // Get the noise sample
	        total += ((1.0 - Math.abs(simplex.func_151605_a(position.x * frequency, position.z * frequency))) * 2.0 - 1.0) * amplitude;

	        // Make the wavelength twice as small
	        frequency *= 2.0;

	        // Add to our maximum possible amplitude
	        maxAmplitude += amplitude;

	        // Reduce amplitude according to persistence for the next octave
	        amplitude *= persistence;
	    }

	    // Scale the result by the maximum amplitude
	    return total / maxAmplitude;
	}
	
	public float cubedNoise(Vec3f position, int octaves, float frequency, float persistence) {
		float total = 0.0f;
	    float maxAmplitude = 0.0f;
	    float amplitude = 1.0f;
	    
	    for (int i = 0; i < octaves; i++) {

	        // Get the noise sample
	    	float tmp = (float) (simplex.func_151605_a(position.x * frequency, position.z * frequency) * amplitude);
	        total += tmp * tmp * tmp * amplitude;

	        // Make the wavelength twice as small
	        frequency *= 2.0;

	        // Add to our maximum possible amplitude
	        maxAmplitude += amplitude;

	        // Reduce amplitude according to persistence for the next octave
	        amplitude *= persistence;
	    }

	    // Scale the result by the maximum amplitude
	    return total / maxAmplitude;
	}
	
	public float thresholdNoise(Vec3f position, float frequency) {
		float noise = this.cubedNoise(position, 1, frequency, 1) * 2f;
		
		if (noise < 0) noise = 0;
		if (noise > 1) noise = 1;
		
		return noise;
	}
}
