package com.owen.algorithm.math.combination;

public class RecPermutation {

	public static void permute(String soFar,String rest){
		if(rest==""){
			return;
		}else{
			String next=null;
			for(int i=0;i<rest.length();i++){
				soFar=soFar+rest.charAt(i);
				
				System.out.println(soFar);
				String remain=rest.substring(0, i)+rest.substring(i+1, rest.length());
				permute(next,remain);
			}

		}
	}

}
