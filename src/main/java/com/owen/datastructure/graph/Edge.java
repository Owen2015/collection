package com.owen.datastructure.graph;

public class Edge {

	private Vertex<?> source;
	private Vertex<?> target;
	private double weight;

	public Edge(Vertex<?> source, Vertex<?> target){
		this.source=source;
		this.target=target;
	}

	public Edge(Vertex<?> source, Vertex<?> target, double weight){
		this.source=source;
		this.target=target;
		this.weight=weight;
	}

	public Vertex<?> getOther(Vertex<?> v){
		if(this.source==v)
			return this.target;
		else if(this.target==v)
			return this.source;
		else{
			System.out.println(" Vertex not in the edge");
			return null;
		}
	}
	
	public boolean contain(Vertex<?> v){
		if(this.getSource().equals(v)||this.getTarget().equals(v)){
			return true;
		}else{
			return false;
		}
	}
	public Vertex<?> getSource() {
		return source;
	}

	public void setSource(Vertex<?> source) {
		this.source = source;
	}

	public Vertex<?> getTarget() {
		return target;
	}

	public void setTarget(Vertex<?> target) {
		this.target = target;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	public String toString(){
		return source.getData().toString()+", "+target.getData().toString()+", "+weight;
	}
	
}
