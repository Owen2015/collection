package com.owen.algorithm.application.util;

public class DistanceUtils {
	public static double EARTH_RADIUS = 6378.137;
	public static double PI = 3.1415926535;

	public static double rad(double d) {
		return d * PI / 180.0;
	}

	public static double degree(double rad){
		return rad*180.0D/PI;
	}
	public static double round(double d) {
		return Math.floor(d + 0.5);
	}

	public static double get_distance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = round(s * 10000) / 10000;
		return s;
	}
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = round(s * 10000) / 10000;
		return s;
	}

	public static double getDiffLat(double lat, double lon,double distance){
		double diff=distance/EARTH_RADIUS;
		return degree(diff);
	}
	
	public static double getDiffLon(double lat, double lon,double distance){
		double diff=2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(rad(lat)));
		return degree(diff);
	}
	
	public static void main(String[] args) {
		System.out.println(DistanceUtils.get_distance(11.2, 123.1, 15.2, 124.1));
		System.out.println("Beijing-> Guangzhou: "+DistanceUtils.get_distance(39.926224, 116.403981, 23.392677, 113.308924));
		System.out.println("Cidney-> Guangzhou: "+DistanceUtils.get_distance(-33.882045, 151.167398, 23.392677, 113.308924));
		System.out.println("London-> Guangzhou: "+DistanceUtils.get_distance(51.528607, 0, 23.392677, 113.308924));
		System.out.println("Albordin-> Guangzhou: "+DistanceUtils.get_distance(51.528607, -2.159698, 23.392677, 113.308924));
	}
}
