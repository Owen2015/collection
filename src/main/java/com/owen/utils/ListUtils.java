package com.owen.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtils {

	static public <T extends Comparable<? super T>> int getMaxIndex(List<T> list){
		T max;
		int index=0;
		max=list.get(0);
		for(int i=0;i<list.size();i++){
			if(list.get(i).compareTo(max)>0){
				max=list.get(i);
				index=i;
			}
		}
		return index;
	}
	
	static public <T extends Comparable<? super T>> int getMinIndex(List<T> list){
		T min;
		int index=0;
		min=list.get(0);
		for(int i=0;i<list.size();i++){
			if(list.get(i).compareTo(min)<0){
				min=list.get(i);
				index=i;
			}
		}
		return index;
	}
	
	
	public static <T> ArrayList<T> Array2List(T[] array){
		ArrayList<T> result=new ArrayList<T>();
		for(int i=0;i<array.length;i++)
			result.add(array[i]);
		return result;
	}
	
	public static <T> Set<T> list2set(List<T> list){
		Set<T> set=new HashSet<T>();
		for(T t:list){
			set.add(t);
		}
		return set;
	}
	
	
	public static <T> List<T> getElements(List<T> list,int num){
		List<T> result=new ArrayList<T>();
		if(num>list.size()){
			System.out.println("num should not be large than the size of the list");
			return null;
		}
		for(int i=0;i<num;i++){
			result.add(list.get(i));
		}
		return result;
	}
	public static void main(String[] args){
		List<Double> testd=new ArrayList<Double>();
		List<Integer> testi=new ArrayList<Integer>();
		testd.add(5.1);
		testd.add(1.1);
		testd.add(2.1);
		testd.add(3.1);
		testi.add(1);
		testi.add(2);
		testi.add(3);
		testi.add(0);
		System.out.println(ListUtils.getMaxIndex(testd));
		System.out.println(ListUtils.getMinIndex(testi));
	}
}
