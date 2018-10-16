package controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import database.DeliveryDB;
import database.DeliveryPointDB;
import database.EmployeeDB;
import database.OrderDB;
import database.RouteDB;
import database.TruckDB;
import model.Delivery;
import model.DeliveryPoint;
import model.Employee;
import model.Order;
import model.Route;
import model.Truck;

/**
 * The functionality of the Route class.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class RouteCtrl {
	/**
	 * Throw an exception if a resupply can not be created.
	 *
	 */
	public class NullRouteException extends NullPointerException {

		public NullRouteException() {
			super("A route was not created.");
		}
	}

	private Route tempRoute;

	/**
	 * Find a truck in the database.
	 * 
	 * @param plateNo
	 *            An Integer for the truck's id.
	 * @return An Object of the type Truck.
	 */
	public Truck findTruck(int plateNo) {
		return new TruckDB().selectTruck(plateNo);
	}

	/**
	 * Show all the trucks from database.
	 * 
	 * @return A list with all the trucks.
	 */
	public List<Truck> findAllTrucks() {
		return new TruckDB().getAllTrucks();
	}

	/**
	 * Add a truck in the database.
	 * 
	 * @param plateNo
	 *            An Integer for the truck's plate number.
	 */
	public void addTruck(int plateNo) {
		new TruckDB().insertTruck(new Truck(plateNo));
	}

	/**
	 * Remove a truck in the database.
	 * 
	 * @param plateNo
	 *            An Integer for the truck's plate number.
	 */
	public void removeTruck(int plateNo) {
		new TruckDB().deleteTruck(plateNo);
	}

	public Delivery findDelivery(int id) {
		return new DeliveryDB().selectDelivery(id);
	}

	public List<Delivery> findAllDeliveries() {
		return new DeliveryDB().getAllDeliveries();
	}

	public List<Delivery> findRouteDeliveries(int routeID) {
		return new DeliveryDB().getRouteDeliveries(routeID);
	}

	public void addDelivery(int ordID) throws NullRouteException {
		Order ord = new OrderDB().selectOrder(ordID);

		if (tempRoute == null)
			throw new NullRouteException();
		else
			tempRoute.addDelivery(ord);
	}

	public void removeDelivery(int id) {
		new DeliveryDB().deleteDelivery(id);
	}

	public Route findRoute(int id) {
		Route route = new RouteDB().selectRoute(id);
		route.deliveries.addAll(findRouteDeliveries(id));
		return route;
	}

	public List<Route> findAllRoutes() {
		return new RouteDB().getAllRoutes();
	}

	/**
	 * Creates a new route. Find a available truck and assign to the route.
	 * 
	 * @param empID
	 *            An Integer for the employee'id.
	 */
	public void createRoute(int empID) {
		Truck truck = new TruckDB().selectAvailableTruck(true);
		Employee emp = new EmployeeDB().selectEmployee(empID);

		tempRoute = new Route(truck, emp);
	}

	public void finishRoute() {
		new RouteDB().insertRoute(tempRoute);
		tempRoute = null;
	}

	public void removeRoute(int routeID) {
		new RouteDB().deleteRoute(routeID);
	}

	/**
	 * Change the status of a truck .
	 * 
	 * @param plateNo
	 *            An Integer for the plate number.
	 * @param available
	 *            A boolean for the status of the truck.
	 */
	public void changeTruckStatus(int plateNo, boolean available) {
		Truck t = findTruck(plateNo);
		t.available = available;
		new TruckDB().updateTruck(t);
	}

	public void changeDeliveryDate(int id, Date date) {
		Delivery del = findDelivery(id);
		del.deliveryDate = date;
		new DeliveryDB().updateDelivery(del);
	}

	public void changeRouteDeliveryTime(int id, Time time) {
		Route route = findRoute(id);
		route.deliveryTime = time;
		new RouteDB().updateRoute(route);
	}

	public void changeRouteStatus(int id, boolean finished) {
		Route route = findRoute(id);
		route.finished = finished;
		new RouteDB().updateRoute(route);
	}

	public void changeStartingTime(int id, Time time) {
		Route route = findRoute(id);
		route.startingTime = time;
		new RouteDB().updateRoute(route);
	}

}
