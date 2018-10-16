package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import util.Session;

/**
 * The Process ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public abstract class Process {

	public double totalAmount;
	public Date processDate;
	public ProcessStatus status;
	public List<OrderProduct> products;
	public Employee employee;

	/**
	 * The constructor of the class.
	 */
	public Process() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		processDate = new Date(System.currentTimeMillis());
		status = ProcessStatus.ORDERED;
		products = new LinkedList<>();
		this.employee = Session.employee;
	}

	/**
	 * The constructor of the class.
	 * 
	 * @param totalAmount
	 *            A double for the total amount of the order.
	 * @param processDate
	 *            An Object of the type Date for the date of the order's
	 *            process.
	 * @param status
	 *            An Object of the type ProcessStatus for the status of the
	 *            order.
	 * @param employee
	 *            An Object of the type Employee to associate the employee
	 *            responsible with the order with the order.
	 */
	public Process(double totalAmount, Date processDate, ProcessStatus status, Employee employee) {
		this.totalAmount = totalAmount;
		this.processDate = processDate;
		this.status = status;
		this.employee = employee;
		products = new LinkedList<>();
	}

	/**
	 * The method that sets all the products that will be ordered.
	 * 
	 * @param products
	 *            A list of the products that will be part of the order.
	 */
	public void setProducts(List<OrderProduct> products) {
		this.products.addAll(products);
	}

	/**
	 * This method add a product to the order's process.
	 * 
	 * @param p
	 *            An Object of the type Product that will be processed in the
	 *            order.
	 * @param quantity
	 *            An Integer for the product's quantity.
	 */
	public void addProduct(Product p, int quantity) {
		products.add(new OrderProduct(p, quantity));
	}

	/**
	 * This method remove a product from order process.
	 * 
	 * @param p
	 *            An Object of the type Product for the product removed.
	 */
	public void removeProduct(Product p) {
		for (OrderProduct op : products)
			if (op.product.barcode.equals(p.barcode)) {
				products.remove(op);
				break;
			}
	}

	/**
	 * This method calculate the final price for the product.
	 * 
	 * @param discountPercentage
	 *            A double for the discount percentage.
	 * @return the final product price as a double.
	 */
	public double getFinalPrice(double discountPercentage) {
		totalAmount = (1.0 - discountPercentage) * getTotalAmount();
		return totalAmount;
	}

	/**
	 * This method calculate the total price of the order.
	 * 
	 * @return The total amount of the order as a double.
	 */
	private double getTotalAmount() {
		double result = 0;

		for (OrderProduct op : products) {
			System.out.println(op.product.name + ": " + op.price + " x " + op.quantity);
			result += op.price * op.quantity;
		}

		return result;
	}

}
