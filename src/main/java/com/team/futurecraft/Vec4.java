package com.team.futurecraft;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import net.minecraft.util.Vec3;

/**
 * This class represents a (x,y,z,w)-Vector. GLSL equivalent to vec4.
 *
 * @author Heiko Brumme
 */
public class Vec4 {

    public double xCoord;
    public double yCoord;
    public double zCoord;
    public double wCoord;

    /**
     * Creates a default 4-tuple vector with all values set to 0.
     */
    public Vec4() {
        this.xCoord = 0f;
        this.yCoord = 0f;
        this.zCoord = 0f;
        this.wCoord = 0f;
    }

    /**
     * Creates a 4-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     * @param w w value
     */
    public Vec4(double x, double y, double z, double w) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        this.wCoord = w;
    }
    
    public Vec4(Vec3 vec, double w) {
    	this.xCoord = vec.xCoord;
        this.yCoord = vec.yCoord;
        this.zCoord = vec.zCoord;
        this.wCoord = w;
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    public double lengthSquared() {
        return xCoord * xCoord + yCoord * yCoord + zCoord * zCoord + wCoord * wCoord;
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
    public Vec4 normalize() {
        float length = length();
        return divide(length);
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    public Vec4 add(Vec4 other) {
        double x = this.xCoord + other.xCoord;
        double y = this.yCoord + other.yCoord;
        double z = this.zCoord + other.zCoord;
        double w = this.wCoord + other.wCoord;
        return new Vec4(x, y, z, w);
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    public Vec4 negate() {
        return scale(-1f);
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    public Vec4 subtract(Vec4 other) {
        return this.add(other.negate());
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    public Vec4 scale(double scalar) {
        double x = this.xCoord * scalar;
        double y = this.yCoord * scalar;
        double z = this.zCoord * scalar;
        double w = this.wCoord * scalar;
        return new Vec4(x, y, z, w);
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    public Vec4 divide(float scalar) {
        return scale(1f / scalar);
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    public double dot(Vec4 other) {
        return this.xCoord * other.xCoord + this.yCoord * other.yCoord + this.zCoord * other.zCoord + this.wCoord * other.wCoord;
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    public Vec4 lerp(Vec4 other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    public DoubleBuffer getBuffer() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(4);
        buffer.put(xCoord).put(yCoord).put(zCoord).put(wCoord);
        buffer.flip();
        return buffer;
    }
}
