package com.owen.algorithm.application.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

import com.owen.algorithm.math.graph.mst.Node;


public class TreeUtils {

	public static void getNodes(Node<?> root, List<Node<?>> nodes){
		nodes.add(root);
		for(Node<?> node:nodes){
			getNodes(node);
		}
	}
	
	public static List<Node<?>> getNodes(Node<?> root){
		List<Node<?>> nodes=new ArrayList<Node<?>>();
		Queue<Node<?>> queue=new PriorityQueue<Node<?>>();
		queue.addAll(root.getChildren());
		nodes.add(root);
		while(!queue.isEmpty()){
			Node<?> node=queue.poll();
			nodes.add(node);
			queue.addAll(node.getChildren());
		}
		return nodes;
	}
	
	// recursive
	public static void getLevelMap(Node<?> root,int i,Map<Node<?>,Integer> levelMap){
		levelMap.put(root, i);
		i++;
		for(Node<?> node:root.getChildren()){
			getLevelMap(node,i,levelMap);
		}
	}
	
	public static Map<Integer, ArrayList<Node<?>>> getLevels(Node<?> root){
		Map<Node<?>,Integer> levelMap=new HashMap<Node<?>,Integer>();
		Map<Integer, ArrayList<Node<?>>> levels=new HashMap<Integer,ArrayList<Node<?>>>();
		getLevelMap(root,0,levelMap);
		for(Entry<Node<?>,Integer> entry:levelMap.entrySet()){
			Integer key=entry.getValue();
			if(levels.containsKey(key)){
				levels.get(key).add(entry.getKey());
			}else{
				List<Node<?>> list=new ArrayList<Node<?>>();
				list.add(entry.getKey());
				levels.put(key, (ArrayList<Node<?>>) list);
			}
		}
		return levels;
	}
	
	public static void getDepth(Node<?> root,Integer depth){
		depth=new Integer(depth.intValue()+1);
		for(Node<?> node:root.getChildren()){
			getDepth(node,depth);
		}
	}
	
	public static void getDepth(Node<?> root,List<Integer> depth){
		depth.set(0, depth.get(0)+1);
		for(Node<?> node:root.getChildren()){
			getDepth(node,depth);
		}
	}
	
	// iterative
	public static List<Node<?>> getNodesByLevel(Node<?> root, int level){
		Queue<Node<?>> current=new PriorityQueue<Node<?>>();
		Queue<Node<?>> next=new PriorityQueue<Node<?>>();
		Queue<Node<?>> temp;
		List<Node<?>> result=new ArrayList<Node<?>>();
		current.addAll(root.getChildren());
		for(int i=1;i<level;i++){
			while(!current.isEmpty()){
				Node<?> node=current.poll();
				next.addAll(node.getChildren());
			}
			temp=next;
			next=current;
			current=temp;
		}
		
		if(level==0){
			result.add(root);
			return result;
		}else{
			result.addAll(current);
			return result;
		}
	}
	
	// recursive 
	public static void prune(Node<?> root,List<Node<?>> nodes,List<Node<?>> forest){
		// loop through the tree, record the node and its level that needed to prune to a data structure(eg. treeMap), and prune them in order
		for(Node<?> node:root.getChildren()){
			prune(node,nodes,forest);
			if(nodes.contains(node)){
				node.getParent().getChildren().remove(node);
				node.setParent(null);
				forest.add(node);
			}
		}
	}
	
	// Iterative
	public static List<Node<?>> prune(Node<?> root, List<Node<?>> nodes){
		Queue<Node<?>> queue=new PriorityQueue<Node<?>>();
		List<Node<?>> forest=new ArrayList<Node<?>>();
		forest.add(root);
		queue.addAll(root.getChildren());
		while(!queue.isEmpty()){
			Node<?> node=queue.poll();
			queue.addAll(node.getChildren());
			if(nodes.contains(node)){
				node.getParent().getChildren().remove(node);
				node.setParent(null);
				forest.add(node);
			}
		}
		return forest;
	}
	
	// temp method
	public static void printTree(Node<?> root){
		Queue<Node<?>> queue=new PriorityQueue<Node<?>>();
		System.out.println("root, "+((Spot)((Vertex<?>)root.getData()).getData()).getNameCn()+", key: "+root.getKey());
		queue.addAll(root.getChildren());
		while(!queue.isEmpty()){
			Node<?> node=queue.poll();
			queue.addAll(node.getChildren());
			System.out.println("name, "+((Spot)((Vertex<?>)node.getData()).getData()).getNameCn()+", key: "+node.getKey());
		}
	}
	
	public static void printNodes(Node<?> root){
		System.out.println("node, "+((Spot)((Vertex<?>)root.getData()).getData()).getNameCn()+", key: "+root.getKey());
		for(Node<?> node:root.getChildren()){
			printNodes(node);
		}
	}
	

	// iterative
	public static void getLevels(Node<?> root, Map<Node<?>,Integer> levels){
		
		/*		int i=0;
		Queue<Node<?>> queue=new PriorityQueue();
		queue.add(root);
		while(!queue.isEmpty()){
			Node<?> node=queue.poll();
		}*/
		
	}
	
}
