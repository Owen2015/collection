package com.owen.algorithm.math.graph.mst;


import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.owen.datastructure.graph.Vertex;
import com.owen.datastructure.graph.WeightedGraph;



public class MST_QUEUE {

	private WeightedGraph graph;
	private Node<?> tree;
	private Queue<Node<?>> queue;
	private Map<Vertex<?>,Node<?>> map;

	public MST_QUEUE(WeightedGraph graph,Vertex<?> vertex){
		this.graph=graph;
		init(vertex);
	}
	public void init(Vertex<?> vertex){
		queue=new PriorityQueue<Node<?>>(new NodeComparator());
		tree=new Node<Vertex<?>>(vertex);
		map=new HashMap<Vertex<?>,Node<?>>();
		
		tree.setKey(0D);
		for(Vertex<?> v:graph.getVertexes()){
			Node<Vertex<?>> node=new Node<Vertex<?>>(v);
			node.setKey(Double.MAX_VALUE);
			node.setParent(null);
			if(!v.equals(vertex)){
				queue.add(node);
				map.put(v, node);
			}
			else{
				queue.add(tree);
				map.put(v, tree);
			}
		}

	}
	
	public void init2(){
		
	}
	
	public Set<Node<?>> mst(){
		Node<?> u;
		Set<Node<?>> nodes=new HashSet<Node<?>>();
		Set<Vertex<?>> adjacents=new HashSet<Vertex<?>>();
		double weight;
		while(!queue.isEmpty()){
			u=queue.poll();
//			if(u.getParent()!=null)
//			System.out.println("node: "+((Scient)((Vertex<?>)u.getData()).getData()).getNameCn()+", parent: "+((Scient)((Vertex<?>)u.getParent().getData()).getData()).getNameCn()+", key: "+u.getKey());
			adjacents=graph.getAdjacents((Vertex<?>)u.getData());
			for(Vertex<?> v:adjacents){
				Node<?> node=map.get(v);
				weight=(double)graph.getWeight(v, (Vertex<?>)u.getData());
				if(queue.contains(node)&&weight<node.getKey()){
					node.setKey(weight);
					node.setParent(u);
				}
			}
/*			Iterator<Node<?>> it=queue.iterator();
			//System.out.println(queue.size());
			while(it.hasNext()){
				Node<?> node=it.next();
				//System.out.println(node.getData());
				if(node!=null && node.getData()!=null&&((Vertex<?>)node.getData()).getData()!=null)
				System.out.println("element: "+((Scient)((Vertex<?>)node.getData()).getData()).getNameCn()+", key: "+node.getKey());
			}*/
/*			Collections.sort(list);
			for(Node node:list){
				System.out.println("ele: "+((Scient)((Vertex<?>)node.getData()).getData()).getNameCn()+", "+node.getKey());
			}*/
			
			nodes.add(u);
		}
		return nodes;
	}
	
	public Node<?> generateTree(Set<Node<?>> nodes){
		for(Node<?> node:nodes){
			
		}
		return null;
	}
	
	public static void main(String[] args){
		Queue<Object> queue=new PriorityQueue<Object>();
		queue.add(3);
		queue.add(5);
		queue.add(7);
		queue.add(9);
		System.out.println(queue.peek()+" "+queue.size());
		System.out.println(queue.poll()+" "+queue.size());
	}

}

