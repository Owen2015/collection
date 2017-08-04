package com.owen.algorithm.application.create;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinbad.itinerary.model.entity.Airport;

public class XXXFactory extends ParameterFactory{

	public XXXFactory(Date date,Airport depart, Airport arrive){
		this.date=date;
		this.depart=depart;
		this.arrive=arrive;
	}
/*	public Object createParameter() {
		// TODO Auto-generated method stub
		YiQiFlyParameter parameter=new YiQiFlyParameter();
		List<Routing> routings=new ArrayList<Routing>();
		Routing routing=new Routing();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		routing.setArrival(arrive.getCode());
		routing.setDeparture(depart.getCode());
		routing.setDepartureDate(simpleDateFormat.format(date));
		routings.add(routing);
		parameter.setOfficeId(officeId);
		parameter.setRoutings(routings);
		return parameter;
	}*/

	@Override
	public Object createParameter(){
		Map<String,Object> parameter=new HashMap<String,Object>();
		Map<String,Object> routing=new HashMap<String,Object>();
		List<HashMap<String,Object>> routings=new ArrayList<HashMap<String,Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		routing.put("Departure", depart.getCode());
		routing.put("Arrival", arrive.getCode());
		routing.put("DepartureDate", format.format(date));
		routings.add((HashMap<String, Object>) routing);
		parameter.put("OfficeId", "9M001");
		parameter.put("Routings", routings);
		return parameter;
	}
	@Override
	public String createCmd() {
		// TODO Auto-generated method stub
		return CMD;
	}
	
	
	public static final String CMD="yiQiFly_search_plane";
	private String officeId="9M001";
	private Date date;
	private Airport depart;
	private Airport arrive;
	@Override
	public Object createParameter2() {
		// TODO Auto-generated method stub
		return null;
	}

}
