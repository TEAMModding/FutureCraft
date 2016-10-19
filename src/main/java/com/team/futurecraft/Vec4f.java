package com.team.futurecraft;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 * This class represents a (x,y,z,w)-Vector. GLSL equivalent to vec4.
 *
 * @author Heiko Brumme
 */
public class Vec4f {

    public float x;
    public float y;
    public float z;
    public float w;

    /**
     * Creates a default 4-tuple vector with all values set to 0.
     */
    public Vec4f() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.w = 0f;
    }

    /**
     * Creates a 4-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     * @param w w value
     */
    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vec4f(Vec3f vec, float w) {
    	this.x = vec.x;
    	this.y = vec.y;
    	this.z = vec.z;
    	this.w = w;
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Calculates the length of the vector.
     *
     * @return Length of this vector
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes the vector.
     *
     * @return Normalized vector
     */
    public Vec4f normalize() {
        float length = length();
        return divide(length);
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    public Vec4f add(Vec4f other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        float z = this.z + other.z;
        float w = this.w + other.w;
        return new Vec4f(x, y, z, w);
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    public Vec4f negate() {
        return scale(-1f);
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    public Vec4f subtract(Vec4f other) {
        return this.add(other.negate());
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    public Vec4f scale(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        float w = this.w * scalar;
        return new Vec4f(x, y, z, w);
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    public Vec4f divide(float scalar) {
        return scale(1f / scalar);
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    public float dot(Vec4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    public Vec4f lerp(Vec4f other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put(x).put(y).put(z).put(w);
        buffer.flip();
        return buffer;
    }
}
