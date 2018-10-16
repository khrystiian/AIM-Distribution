package util;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {

	public T data;
	public List<GraphNode<T>> neighbours = new ArrayList<GraphNode<T>>();

	public GraphNode(T data) {
		this.data = data;
	}

	public void addNeighbour(GraphNode<T> element) {
		if (!neighbours.contains(element)) {
			neighbours.add(element);
			element.addNeighbour(this);
		}
	}

	public void removeNeighbour(GraphNode<T> element) {
		if (!neighbours.contains(element)){
			neighbours.remove(element);
			element.removeNeighbour(this);
		}
	}
}
