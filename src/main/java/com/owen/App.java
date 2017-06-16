package com.owen;

import java.util.ArrayList;
import java.util.List;

import com.owen.algorithm.math.combination.Combination;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Combination.permute("","ABC");
        List<Integer> list=new ArrayList<Integer>();
        List<ArrayList<Integer>> result=new ArrayList<ArrayList<Integer>>();
        List<Integer> soFar=new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        //list.add(4);
        Combination.permute(soFar, list, result);
        for(ArrayList<Integer> ele:result){
        	System.out.println(ele);
        }
    }
}
