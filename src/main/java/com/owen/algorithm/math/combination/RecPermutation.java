package com.owen.algorithm.math.combination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecPermutation {

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
	public static void permute(List<?> soFar,List<?> rest){
		if(rest.isEmpty()){
			
		}else{
			for(int i=0;i<rest.size();i++){
				//soFar.add((Object)rest.get(i));
				List<?> next =new ArrayList<Object>();
			}
		}
	}
	
	public static void subset(String soFar,String rest){
		if(rest.isEmpty()){
			return;
		}else{
			
		}
	}

}
