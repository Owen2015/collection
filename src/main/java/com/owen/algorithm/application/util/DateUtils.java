package com.owen.algorithm.application.util;

import java.util.Date;

public class DateUtils {

	public static Date getAfter(Date tempDate, Integer day) {
		Date date = new Date(tempDate.getTime()+day*86400000);
		return date;
	}
	
	public static double roundHour(Double hour){
/*		if(hour*10%10<5){
			return (double) Math.round(hour);
		}else{
			return (double)Math.round(hour)+0.5;
		}*/
		int criteria=0;
		double temp=hour*100%100;
		if(0<=temp&&temp<25){
			criteria=0;
		}
		else if(25<=temp&&temp<50){
			criteria=1;
		}
		else if(50<=temp&&temp<75){
			criteria=2;
		}
		else if(75<=temp&&temp<100){
			criteria=3;
		}
		
		switch(criteria){
		case 0: return (double) Math.round(hour); 
		case 1: return (double)((double) Math.round(hour)+0.5);
		case 2: return (double)((double) Math.round(hour)-0.5);
		case 3: return (double) Math.round(hour); 	
		}
		return (Double) null;
	}
	public static void main(String[] args){
		double a=11.11111111111;
		double b=11.333333333333333;
		double c=11.6666666666666;
		double d=11.8888888888;
		System.out.println(a+" : "+DateUtils.roundHour(a));
		System.out.println(b+" : "+DateUtils.roundHour(b));
		System.out.println(c+" : "+DateUtils.roundHour(c));
		System.out.println(d+" : "+DateUtils.roundHour(d));
	}
}
