package com.owen.algorithm.math.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.owen.datastructure.graph.Edge;
import com.owen.datastructure.graph.Vertex;
import com.owen.datastructure.graph.WeightedGraph;


public class SubGraph {

	public static WeightedGraph subGraph(WeightedGraph graph,Set<Vertex<?>> vertexes){
		Set<Edge> originEdges=graph.getEdges();
		Set<Edge> edges=new HashSet<Edge>();
		Iterator<Edge> edgeIter=originEdges.iterator();
		while(edgeIter.hasNext()){
			Edge edge=edgeIter.next();
			if(vertexes.contains(edge.getSource())&&vertexes.contains(edge.getTarget())){
				edges.add(edge);
			}
		}
		return new WeightedGraph(vertexes,edges);
	}
}
