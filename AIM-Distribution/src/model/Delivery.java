package model;

import java.sql.Date;

/**
 * The Delivery ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Delivery {
	
	public Order ord;
	public Route route;
	public int id;
	public Date deliveryDate;
	
	/**
	 * The constructor of the class.
	 * @param ord An object of the type Order be used when a delivery is created.
	 * @param route An object of the type Route used when a delivery is created.
	 */
	public Delivery(Order ord, Route route){
		this.ord = ord;
		this.route = route;
	}

}
