package com.owen.algorithm.application.util;

import org.apache.commons.math3.stat.StatUtils;

public class StatisticsUtil {

	public static double[] normalize(double[] input){
		int size=input.length;
		double[] output=new double[size];
		double max=StatUtils.max(input);
		double min=StatUtils.min(input);
		if(min!=max){
			for(int i=0;i<size;i++){
				try{
					output[i]=(input[i]-min)/(max-min);
				}
				catch(Exception e){
					System.out.println(e);
				}
			}			
		}
		else{
			for(int i=0;i<size;i++)
				output[i]=min;
		}
		return output;
	}
	
	public static double[][] normalize(double[][] input){
		int size=input.length;
		int width;
		if(size==0){
			width=0;
			// return null;
		}else {
			width=input[0].length;			
		}
		double[][] output=new double[size][width];
		double[] vector=new double[size];
		
		for(int i=0;i<width;i++){
			for(int j=0;j<size;j++){
				vector[j]=input[j][i];
			}
			vector=normalize(vector);
			for(int j=0;j<size;j++){
				output[j][i]=vector[j];
			}
		}
		return output;
	}
	
	
}
