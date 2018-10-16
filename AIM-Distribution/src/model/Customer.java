package model;

import java.util.LinkedList;
import java.util.List;

/**
 * The Customer ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */

public class Customer {

	public String phone, name, email = null;
	public int id;
	public List<DeliveryPoint> points;

	/**
	 * The constructor of the class used to the table from database.
	 * 
	 * @param id
	 *            A String for the customer's id.
	 * @param phone
	 *            A String for the customer's phone number.
	 * @param name
	 *            A String for the customer's name.
	 * @param email
	 *            A String for the customer's email address.
	 */
	public Customer(int id, String phone, String name, String email) {
		this.id = id;
		this.phone = phone;
		this.name = name;
		this.email = email;
		points = new LinkedList<DeliveryPoint>();
	}

	/**
	 * The constructor of the class to create a new customer.
	 * 
	 * @param phone
	 *            A String for the customer's phone number.
	 * @param name
	 *            A String for the customer's name.
	 * @param email
	 *            A String for the customer's email address.
	 */
	public Customer(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		points = new LinkedList<DeliveryPoint>();
	}


}
