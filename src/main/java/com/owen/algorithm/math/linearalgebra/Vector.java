package com.owen.algorithm.math.linearalgebra;

public class Vector {
	public static double[] add(double[] v1,double[] v2){
		double[] result=new double[v1.length];
		for(int i=0;i<v1.length;i++)
			result[i]=v1[i]+v2[i];
		return result;
	}
	public static double[] subtract(double[] v1,double[] v2){
		double[] result=new double[v1.length];
		for(int i=0;i<v1.length;i++)
			result[i]=v1[i]-v2[i];
		return result;
	}
	public static double[] scalarProduct(double[] v1, double k){
		double[] result=new double[v1.length];
		for (int i=0;i<v1.length;i++){
			result[i]=k*v1[i];
		}
		return result;
	}	
	public static double dotProduct(double[] v1, double[] v2){
		double result=0;
		for (int i=0;i<v1.length;i++){
			result+=v1[i]*v2[i];
		}
		return result;
	}
	
	public static double crossProduct2D(double[] v1,double[] v2){
		return crossProduct2D(v1[0],v1[1],v2[0],v2[1]);
	}
	public static double crossProduct2D(double v1x,double v1y,double v2x,double v2y){
		return (v1x*v2y-v1y*v2x);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] v1={0,1};
		double[] v2={1,0};
		double k=5;
		System.out.println("dot product is "+dotProduct(v1, v2));
		for (int i=0;i<v1.length;i++)
		{
			System.out.println("add is "+add(v1, v2)[i]+" subtract is "+subtract(v1, v2)[i]+" scalarProduct is "+scalarProduct(v1, k)[i]);			
		}
	}
}

