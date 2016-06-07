package com.team.futurecraft;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

/**
 * This class represents a (x,y,z)-Vector. GLSL equivalent to vec3.
 *
 * @author Heiko Brumme
 */
public class Vec3f {

    public float x;
    public float y;
    public float z;

    /**
     * Creates a default 3-tuple vector with all values set to 0.
     */
    public Vec3f() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    /**
     * Creates a 3-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     */
    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec3f(Vec4f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    
    public Vec3f(Vec3 vec) {
    	this.x = (float)vec.xCoord;
    	this.y = (float)vec.yCoord;
    	this.z = (float)vec.zCoord;
    }
    
    
    public Vec3f(double x, double y, double z) {
    	this((float)x, (float)y, (float)z);
    }

    public Vec3 toVec3() {
    	return new Vec3(this.x, this.y, this.z);
    }
    
    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
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
    public Vec3f normalize() {
        float length = length();
        return divide(length);
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    public Vec3f add(Vec3f other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        float z = this.z + other.z;
        return new Vec3f(x, y, z);
    }
    
    public Vec3f multiply(Vec3f other) {
        float x = this.x * other.x;
        float y = this.y * other.y;
        float z = this.z * other.z;
        return new Vec3f(x, y, z);
    }
    
    public Vec3f multiply(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        return new Vec3f(x, y, z);
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    public Vec3f negate() {
        return scale(-1f);
    }
    
    public double distanceTo(Vec3f vec)
    {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        return (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
    }
    
    public Vec3f rotatePitch(float pitch)
    {
        float f1 = MathHelper.cos(pitch);
        float f2 = MathHelper.sin(pitch);
        float d0 = this.x;
        float d1 = this.y * f1 + this.z * f2;
        float d2 = this.z * f1 - this.y * f2;
        return new Vec3f(d0, d1, d2);
    }

    public Vec3f rotateYaw(float yaw)
    {
        float f1 = MathHelper.cos(yaw);
        float f2 = MathHelper.sin(yaw);
        float d0 = this.x * f1 + this.z * f2;
        float d1 = this.y;
        float d2 = this.z * f1 - this.x * f2;
        return new Vec3f(d0, d1, d2);
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    public Vec3f subtract(Vec3f other) {
        return this.add(other.negate());
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    public Vec3f scale(float scalar) {
        float x = this.x * scalar;
        float y = this.y * scalar;
        float z = this.z * scalar;
        return new Vec3f(x, y, z);
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    public Vec3f divide(float scalar) {
        return scale(1f / scalar);
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    public float dot(Vec3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Cross product of this x other
     */
    public Vec3f cross(Vec3f other) {
        float x = this.y * other.z - this.z * other.y;
        float y = this.z * other.x - this.x * other.z;
        float z = this.x * other.y - this.y * other.x;
        return new Vec3f(x, y, z);
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    public Vec3f lerp(Vec3f other, float alpha) {
        return this.scale(1f - alpha).add(other.scale(alpha));
    }
    
    public String toString() {
    	return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        buffer.put(x).put(y).put(z);
        buffer.flip();
        return buffer;
    }
}