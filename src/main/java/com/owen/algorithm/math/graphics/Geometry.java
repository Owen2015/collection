package com.owen.algorithm.math.graphics;

import com.owen.algorithm.math.linearalgebra.Vector;

public class Geometry {
	
	public static boolean IsPtInRect(double[] pt,double[][] rect){
		return IsPtInRect(pt, rect[0],rect[1],rect[2],rect[3]);
	}
	public static boolean IsPtInRect(double []pt,double[] rect0,double[] rect1,double[] rect2,double[] rect3){
		//(0<AM*AB<AB*AB)AND(0<AM*AD<AD*AD), M is the point,ABCD is the rect 
		double c1=Vector.dotProduct(Vector.subtract(pt, rect0), Vector.subtract(rect1, rect0));
		double c2=Vector.dotProduct(Vector.subtract(rect1, rect0), Vector.subtract(rect1, rect0));
		double c3=Vector.dotProduct(Vector.subtract(pt, rect0), Vector.subtract(rect3, rect0));
		double c4=Vector.dotProduct(Vector.subtract(rect3, rect0), Vector.subtract(rect3, rect0));
		return c1>=0&&c2>=0&&c2>=c1&&c3>=0&&c4>=0&&c4>=c3;
	}
	public static double Direction(double[] pt1,double pt2[],double[] pt3){
		return Vector.crossProduct2D(Vector.subtract(pt3, pt1), Vector.subtract(pt2, pt1));
	}
	public static boolean OnSegment(double[] pt1,double[] pt2,double[] pt3){
		boolean c1=(Math.min(pt1[0], pt2[0])<=pt3[0]&&pt3[0]<=Math.max(pt1[0], pt2[0]));
		boolean c2=(Math.min(pt1[1], pt2[1])<=pt3[1]&&pt3[1]<=Math.max(pt1[1], pt2[1]));
		if(c1&&c2) return true;
		else return false;
	}
	public static boolean IsIntersect(double[] pt1,double[] pt2, double[] pt3,double[] pt4){
		double d1=Direction(pt3, pt4, pt1);
		double d2=Direction(pt3, pt4, pt2);
		double d3=Direction(pt1, pt2, pt3);
		double d4=Direction(pt1, pt2, pt4);
		// straddle
		if(((d1<0&&d2>0)||(d1>0&&d2<0))&&
		   ((d3>0&&d4<0)||(d3<0&&d4>0)))
			return true;
		// colinear
		else if (d1==0&&OnSegment(pt3, pt4, pt1))
			return true;
		else if (d2==0&&OnSegment(pt3, pt4, pt2))
			return true;
		else if (d3==0&&OnSegment(pt1, pt2, pt3))
			return true;
		else if (d3==0&&OnSegment(pt1, pt2, pt3))
			return true;
		else return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
   	double[] pt1={3,4};
   	double[][] rect1={{1,0},{4,3},{3,4},{0,1}};
   	System.out.println("point in rect? "+Geometry.IsPtInRect(pt1,rect1));
   	System.out.println("segment intersect? "+Geometry.IsIntersect(pt1, rect1[3], rect1[0], rect1[1]));
	}

}
