package model;
/**
* The Order product ModelLayer interface.
* @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
* @version 08.05.2016
*/

public class OrderProduct {

	public Product product;
	public int quantity;
	public double price;
	
	/**
	 * The constructor of the class.
	 * @param quantity An integer for the quantity of the products ordered.
	 * @param price An integer for the product ordered price.
	 */
	public OrderProduct(Product product, int quantity){
		this.product = product;
		this.quantity = quantity;
		this.price = product.price;
	}
}
