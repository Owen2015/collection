package com.owen.algorithm.application.planner;

import java.util.ArrayList;
import java.util.List;

import com.owen.algorithm.application.model.Locatable;
import com.owen.algorithm.application.util.DistanceUtils;
import com.owen.algorithm.application.util.ListUtils;
import com.owen.algorithm.math.graph.GeneticTSP;



public class TspLocationPlanner implements Planned{

	public List<?> plan(List<? extends Locatable> locations, Locatable start, int days){
		return tspPlan(locations,start,days);
	}
	
	@SuppressWarnings("unchecked")
	private List<Locatable> tspPlan(List<? extends Locatable> locations, Locatable start, int days){
		int[] result=tsp((List<Locatable>) locations);
		int startIndex=ListUtils.getIndex ((List<Locatable>)locations,start);
		int resultIndex=getIndex(result,startIndex);
		if(resultIndex==-1)
		System.out.println("start spot did not be found in spot lists");
		List<Locatable> plannedLocations=new ArrayList<Locatable>();
		getTspLocations((List<Locatable>)locations,plannedLocations,result,resultIndex);
		return plannedLocations;
	}
	
	public int[] tsp(List<Locatable> locations){
		GeneticTSP tsp=new GeneticTSP();
		tsp.setMaxGeneration(1000);
		tsp.setAutoNextGeneration(true);
		return tsp.tsp(getAdjMatrix(locations));
	}
	
	private double[][]  getAdjMatrix (List<Locatable> locations){
		int size=locations.size();
		double[][] matrix=new double[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				matrix[i][j]=DistanceUtils.get_distance(locations.get(i).getLatitude(), locations.get(i).getLongitude(), locations.get(j).getLatitude(), locations.get(j).getLongitude());
			}
		}
		return matrix;
	}

	private void getTspLocations(List<Locatable> in, List<Locatable> out,int[] result,int index){
		for(int i=index;i<result.length;i++){
			out.add(in.get(result[i]));
		}
		for(int i=0;i<index;i++){
			out.add(in.get(result[i]));
		}
	}

	private  int getIndex(int[] array,int ele ){
		for(int i=0;i<array.length;i++){
			if(array[i]==ele){
				return i;
			}
		}
		return -1;
	}
}
