package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.CustomerDB;
import database.DeliveryPointDB;
import model.Customer;
import model.DeliveryPoint;

/**
 * The functionality of the Customer class.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class CustomerCtrl {

	private Customer tempCustomer;

	/**
	 * The method find a customer using the id.
	 * 
	 * @param id
	 *            An integer as customer's id to be found.
	 * @return An object of the type Customer.
	 */
	public Customer findCustomerByID(int id) {
		try {
			return new CustomerDB().findCustomerById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Find a customer using the name.
	 * 
	 * @param name
	 *            A String as customer's name to be found.
	 * @return An object of the type Customer.
	 */
	public Customer findCustomerByName(String name) {
		try {
			return new CustomerDB().findCustomerByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get all the customers.
	 * 
	 * @return A list with all the customers registered.
	 */
	public List<Customer> getAllCustomers() {
		List<Customer> list = new ArrayList<Customer>();
		try {
			list.addAll(new CustomerDB().allCustomers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Delete a customer.
	 * 
	 * @param id
	 *            An integer for the customer to be found in order to be
	 *            deleted.
	 */
	public void deleteCustomer(int id) {
		new CustomerDB().deleteCustomer(id);
	}

	/**
	 * Insert a new customer in the database.
	 * 
	 * @param name
	 *            A String used as customer's name.
	 * @param phone
	 *            A String used as customer's phone number.
	 * @param email
	 *            A String used as customer's email address.
	 */
	public void createCustomer(String name, String phone, String email) {
		tempCustomer = new Customer(name, phone, email);
	}

	/**
	 * This method finds a delivery point in the database.
	 * 
	 * @param id
	 *            An Integer for the delivery point id.
	 * @return An Object of the type DeliveryPoint found.
	 */
	public DeliveryPoint findDeliveryPoint(int id) {
		return new DeliveryPointDB().selectPoint(id);
	}

	/**
	 * This method finds all the delivery points in the database.
	 * 
	 * @return A list with all delivery points found.
	 */
	public List<DeliveryPoint> findAllDeliveryPoints() {
		return new DeliveryPointDB().getAllDeliveryPoints();
	}

	/**
	 * This method add a delivery point in the database.
	 * 
	 * @param address
	 *            A String for the delivery address.
	 * @param zipcode
	 *            A String for the delivery zip code.
	 * @param city
	 *            A String for the delivery city.
	 * @param region
	 *            A String for the delivery region.
	 * @param country
	 *            A String for the delivery country.
	 */
	public void addDeliveryPoint(String address, String zipcode, String city, String region, String country) {
		DeliveryPoint dp = new DeliveryPoint(tempCustomer, address, zipcode, city, region, country);
		tempCustomer.points.add(dp);

	}

	/**
	 * This method creates a new delivery point.
	 * 
	 * @param address
	 *            A String for the delivery address.
	 * @param zipcode
	 *            A String for the delivery zip code.
	 * @param city
	 *            A String for the delivery city.
	 * @param region
	 *            A String for the delivery region.
	 * @param country
	 *            A String for the delivery country.
	 */
	public void addDeliveryPoint(int custID, String address, String zipcode, String city, String region,
			String country) {
		Customer cust = findCustomerByID(custID);
		DeliveryPoint dp = new DeliveryPoint(cust, address, zipcode, city, region, country);
		new DeliveryPointDB().insertDeliveryPoint(dp);
	}

	/**
	 * This method add the customer with delivery points in the database.
	 */
	public void finishCustomerInsert() {
		new CustomerDB().insertCustomer(tempCustomer);
		tempCustomer = null;
	}

	/**
	 * This method finds a delivery point by city.
	 * 
	 * @param city
	 *            A String for the user as a input to find the delivery point.
	 * @return A list with all delivery points in the city the user used as a
	 *         input.
	 */
	public List<DeliveryPoint> findDeliveryPointsByCity(String city) {
		return new DeliveryPointDB().selectPointsByCity(city);
	}

	/**
	 * This method finds a delivery point by region.
	 * 
	 * @param region
	 *            A String for the user as a input to find the delivery point.
	 * @return A list with all delivery points in the region the user used as a
	 *         input.
	 */
	public List<DeliveryPoint> findDeliveryPointsByRegion(String region) {
		return new DeliveryPointDB().selectPointsByRegion(region);
	}

	/**
	 * This method remove a delivery point from database.
	 * 
	 * @param id
	 *            An Integer used by user as a input to find the delivery point
	 *            in order to be deleted.
	 */
	public void removeDeliveryPoint(int id) {
		new DeliveryPointDB().deleteDeliveryRoute(id);
	}

	/**
	 * This method has the functionality for updating a customer in database.
	 * 
	 * @param cust
	 *            An Object of the type Customer that will be updated.
	 */
	public void updateCustomer(Customer cust) {
		Customer dbCust = findCustomerByID(cust.id);
		int id = dbCust.id;

		if (!cust.name.equals(dbCust.name))
			changeName(id, cust.name);
		if (!cust.phone.equals(dbCust.phone))
			changePhone(id, cust.phone);
		if (!cust.email.equals(dbCust.email))
			changeEmail(id, cust.email);
	}

	/**
	 * This method updates the name of the customer.
	 * 
	 * @param id
	 *            An Integer for the customer's id to be found.
	 * @param name
	 *            A String for the new name given to the customer.
	 */
	public void changeName(int id, String name) {
		Customer customer = findCustomerByID(id);
		customer.name = name;
		new CustomerDB().updateCustomer(customer);
	}

	/**
	 * This method updates the email of the customer.
	 * 
	 * @param id
	 *            An Integer for the customer's id to be found.
	 * @param mail
	 *            A String for the new email given to the customer.
	 */
	public void changeEmail(int id, String mail) {
		Customer customer = findCustomerByID(id);
		customer.email = mail;
		new CustomerDB().updateCustomer(customer);
	}

	/**
	 * This method updates the phone number of the customer.
	 * 
	 * @param id
	 *            An Integer for the customer's id to be found.
	 * @param phone
	 *            A String for the new phone number given to the customer.
	 */
	public void changePhone(int id, String phone) {
		Customer customer = findCustomerByID(id);
		customer.phone = phone;
		new CustomerDB().updateCustomer(customer);
	}

	/**
	 * This method has the functionality for updating the delivery point of a
	 * customer.
	 * 
	 * @param delPoint
	 *            An Object of the type DeliveryPoint that will be updated.
	 */
	public void updateDeliveryPoint(DeliveryPoint delPoint) {
		DeliveryPoint dbDelPoint = findDeliveryPoint(delPoint.id);
		int id = dbDelPoint.id;

		if (!delPoint.address.equals(dbDelPoint.address))
			changeAddress(id, delPoint.address);
		if (!delPoint.zipcode.equals(dbDelPoint.zipcode))
			changeZip(id, delPoint.zipcode);
		if (!delPoint.city.equals(dbDelPoint.city))
			changeCity(id, delPoint.city);
		if (!delPoint.region.equals(dbDelPoint.region))
			changeRegion(id, delPoint.region);
		if (!delPoint.country.equals(dbDelPoint.country))
			changeCountry(id, delPoint.country);
	}

	/**
	 * This method updates the address of the delivery point.
	 * 
	 * @param id
	 *            An Integer for the delivery point's id to be found.
	 * @param address
	 *            A String for the new address given to the delivery point.
	 */
	public void changeAddress(int id, String address) {
		DeliveryPoint dp = findDeliveryPoint(id);
		dp.address = address;
		new DeliveryPointDB().updateDeliveryPoint(dp);
	}

	/**
	 * This method updates the region of the delivery point.
	 * 
	 * @param id
	 *            An Integer for the delivery point's id to be found.
	 * @param region
	 *            A String for the new region given to the delivery point.
	 */
	public void changeRegion(int id, String region) {
		DeliveryPoint dp = findDeliveryPoint(id);
		dp.region = region;
		new DeliveryPointDB().updateDeliveryPoint(dp);
	}

	/**
	 * This method updates the country of the delivery point.
	 * 
	 * @param id
	 *            An Integer for the delivery point's id to be found.
	 * @param country
	 *            A String for the new country given to the delivery point.
	 */
	public void changeCountry(int id, String country) {
		DeliveryPoint dp = findDeliveryPoint(id);
		dp.country = country;
		new DeliveryPointDB().updateDeliveryPoint(dp);
	}

	/**
	 * This method updates the zip code of the delivery point.
	 * 
	 * @param id
	 *            An Integer for the delivery point's id to be found.
	 * @param zip
	 *            A String for the new zip code given to the delivery point.
	 */
	public void changeZip(int id, String zip) {
		DeliveryPoint dp = findDeliveryPoint(id);
		dp.zipcode = zip;
		new DeliveryPointDB().updateDeliveryPoint(dp);
	}

	/**
	 * This method updates the city of the delivery point.
	 * 
	 * @param id
	 *            An Integer for the delivery point's id to be found.
	 * @param city
	 *            A String for the new city given to the delivery point.
	 */
	public void changeCity(int id, String city) {
		DeliveryPoint dp = findDeliveryPoint(id);
		dp.city = city;
		new DeliveryPointDB().updateDeliveryPoint(dp);
	}

	/**
	 * This method finds all the delivery points in the database for one
	 * customer.
	 * 
	 * @param custID
	 *            An Integer for the customer's id.
	 * @return A list of all the delivery points for the chosen customer.
	 */
	public List<DeliveryPoint> findAllPointsByCustomer(int custID) {
		return new DeliveryPointDB().selectPointsByCustomer(custID);
	}

	/**
	 * This method finds a delivery point in the database by address.
	 * 
	 * @param address
	 *            A String for the user to find the delivery point.
	 * @param custID
	 *            An Integer for the customer's id.
	 * @return An Object of the type DeliveryPoint found by address.
	 */
	public DeliveryPoint findPointByAddress(String address, int custID) {
		return new DeliveryPointDB().selectPointByAddress(address, custID);
	}

	/**
	 * This method will find the customer in the database using a delivery point
	 * id.
	 * 
	 * @param pointID
	 *            An Integer for the delivery point's id used to found the
	 *            customer.
	 * @return An Object of the type Customer.
	 */
	public Customer findCustomerByDeliveryPoint(int pointID) {
		int custID = new DeliveryPointDB().selectCustomerByDeliveryPoint(pointID);
		Customer cust = findCustomerByID(custID);
		return cust;
	}
}
