package com.owen.algorithm.application.planner;

import java.util.List;

import com.owen.algorithm.application.model.Locatable;
import com.owen.algorithm.application.util.DistanceUtils;
import com.owen.algorithm.application.util.ListUtils;




public class GreedyLocationPlanner implements Planned {

	public List<?> plan(List<? extends Locatable> locations, Locatable start, int num) {
		// TODO Auto-generated method stub
		return greedyPlan(locations,start,num);
	}
	
	@SuppressWarnings("unchecked")
	private List<Locatable> greedyPlan(List<? extends Locatable> locations, Locatable start, int num){
		int index=ListUtils.getIndex((List<Locatable>)locations,start);
		if(index!=-1){
			ListUtils.swap(locations,0,index);
		}else{
			System.out.println("the element not in the list");
		}
		int minIndex=0;
		double minDistance=-1D;
		for(int i=0;i<locations.size()-1;i++){
			minDistance=DistanceUtils.get_distance(((Locatable)locations.get(i)).getLatitude(), ((Locatable)locations.get(i)).getLongitude(), ((Locatable)locations.get(i+1)).getLatitude(), ((Locatable)locations.get(i+1)).getLongitude());
			minIndex=i+1;
			for(int j=i+1;j<locations.size();j++){
				if(DistanceUtils.get_distance(((Locatable)locations.get(i)).getLatitude(), ((Locatable)locations.get(i)).getLongitude(), ((Locatable)locations.get(j)).getLatitude(), ((Locatable)locations.get(j)).getLongitude())<
						minDistance){
					minDistance=DistanceUtils.get_distance(((Locatable)locations.get(i)).getLatitude(), ((Locatable)locations.get(i)).getLongitude(),((Locatable)locations.get(j)).getLatitude(), ((Locatable)locations.get(j)).getLongitude());
					minIndex=j;
				}
			}
			ListUtils.swap(locations,i+1,minIndex);
		}
		return (List<Locatable>) locations;
	}

}
