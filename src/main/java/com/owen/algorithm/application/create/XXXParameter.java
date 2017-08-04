package com.owen.algorithm.application.create;

import java.util.List;

public class XXXParameter {

	public String getOfficeId() {
		return OfficeId;
	}

	public void setOfficeId(String officeId) {
		OfficeId = officeId;
	}

	public List<Routing> getRoutings() {
		return Routings;
	}

	public void setRoutings(List<Routing> routings) {
		Routings = routings;
	}

	private String OfficeId;
	private List<Routing> Routings;
	
/*	public class Routing {
		private String Departure;
		private String Arrival;
		private Date date;
		public String getDeparture() {
			return Departure;
		}
		public void setDeparture(String departure) {
			Departure = departure;
		}
		public String getArrival() {
			return Arrival;
		}
		public void setArrival(String arrival) {
			Arrival = arrival;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		
	}*/
	
}


