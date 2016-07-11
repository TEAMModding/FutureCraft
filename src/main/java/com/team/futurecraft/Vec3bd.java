package com.team.futurecraft;

import java.math.BigDecimal;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 * This class represents a (x,y,z,w)-Vector. GLSL equivalent to vec4.
 *
 * @author Heiko Brumme
 */
public class Vec3bd {

    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal z;

    /**
     * Creates a default 4-tuple vector with all values set to 0.
     */
    public Vec3bd() {
        this.x = new BigDecimal(0f);
        this.y = new BigDecimal(0f);
        this.z = new BigDecimal(0f);
    }

    /**
     * Creates a 4-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     * @param w w value
     */
    public Vec3bd(float x, float y, float z) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.z = new BigDecimal(z);
    }

    public Vec3bd(BigDecimal x, BigDecimal y, BigDecimal z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    public BigDecimal lengthSquared() {
        return x.multiply(x).add(y.multiply(y)).add(z.multiply(z));
    }

    /**
     * Calculates the length of the vector.
     *
     * @return Length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared().doubleValue());
    }

    /**
     * Normalizes the vector.
     *
     * @return Normalized vector
     */
    public Vec3bd normalize() {
        double length = length();
        return divide(new BigDecimal(length));
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    public Vec3bd add(Vec3bd other) {
        BigDecimal x = this.x.add(other.x);
        BigDecimal y = this.y.add(other.y);
        BigDecimal z = this.z.add(other.z);
        return new Vec3bd(x, y, z);
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    public Vec3bd negate() {
        return scale(new BigDecimal(-1f));
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    public Vec3bd subtract(Vec3bd other) {
        return this.add(other.negate());
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    public Vec3bd scale(BigDecimal scalar) {
        BigDecimal x = this.x.multiply(scalar);
        BigDecimal y = this.y.multiply(scalar);
        BigDecimal z = this.z.multiply(scalar);
        return new Vec3bd(x, y, z);
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    public Vec3bd divide(BigDecimal scalar) {
        return scale(BigDecimal.ONE.divide(scalar));
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    public BigDecimal dot(Vec3bd other) {
        return this.x.multiply(other.x).add(this.y.multiply(other.y)).add(this.z.multiply(other.z));
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    public Vec3bd lerp(Vec3bd other, BigDecimal alpha) {
        return this.scale(BigDecimal.ONE.subtract(alpha)).add(other.scale(alpha));
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        buffer.put(x.floatValue()).put(y.floatValue()).put(z.floatValue());
        buffer.flip();
        return buffer;
    }
}
