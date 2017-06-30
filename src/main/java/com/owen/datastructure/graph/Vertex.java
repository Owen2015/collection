package com.owen.datastructure.graph;

public  class  Vertex<T> {

	private T data;

	public Vertex(T t){
		data=t;

	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	
}
