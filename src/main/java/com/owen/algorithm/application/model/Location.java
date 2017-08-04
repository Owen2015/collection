package com.owen.algorithm.application.model;

public class Location {

	float latitude;
	float longitude;
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public String toString(){
		return "["+latitude+", "+longitude+"]";
	}
}
