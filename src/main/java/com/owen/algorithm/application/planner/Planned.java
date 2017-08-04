package com.owen.algorithm.application.planner;

import java.util.List;

import com.owen.algorithm.application.model.Locatable;


public interface Planned {

	public List<?> plan(List<? extends Locatable> locations,Locatable start,int num);
}
