package util;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {
	
	public List<GraphNode<T>> nodes;
	
	public Graph(){
		nodes = new ArrayList<GraphNode<T>>();
	}
	
	public void addNode(GraphNode<T> element){
		nodes.add(element);
	}

	public void removeNode(GraphNode<T> element){
		nodes.remove(element);
	}
}
