package com.owen.algorithm.math.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.owen.datastructure.graph.Vertex;
import com.owen.datastructure.graph.WeightedGraph;



public class MST {

	private WeightedGraph graph;
	private Map<Vertex<?>,Node<?>> vertex2node;
	private List<Node<?>> list;
/*	public MST(WeightedGraph graph){
		this.graph=graph;
		init();
	}*/
	public MST(WeightedGraph graph,Vertex<?> vertex){
		this.graph=graph;
		init(vertex);
	}
	public void init(Vertex<?> vertex){

		list=new ArrayList<Node<?>>();
		vertex2node=new HashMap<Vertex<?>,Node<?>>();

		for(Vertex<?> v:graph.getVertexes()){
			if(!v.equals(vertex)){
				Node<Vertex<?>> node=new Node<Vertex<?>>(v);
				node.setKey(Double.MAX_VALUE);
				node.setParent(null);
				list.add(node);
				vertex2node.put(v, node);
			}
			else{
				Node<Vertex<?>> node=new Node<Vertex<?>>(v);
				node.setKey(0D);
				list.add(node);
				vertex2node.put(v, node);
			}
		}
	}
	
	public void init2(){
		
	}
	
	public Set<Node<?>> mst(){
		return prim();
	}
	
	private Set<Node<?>> prim(){
		double weight;
		Node<?> u;
		Set<Node<?>> nodes=new HashSet<Node<?>>();
		Set<Vertex<?>> adjacents=new HashSet<Vertex<?>>();
		while(!list.isEmpty()){
			Collections.sort(list);
			u=list.get(0);
			list.remove(0);
			adjacents=graph.getAdjacents((Vertex<?>)u.getData());
			for(Vertex<?> v:adjacents){
				Node<?> node=vertex2node.get(v);
				weight=(double)graph.getWeight(v, (Vertex<?>)u.getData());
				if(list.contains(node)&&weight<node.getKey()){
					node.setKey(weight);
					node.setParent(u);
				}
			}
			nodes.add(u);
		}
		return nodes;
	}
	public Node<?> generateTree(Set<Node<?>> nodes){
		Node<?> root=null;
		for(Node<?> node:nodes){
			if(node.getParent()!=null){
				node.getParent().getChildren().add(node);
			}else{
				root=node;
			}
		}
		return root;
	}
	
	public static List<Node<?>> getPrunednodes(List<Node<?>> list, int num){
		List<Node<?>> prunednodes=new ArrayList<Node<?>>();
		if(num>=list.size()-1){
			System.out.println("pruned nodes should be less than the origin tree");
			return null;
		}
		for(int i=list.size()-1;i>list.size()-1-num;i--){
			prunednodes.add(list.get(i));
		}
		return prunednodes;
	}

}
