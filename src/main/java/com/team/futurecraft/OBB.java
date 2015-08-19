package com.team.futurecraft;

import com.team.futurecraft.RayTrace.EnumAxis;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class OBB {
	private  Vec3 min;
	private Vec3 max;
	private Mat4 matrix;
	
	public OBB(Vec3 min, Vec3 max, Mat4 matrix) {
		this.min = min;
		this.max = max;
		this.matrix = matrix;
	}
	
	public static OBB fromAABB(AxisAlignedBB aabb) {
		Vec3 min = new Vec3(aabb.minX, aabb.minY, aabb.minZ);
		Vec3 max = new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ);
		return new OBB(min, max, new Mat4());
	}
	
	public RayTrace rayTrace(Vec3 ray_origin, Vec3 ray_direction) {
		double tMin = 0.0f;
		double tMax = 100000.0f;
		EnumAxis axis = null;
		
		Vec3 position = new Vec3(matrix.m03, matrix.m13, matrix.m23);
		Vec3 xAxis = new Vec3(matrix.m00, matrix.m10, matrix.m20);
		
		Vec3 delta = position.subtract(ray_origin);
		
		double e = xAxis.dotProduct(delta);
		double f = ray_direction.dotProduct(xAxis);
		
		//--x--
		double t1 = (e + min.xCoord) / f;
		double t2 = (e + max.xCoord) / f;
		
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
		Vec3 yAxis = new Vec3(matrix.m01, matrix.m11, matrix.m21);
		
		delta = position.subtract(ray_origin);
		
		e = yAxis.dotProduct(delta);
		f = ray_direction.dotProduct(yAxis);
		
		t1 = (e + min.yCoord) / f;
		t2 = (e + max.yCoord) / f;
		
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
		Vec3 zAxis = new Vec3(matrix.m02, matrix.m12, matrix.m22);
		
		delta = position.subtract(ray_origin);
		
		e = zAxis.dotProduct(delta);
		f = ray_direction.dotProduct(zAxis);
				
		t1 = (e + min.zCoord) / f;
		t2 = (e + max.zCoord) / f;
				
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
		
		Vec3 position = new Vec3(matrix.m03, matrix.m13, matrix.m23);
		Vec3 xAxis = new Vec3(matrix.m00, matrix.m10, matrix.m20);
		
		Vec3 otherposition = new Vec3(other.matrix.m03, other.matrix.m13, other.matrix.m23);
		Vec3 otherxAxis = new Vec3(other.matrix.m00, other.matrix.m10, other.matrix.m20);
		
		Vec3 delta = position.subtract(otherposition);
		
		double e = xAxis.dotProduct(delta);
		double f = otherxAxis.dotProduct(xAxis);
		
		//--x--
		double t1 = (e + min.xCoord) / f;
		double t2 = (e + max.xCoord) / f;
		
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
		Vec3 yAxis = new Vec3(matrix.m01, matrix.m11, matrix.m21);
		Vec3 otheryAxis = new Vec3(other.matrix.m01, other.matrix.m11, other.matrix.m21);
		
		delta = position.subtract(otherposition);
		
		e = yAxis.dotProduct(delta);
		f = otheryAxis.dotProduct(yAxis);
		
		t1 = (e + min.yCoord) / f;
		t2 = (e + max.yCoord) / f;
		
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
		Vec3 zAxis = new Vec3(matrix.m02, matrix.m12, matrix.m22);
		Vec3 otherzAxis = new Vec3(other.matrix.m02, other.matrix.m12, other.matrix.m22);
		
		delta = position.subtract(otherposition);
		
		e = zAxis.dotProduct(delta);
		f = otherzAxis.dotProduct(zAxis);
				
		t1 = (e + min.zCoord) / f;
		t2 = (e + max.zCoord) / f;
				
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
