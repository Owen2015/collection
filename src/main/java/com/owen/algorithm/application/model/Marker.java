package com.owen.algorithm.application.model;


public class Marker {
	private double latitude;
	private double longitude;
	private int rank;
	private String nameCn;
	private String nameEn;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String toString(){
		return "{lat:"+latitude+", lon: "+longitude+", name:'"+nameCn.replaceAll("'|\\]|\\[|\"", " ")+"', rank: "+rank+"}";
	}
}
