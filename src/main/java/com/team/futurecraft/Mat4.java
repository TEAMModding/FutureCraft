package com.team.futurecraft;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import net.minecraft.util.Vec3;
/*
 * The MIT License (MIT)
 *
 * Copyright © 2015, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Mat4 {

    public double m00, m01, m02, m03;
    public double m10, m11, m12, m13;
    public double m20, m21, m22, m23;
    public double m30, m31, m32, m33;

    /**
     * Creates a 4x4 identity matrix.
     */
    public Mat4() {
        setIdentity();
    }

    /**
     * Creates a 4x4 matrix with specified columns.
     *
     * @param col1 Vector with values of the first column
     * @param col2 Vector with values of the second column
     * @param col3 Vector with values of the third column
     * @param col4 Vector with values of the fourth column
     */
    public Mat4(Vec4 col1, Vec4 col2, Vec4 col3, Vec4 col4) {
        m00 = col1.xCoord;
        m10 = col1.yCoord;
        m20 = col1.zCoord;
        m30 = col1.wCoord;

        m01 = col2.xCoord;
        m11 = col2.yCoord;
        m21 = col2.zCoord;
        m31 = col2.wCoord;

        m02 = col3.xCoord;
        m12 = col3.yCoord;
        m22 = col3.zCoord;
        m32 = col3.wCoord;

        m03 = col4.xCoord;
        m13 = col4.yCoord;
        m23 = col4.zCoord;
        m33 = col4.wCoord;
    }

    /**
     * Sets this matrix to the identity matrix.
     */
    public final void setIdentity() {
        m00 = 1f;
        m11 = 1f;
        m22 = 1f;
        m33 = 1f;

        m01 = 0f;
        m02 = 0f;
        m03 = 0f;
        m10 = 0f;
        m12 = 0f;
        m13 = 0f;
        m20 = 0f;
        m21 = 0f;
        m23 = 0f;
        m30 = 0f;
        m31 = 0f;
        m32 = 0f;
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Sum of this + other
     */
    public Mat4 add(Mat4 other) {
        Mat4 result = new Mat4();

        result.m00 = this.m00 + other.m00;
        result.m10 = this.m10 + other.m10;
        result.m20 = this.m20 + other.m20;
        result.m30 = this.m30 + other.m30;

        result.m01 = this.m01 + other.m01;
        result.m11 = this.m11 + other.m11;
        result.m21 = this.m21 + other.m21;
        result.m31 = this.m31 + other.m31;

        result.m02 = this.m02 + other.m02;
        result.m12 = this.m12 + other.m12;
        result.m22 = this.m22 + other.m22;
        result.m32 = this.m32 + other.m32;

        result.m03 = this.m03 + other.m03;
        result.m13 = this.m13 + other.m13;
        result.m23 = this.m23 + other.m23;
        result.m33 = this.m33 + other.m33;

        return result;
    }

    /**
     * Negates this matrix.
     *
     * @return Negated matrix
     */
    public Mat4 negate() {
        return multiply(-1f);
    }

    /**
     * Subtracts this matrix from another matrix.
     *
     * @param other The other matrix
     * @return Difference of this - other
     */
    public Mat4 subtract(Mat4 other) {
        return this.add(other.negate());
    }

    /**
     * Multiplies this matrix with a scalar.
     *
     * @param scalar The scalar
     * @return Scalar product of this * scalar
     */
    public Mat4 multiply(float scalar) {
        Mat4 result = new Mat4();

        result.m00 = this.m00 * scalar;
        result.m10 = this.m10 * scalar;
        result.m20 = this.m20 * scalar;
        result.m30 = this.m30 * scalar;

        result.m01 = this.m01 * scalar;
        result.m11 = this.m11 * scalar;
        result.m21 = this.m21 * scalar;
        result.m31 = this.m31 * scalar;

        result.m02 = this.m02 * scalar;
        result.m12 = this.m12 * scalar;
        result.m22 = this.m22 * scalar;
        result.m32 = this.m32 * scalar;

        result.m03 = this.m03 * scalar;
        result.m13 = this.m13 * scalar;
        result.m23 = this.m23 * scalar;
        result.m33 = this.m33 * scalar;

        return result;
    }

    /**
     * Multiplies this matrix to a vector.
     *
     * @param vector The vector
     * @return Vector product of this * other
     */
    public Vec4 multiply(Vec4 vector) {
        double x = this.m00 * vector.xCoord + this.m01 * vector.yCoord + this.m02 * vector.zCoord + this.m03 * vector.wCoord;
        double y = this.m10 * vector.xCoord + this.m11 * vector.yCoord + this.m12 * vector.zCoord + this.m13 * vector.wCoord;
        double z = this.m20 * vector.xCoord + this.m21 * vector.yCoord + this.m22 * vector.zCoord + this.m23 * vector.wCoord;
        double w = this.m30 * vector.xCoord + this.m31 * vector.yCoord + this.m32 * vector.zCoord + this.m33 * vector.wCoord;
        return new Vec4(x, y, z, w);
    }

    /**
     * Multiplies this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Matrix product of this * other
     */
    public Mat4 multiply(Mat4 other) {
        Mat4 result = new Mat4();

        result.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30;
        result.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30;
        result.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30;
        result.m30 = this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30;

        result.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31;
        result.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31;
        result.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31;
        result.m31 = this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31;

        result.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32;
        result.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32;
        result.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32;
        result.m32 = this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32;

        result.m03 = this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33;
        result.m13 = this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33;
        result.m23 = this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33;
        result.m33 = this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33;

        return result;
    }

    /**
     * Transposes this matrix.
     *
     * @return Transposed matrix
     */
    public Mat4 transpose() {
        Mat4 result = new Mat4();

        result.m00 = this.m00;
        result.m10 = this.m01;
        result.m20 = this.m02;
        result.m30 = this.m03;

        result.m01 = this.m10;
        result.m11 = this.m11;
        result.m21 = this.m12;
        result.m31 = this.m13;

        result.m02 = this.m20;
        result.m12 = this.m21;
        result.m22 = this.m22;
        result.m32 = this.m23;

        result.m03 = this.m30;
        result.m13 = this.m31;
        result.m23 = this.m32;
        result.m33 = this.m33;

        return result;
    }

    /**
     * Returns the Buffer representation of this vector.
     *
     * @return Vector as FloatBuffer
     */
    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        buffer.put((float) m00).put((float) m10).put((float) m20).put((float) m30);
        buffer.put((float) m01).put((float) m11).put((float) m21).put((float) m31);
        buffer.put((float) m02).put((float) m12).put((float) m22).put((float) m32);
        buffer.put((float) m03).put((float) m13).put((float) m23).put((float) m33);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates a orthographic projection matrix. Similar to
     * <code>glOrtho(left, right, bottom, top, near, far)</code>.
     *
     * @param left Coordinate for the left vertical clipping pane
     * @param right Coordinate for the right vertical clipping pane
     * @param bottom Coordinate for the bottom horizontal clipping pane
     * @param top Coordinate for the bottom horizontal clipping pane
     * @param near Coordinate for the near depth clipping pane
     * @param far Coordinate for the far depth clipping pane
     * @return Orthographic matrix
     */
    public static Mat4 orthographic(double left, double right, double bottom, double top, double near, double far) {
        Mat4 ortho = new Mat4();

        double tx = -(right + left) / (right - left);
        double ty = -(top + bottom) / (top - bottom);
        double tz = -(far + near) / (far - near);

        ortho.m00 = 2f / (right - left);
        ortho.m11 = 2f / (top - bottom);
        ortho.m22 = -2f / (far - near);
        ortho.m03 = tx;
        ortho.m13 = ty;
        ortho.m23 = tz;

        return ortho;
    }

    /**
     * Creates a perspective projection matrix. Similar to
     * <code>glFrustum(left, right, bottom, top, near, far)</code>.
     *
     * @param left Coordinate for the left vertical clipping pane
     * @param right Coordinate for the right vertical clipping pane
     * @param bottom Coordinate for the bottom horizontal clipping pane
     * @param top Coordinate for the bottom horizontal clipping pane
     * @param near Coordinate for the near depth clipping pane, must be positive
     * @param far Coordinate for the far depth clipping pane, must be positive
     * @return Perspective matrix
     */
    public static Mat4 frustum(double left, double right, double bottom, double top, double near, double far) {
        Mat4 frustum = new Mat4();

        double a = (right + left) / (right - left);
        double b = (top + bottom) / (top - bottom);
        double c = -(far + near) / (far - near);
        double d = -(2f * far * near) / (far - near);

        frustum.m00 = (2f * near) / (right - left);
        frustum.m11 = (2f * near) / (top - bottom);
        frustum.m02 = a;
        frustum.m12 = b;
        frustum.m22 = c;
        frustum.m32 = -1f;
        frustum.m23 = d;
        frustum.m33 = 0f;

        return frustum;
    }

    /**
     * Creates a perspective projection matrix. Similar to
     * <code>gluPerspective(fovy, aspec, zNear, zFar)</code>.
     *
     * @param fovy Field of view angle in degrees
     * @param aspect The aspect ratio is the ratio of width to height
     * @param near Distance from the viewer to the near clipping plane, must be
     * positive
     * @param far Distance from the viewer to the far clipping plane, must be
     * positive
     * @return Perspective matrix
     */
    public static Mat4 perspective(double fovy, double aspect, double near, double far) {
        Mat4 perspective = new Mat4();

        double f = 1 / Math.tan(Math.toRadians(fovy) / 2f);

        perspective.m00 = f / aspect;
        perspective.m11 = f;
        perspective.m22 = (far + near) / (near - far);
        perspective.m32 = -1f;
        perspective.m23 = (2f * far * near) / (near - far);
        perspective.m33 = 0f;

        return perspective;
    }

    /**
     * Creates a translation matrix. Similar to
     * <code>glTranslate(x, y, z)</code>.
     *
     * @param x x coordinate of translation vector
     * @param y y coordinate of translation vector
     * @param z z coordinate of translation vector
     * @return Translation matrix
     */
    public static Mat4 translate(double x, double y, double z) {
        Mat4 translation = new Mat4();

        translation.m03 = x;
        translation.m13 = y;
        translation.m23 = z;

        return translation;
    }
    
    /**
     * Creates a rotation matrix. Similar to
     * <code>glRotate(angle, x, y, z)</code>.
     *
     * @param angle Angle of rotation in degrees
     * @param x x coordinate of the rotation vector
     * @param y y coordinate of the rotation vector
     * @param z z coordinate of the rotation vector
     * @return Rotation matrix
     */
    public static Mat4 rotate(double angle, double x, double y, double z) {
        Mat4 rotation = new Mat4();

        double c = Math.cos(Math.toRadians(angle));
        double s = Math.sin(Math.toRadians(angle));
        Vec3 vec = new Vec3(x, y, z);
        if (vec.lengthVector() != 1f) {
            vec = vec.normalize();
            x = vec.xCoord;
            y = vec.yCoord;
            z = vec.zCoord;
        }

        rotation.m00 = x * x * (1f - c) + c;
        rotation.m10 = y * x * (1f - c) + z * s;
        rotation.m20 = x * z * (1f - c) - y * s;
        rotation.m01 = x * y * (1f - c) - z * s;
        rotation.m11 = y * y * (1f - c) + c;
        rotation.m21 = y * z * (1f - c) + x * s;
        rotation.m02 = x * z * (1f - c) + y * s;
        rotation.m12 = y * z * (1f - c) - x * s;
        rotation.m22 = z * z * (1f - c) + c;

        return rotation;
    }

    /**
     * Creates a scaling matrix. Similar to <code>glScale(x, y, z)</code>.
     *
     * @param x Scale factor along the x coordinate
     * @param y Scale factor along the y coordinate
     * @param z Scale factor along the z coordinate
     * @return Scaling matrix
     */
    public static Mat4 scale(double x, double y, double z) {
        Mat4 scaling = new Mat4();

        scaling.m00 = x;
        scaling.m11 = y;
        scaling.m22 = z;

        return scaling;
    }
}
