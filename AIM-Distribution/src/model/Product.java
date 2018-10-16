package model;

/**
 * The Product ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */

public class Product {

	public String barcode, name, description;
	public int quantity, minStock;
	public double price;

	/**
	 * The constructor of the class.
	 * 
	 * @param barcode
	 *            A String for the product's barcode.
	 * @param name
	 *            A String for the product's name.
	 * @param description
	 *            A String for the product's description.
	 * @param quantity
	 *            An integer for the product's quantity.
	 * @param price
	 *            A double for the product's price.
	 * @param minStock
	 *            An integer for the low quantity of the product.
	 */
	public Product(String barcode, String name, double price, int quantity, int minStock, String description) {
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.minStock = minStock;
	}
}
