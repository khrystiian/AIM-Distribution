package controller;

import java.util.List;

import database.DeliveryPointDB;
import database.OrderDB;
import database.PaymentDB;
import database.ProductDB;
import model.DeliveryPoint;
import model.Order;
import model.Payment;
import model.ProcessStatus;
import model.Product;

/**
 * The functionality of the Order class.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class OrderCtrl {

	/**
	 * This method will throw an exception if there is no order created in the
	 * database.
	 *
	 */
	public class NullOrderException extends NullPointerException {

		public NullOrderException() {
			super("An order was not created.");
		}

	}

	private Order tempOrder;

	/**
	 * Find an order in the database.
	 * 
	 * @param orderID
	 *            An Integer for the order's id to find it in the table in
	 *            database.
	 * @return An Object of the type Order found in database.
	 */
	public Order findOrder(int orderID) {
		return new OrderDB().selectOrder(orderID);
	}

	/**
	 * Find the payment for a specific order.
	 * 
	 * @param orderID
	 *            An Integer for the order's id.
	 * @return A list with all the payments.
	 */
	public List<Payment> findPaymentsForOrder(int orderID) {
		return new PaymentDB().selectPaymentsByOrder(orderID);
	}

	/**
	 * Find all the orders in the database.
	 * 
	 * @return A list will all the orders.
	 */
	public List<Order> findAllOrders() {
		return new OrderDB().selectAllOrders();
	}

	/**
	 * Find all the payments in the database.
	 * 
	 * @return A list with all the payments.
	 */
	public List<Payment> findAllPayments() {
		return new PaymentDB().selectAllPayments();
	}

	/**
	 * Do the payment for an order.
	 * 
	 * @param orderID
	 *            An Integer for the order's id.
	 * @param amount
	 *            An Integer for the order's amount.
	 */
	public void payOrder(int orderID, double amount) {
		new PaymentDB().insertPayment(new Payment(orderID, amount));
	}

	public void startOrder(int pointID) {
		DeliveryPoint dp = new DeliveryPointDB().selectPoint(pointID);
		tempOrder = new Order(dp);
	}

	/**
	 * Add a product in the order.
	 * 
	 * @param barcode
	 *            A String for the product's barcode.
	 * @param quantity
	 *            An Integer for the product's quantity.
	 * @throws NullOrderException
	 *             If the product does not exist in the database.
	 */
	public void addProduct(String barcode, int quantity) throws NullOrderException {
		Product prod = new ProductCtrl().findProduct(barcode);
		if (tempOrder == null)
			throw new NullOrderException();
		else {
			tempOrder.addProduct(prod, quantity);
			prod.quantity -= quantity;
			new ProductCtrl().changeQuantity(barcode, prod.quantity - quantity);
		}
	}

	public void finishOrder() {
		new OrderDB().insertOrder(tempOrder);
		tempOrder = null;
	}

	/**
	 * Change the status of an order.
	 * 
	 * @param orderID
	 *            An Object of the type Order for the order's id.
	 * @param status
	 *            An Object of the type ProcessStatus for the status of the
	 *            order.
	 */
	public void updateStatus(int orderID, ProcessStatus status) {
		new OrderDB().updateOrder(orderID, status);
	}

	/**
	 * Show all the orders from database with a given status.
	 * 
	 * @param status
	 *            A String for the status of the order.
	 * @return A list of all the orders.
	 */
	public List<Order> getAllOrdered(String status) {
		return new OrderDB().selectAllOrdered(status);
	}
}
