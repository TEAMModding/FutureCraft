package com.team.futurecraft;

import com.team.futurecraft.RayTrace.EnumAxis;

import net.minecraft.util.AxisAlignedBB;

public class OBB {
	private  Vec3f min;
	private Vec3f max;
	private Mat4f matrix;
	
	public OBB(Vec3f min, Vec3f max, Mat4f matrix) {
		this.min = min;
		this.max = max;
		this.matrix = matrix;
	}
	
	public static OBB fromAABB(AxisAlignedBB aabb) {
		Vec3f min = new Vec3f(aabb.minX, aabb.minY, aabb.minZ);
		Vec3f max = new Vec3f(aabb.maxX, aabb.maxY, aabb.maxZ);
		return new OBB(min, max, new Mat4f());
	}
	
	public RayTrace rayTrace(Vec3f ray_origin, Vec3f ray_direction) {
		double tMin = 0.0f;
		double tMax = 100000.0f;
		EnumAxis axis = null;
		
		Vec3f position = new Vec3f(matrix.m03, matrix.m13, matrix.m23);
		Vec3f xAxis = new Vec3f(matrix.m00, matrix.m10, matrix.m20);
		
		Vec3f delta = position.subtract(ray_origin);
		
		double e = xAxis.dot(delta);
		double f = ray_direction.dot(xAxis);
		
		//--x--
		double t1 = (e + min.x) / f;
		double t2 = (e + max.x) / f;
		
		if (t1>t2) { // if wrong order
			double w=t1; t1=t2; t2=w; // swap t1 and t2
		}
		
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if ( t2 < tMax ) {
			tMax = t2;
		}
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if ( t1 > tMin ) {
			axis = EnumAxis.X;
			tMin = t1;
		}
		
		if (tMax < tMin )
		    return null;
		
		//--y--
		Vec3f yAxis = new Vec3f(matrix.m01, matrix.m11, matrix.m21);
		
		delta = position.subtract(ray_origin);
		
		e = yAxis.dot(delta);
		f = ray_direction.dot(yAxis);
		
		t1 = (e + min.y) / f;
		t2 = (e + max.y) / f;
		
		if (t1>t2) { // if wrong order
			double w=t1; t1=t2; t2=w; // swap t1 and t2
		}
		
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if ( t2 < tMax ) {
			tMax = t2;
		}
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if ( t1 > tMin ) {
			axis = EnumAxis.Y;
			tMin = t1;
		}
		
		if (tMax < tMin )
		    return null;
		
		//--z--
		Vec3f zAxis = new Vec3f(matrix.m02, matrix.m12, matrix.m22);
		
		delta = position.subtract(ray_origin);
		
		e = zAxis.dot(delta);
		f = ray_direction.dot(zAxis);
				
		t1 = (e + min.z) / f;
		t2 = (e + max.z) / f;
				
		if (t1>t2) { // if wrong order
			double w=t1; t1=t2; t2=w; // swap t1 and t2
		}
				
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if ( t2 < tMax ) {
			tMax = t2;
		}
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if ( t1 > tMin ) {
			axis = EnumAxis.Z;
			tMin = t1;
		}
				
		if (tMax < tMin )
			return null;
		
		return new RayTrace(axis, tMin);
	}
	
	public boolean intersects(OBB other) {
		return this.intersectsDirect(other) || other.intersectsDirect(this);
	}
	
	private boolean intersectsDirect(OBB other) {
		double tMin = 0.0f;
		double tMax = 100000.0f;
		
		Vec3f position = new Vec3f(matrix.m03, matrix.m13, matrix.m23);
		Vec3f xAxis = new Vec3f(matrix.m00, matrix.m10, matrix.m20);
		
		Vec3f otherposition = new Vec3f(other.matrix.m03, other.matrix.m13, other.matrix.m23);
		Vec3f otherxAxis = new Vec3f(other.matrix.m00, other.matrix.m10, other.matrix.m20);
		
		Vec3f delta = position.subtract(otherposition);
		
		double e = xAxis.dot(delta);
		double f = otherxAxis.dot(xAxis);
		
		//--x--
		double t1 = (e + min.x) / f;
		double t2 = (e + max.x) / f;
		
		if (t1>t2) { // if wrong order
			double w=t1; t1=t2; t2=w; // swap t1 and t2
		}
		
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if ( t2 < tMax ) tMax = t2;
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if ( t1 > tMin ) tMin = t1;
		
		if (tMax < tMin )
		    return false;
		
		//--y--
		Vec3f yAxis = new Vec3f(matrix.m01, matrix.m11, matrix.m21);
		Vec3f otheryAxis = new Vec3f(other.matrix.m01, other.matrix.m11, other.matrix.m21);
		
		delta = position.subtract(otherposition);
		
		e = yAxis.dot(delta);
		f = otheryAxis.dot(yAxis);
		
		t1 = (e + min.y) / f;
		t2 = (e + max.y) / f;
		
		if (t1>t2) { // if wrong order
			double w=t1; t1=t2; t2=w; // swap t1 and t2
		}
		
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if ( t2 < tMax ) tMax = t2;
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if ( t1 > tMin ) tMin = t1;
		
		if (tMax < tMin )
		    return false;
		
		//--z--
		Vec3f zAxis = new Vec3f(matrix.m02, matrix.m12, matrix.m22);
		Vec3f otherzAxis = new Vec3f(other.matrix.m02, other.matrix.m12, other.matrix.m22);
		
		delta = position.subtract(otherposition);
		
		e = zAxis.dot(delta);
		f = otherzAxis.dot(zAxis);
				
		t1 = (e + min.z) / f;
		t2 = (e + max.z) / f;
				
		if (t1>t2) { // if wrong order
			double w=t1; t1=t2; t2=w; // swap t1 and t2
		}
				
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if ( t2 < tMax ) tMax = t2;
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if ( t1 > tMin ) tMin = t1;
				
		if (tMax < tMin )
			return false;
		
		return true;
	}
}
