package model;

/**
 * The DeliveryPoint ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class DeliveryPoint {

	public String address, zipcode, city, region, country;
	public Customer customer;
	public int id;
	
	/**
	 * The constructor of the class.
	 * @param customer A Customer object uset to create a delivery point.
	 * @param address A String for the delivery's address.
	 * @param zipcode A String for the delivery's zip code.
	 * @param city A String for the delivery's city.
	 * @param region A String for the delivery's region.
	 * @param country A String for the delivery's country.
	 */
	public DeliveryPoint(Customer customer, String address, String zipcode, String city, String region, String country){
		this.customer = customer;
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		this.region = region;
		this.country = country;
	}
}
