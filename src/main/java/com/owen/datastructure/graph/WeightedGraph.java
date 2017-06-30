package com.owen.datastructure.graph;

import java.util.HashSet;
import java.util.Set;

public class WeightedGraph {  

	private Set<Vertex<?>> vertexes;
	private Set<Edge> edges;
	
	public WeightedGraph(Set<Vertex<?>> vertexes,Set<Edge> edges){
		this.vertexes=vertexes;
		this.edges=edges;
	}
	
	public WeightedGraph(){
		this.vertexes=new HashSet<Vertex<?>>();
		this.edges=new HashSet<Edge>();
	}
	
	public Set<Vertex<?>> getVertexes() {
		return vertexes;
	}

	public void setVertexes(Set<Vertex<?>> vertexes) {
		this.vertexes = vertexes;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}
	
	public void addVertex(Vertex<?> v){
		this.vertexes.add(v);
	}
	public void addEdge(Edge e){
		this.edges.add(e);
	}
	public boolean containVertex(Vertex<?> v){
		return this.vertexes.contains(v);
	}
	public boolean containEdge(Edge e){
		return this.edges.contains(e);
	}
	
	// optimal for efficiency later
	public Set<Vertex<?>> getAdjacents(Vertex<?> v){
		Set<Vertex<?>> adjacents=new HashSet<Vertex<?>>();
		for(Edge edge:this.getEdges()){
			if(edge.contain(v)){
				adjacents.add(edge.getOther(v));
			}
		}
		return adjacents;
	}
	
	public double getWeight(Vertex<?> v1,Vertex<?> v2){
		for(Edge edge:this.getEdges()){
			if((edge.getSource().equals(v1)&&edge.getTarget().equals(v2))||(edge.getSource().equals(v2)&&edge.getTarget().equals(v1))){
				return edge.getWeight();
			}
		}
		return (Double) null;
	}
	
}  