package com.owen.algorithm.math.graph.mst;

import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Comparable<Node<?>>{

	private T data;
	private double key;
	private Node<?> parent;
	private List<Node<?>> children;
	
	public Node(T t){
		data=t;
		children=new ArrayList<Node<?>>();
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
	}

	public Node<?> getParent() {
		return parent;
	}

	public void setParent(Node<?> parent) {
		this.parent = parent;
	}

	public List<Node<?>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<?>> children) {
		this.children = children;
	}

	@SuppressWarnings("null")
	public int compareTo(Node<?> node){
		try{
			if(this.getKey()<node.getKey()){
				return -1;
			}else if(this.getKey()==node.getKey()){
				return 0;
			}else if(this.getKey()>node.getKey()){
				return 1;
			}else {
				return (Integer) null;
			}			
		}catch(Exception e){
			System.out.println(e);
			return (Integer) null;
		}
	}

}
