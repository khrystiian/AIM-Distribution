package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ProductDB;
import model.Product;

/**
 * The functionality of the Product class.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class ProductCtrl {
	/**
	 * Find a product in the database.
	 * 
	 * @param barcode
	 *            A String for the product's barcode used to find the product.
	 * @return An object of the type product.
	 */
	public Product findProduct(String barcode) {
		try {
			return new ProductDB().findProduct(barcode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Product findProductByName(String name) {
		return new ProductDB().findProductByName(name);
	}

	/**
	 * Get all the products registered in a list.
	 * 
	 * @return A list with the products.
	 */
	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<Product>();
		list.addAll(new ProductDB().allProducts());

		return list;
	}

	/**
	 * Update the product in the database.
	 * 
	 * @param prod
	 *            An Object of the type Product that will be then updated.
	 */
	public void updateProduct(Product prod) {
		Product dbProd = findProduct(prod.barcode);
		String barcode = dbProd.barcode;

		if (!prod.name.equals(dbProd.name))
			changeName(barcode, prod.name);
		if (prod.price != (dbProd.price))
			changePrice(barcode, prod.price);
		if (prod.quantity != (dbProd.quantity))
			changeQuantity(barcode, prod.quantity);
		if (!prod.description.equals(dbProd.description))
			changeDescription(barcode, prod.description);
		if (prod.minStock != (dbProd.minStock))
			changeMinStock(barcode, prod.minStock);

	}

	/**
	 * Change the name of the product with a new one.
	 * 
	 * @param barcode
	 *            A String for the product's barcode.
	 * @param name
	 *            A String for the product's name.
	 */
	public void changeName(String barcode, String name) {
		Product prod = findProduct(barcode);
		prod.name = name;
		new ProductDB().updateProduct(prod);
	}

	public void changePrice(String barcode, double price) {
		Product prod = findProduct(barcode);
		prod.price = price;

		new ProductDB().updateProduct(prod);

	}

	public void changeQuantity(String barcode, int quantity) {
		Product prod = findProduct(barcode);
		prod.quantity = quantity;

		new ProductDB().updateProduct(prod);

	}

	public void changeMinStock(String barcode, int minStock) {
		Product prod = findProduct(barcode);
		prod.minStock = minStock;

		new ProductDB().updateProduct(prod);

	}

	public void changeDescription(String barcode, String description) {
		Product prod = findProduct(barcode);
		prod.description = description;

		new ProductDB().updateProduct(prod);

	}

	/**
	 * Delete a product.
	 * 
	 * @param barcode
	 *            A String for the product that will found and deleted.
	 */
	public void delete(String barcode) {
		new ProductDB().deleteProduct(barcode);
	}

	/**
	 * Insert a new product in the database.
	 * 
	 * @param barcode
	 *            A String for the product's barcode.
	 * @param name
	 *            A String for the product's name.
	 * @param price
	 *            A double for the product's price.
	 * @param quantity
	 *            An Integer for the product's quantity.
	 * @param minStock
	 *            An Integer for the product's low stock.
	 * @param description
	 *            A String for the product's description.
	 */
	public void insert(String barcode, String name, double price, int quantity, int minStock, String description) {
		Product prod = new Product(barcode, name, price, quantity, minStock, description);
		new ProductDB().insertProduct(prod);

	}

}
