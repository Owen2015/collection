package com.owen.algorithm.application.planner;

import java.util.List;

import com.owen.algorithm.application.model.Locatable;




public class LocationPlanner {

	@SuppressWarnings("unchecked")
	public LocationPlanner(List<? extends Locatable> locations,Locatable start){
		this.locations=(List<Locatable>) locations;
		this.start=start;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> performPlan(int days){
		return planner.plan((List<Locatable>)locations,start,days);
	}

	public void setPlanner(Planned planner){
		this.planner=planner;
	}
	
	protected List<Locatable> locations;
	protected Locatable start;
	private Planned planner;

}
