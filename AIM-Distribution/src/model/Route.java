package model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import util.Graph;
import util.GraphNode;

/**
 * The Route ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Route {

	public int routeID;
	public List<Delivery> deliveries;
	public boolean finished;
	public Time optimalTime, deliveryTime, startingTime;
	public Truck truck;
	public Employee emp;
	public Graph<Delivery> graph;

	/**
	 * The constructor of the class to create a route in database table.
	 * 
	 * @param routeID
	 *            An Integer for the route'id.
	 * @param truck
	 *            An Object of the type Truck for the truck that will follow the
	 *            route.
	 * @param emp
	 *            An Object of the type Employee for the employee responsible
	 *            with the route.
	 */
	public Route(int routeID, Truck truck, Employee emp) {
		deliveries = new LinkedList<Delivery>();
		graph = new Graph<>();
		finished = false;
		this.truck = truck;
		this.emp = emp;
		this.routeID = routeID;
	}

	/**
	 * The constructor of the class for creating a route.
	 * 
	 * @param truck
	 *            An Object of the type Truck for the truck that will follow the
	 *            route.
	 * @param emp
	 *            An Object of the type Route for the employee responsible with
	 *            the route.
	 */
	public Route(Truck truck, Employee emp) {
		deliveries = new LinkedList<Delivery>();
		graph = new Graph<>();
		finished = false;
		this.truck = truck;
		this.emp = emp;
	}

	/**
	 * This method add a delivery to the route.
	 * 
	 * @param ord
	 *            An Object of the type Order the delivery will have.
	 */
	public void addDelivery(Order ord) {
		Delivery del = new Delivery(ord, this);
		deliveries.add(del);
		GraphNode<Delivery> node = new GraphNode<>(del);
		graph.addNode(node);
		if (!graph.nodes.isEmpty()) {
			createEdge(del);
		}
	}

	/**
	 * This method creates an edge between two graph's node.
	 * 
	 * @param del
	 *            An Object of the type Delivery.
	 */
	public void createEdge(Delivery del) {
		GraphNode<Delivery> lastVisitedNode = graph.nodes.get(graph.nodes.size() - 1);
		GraphNode<Delivery> lastNode = new GraphNode<>(del);
		lastVisitedNode.addNeighbour(lastNode);
	}
}
