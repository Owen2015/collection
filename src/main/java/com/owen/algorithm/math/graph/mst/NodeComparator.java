package com.owen.algorithm.math.graph.mst;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node<?>>{

	public int compare(Node<?> o1, Node<?> o2) {
		// TODO Auto-generated method stub
        double numbera = o1.getKey();  
        double numberb = o2.getKey(); 
        int result;
        if(numberb > numbera)  
        {  
            result=1;  
        }  
        else if(numberb<numbera)  
        {  
            result=-1;  
        }  
        else  
        {  
            result=0;  
        }  
        return -result;
	}

}
