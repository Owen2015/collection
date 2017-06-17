package com.owen.algorithm.math.combination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Combination {

	public static void permute(String soFar,String rest){
		if(rest.isEmpty()){
			System.out.println(soFar);
			return;
		}else{
			//String next=null;
			for(int i=0;i<rest.length();i++){
				String next=soFar+rest.charAt(i);
				String remain=rest.substring(0, i)+rest.substring(i+1, rest.length());
				//System.out.println("next: "+next+", rest: "+remain);
				permute(next,remain);
			}
		}
	}
	public static void subset(String soFar,String rest){
		if(rest.isEmpty()){
			System.out.println(soFar);
			return;
		}else{
			//String remain=rest.substring(0, rest.length());
			subset(soFar+rest.charAt(0),rest.substring(1, rest.length()));
			subset(soFar,rest.substring(1, rest.length()));
		}
	}
	public static <T> void subset(List<T> soFar,List<T> rest,List<ArrayList<T>> result){
		if(rest.isEmpty()){
			result.add((ArrayList<T>) soFar);
		}else{
			List<T> remain=new ArrayList<T>();
			List<T> next=new ArrayList<T>();
			next.addAll(soFar);
			next.add(rest.get(0));
			remain.addAll(rest);
			remain.remove(0);
			subset(next,remain,result);
			subset(soFar,remain,result);
		}
	}
	public static <T>  void permute(List<T> soFar,List<T> rest,List<ArrayList<T>> result){
		if(rest.isEmpty()){
			result.add((ArrayList<T>) soFar);
			return;
		}else{
			for(int i=0;i<rest.size();i++){
				List<T> next =new ArrayList<T>();
				List<T> remain=new ArrayList<T>();
				next.addAll(soFar);
				next.add(rest.get(i));
				remain.addAll(rest);
				remain.remove(i);
				permute(next,remain,result);
			}
		}
	}
	public static <T> void subset(List<T> soFar, List<T> rest,List<ArrayList<T>> result,int n){
		subset(soFar,rest,result);
		List<ArrayList<T>> temp=new ArrayList<ArrayList<T>>();
		//full.addAll(result);
		for(int i=0;i<result.size();i++){
			if(result.get(i).size()==n){
				temp.add(result.get(i));
			}
		}
		result.clear();
		result.addAll(temp);
	}


}
