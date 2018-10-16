package controller;

import java.util.List;

import controller.OrderCtrl.NullOrderException;
import database.OrderDB;
import database.PaymentDB;
import database.ResupplyDB;
import model.Customer;
import model.Order;
import model.Payment;
import model.ProcessStatus;
import model.Product;
import model.Resupply;
/**
 * The functionality of the Resupply class.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class ResupplyCtrl {
	/**
	 * Throw an exception if a resupply can not be created.
	 *
	 */
	public static class NullResupplyException extends NullPointerException {

		public NullResupplyException() {
			super("A resupply was not created.");
		}

	}

	private Resupply tempResupply;

	/**
	 * Find a resupply in the database.
	 * 
	 * @param resupplyID
	 *            An Integer for the resupply'id.
	 * @return An object of the type Resupply found by the user.
	 */
	public Resupply findResupply(int resupplyID) {
		return new ResupplyDB().selectResupply(resupplyID);
	}

	/**
	 * Show all the resupply from the table in database.
	 * 
	 * @return A list with all the resupply.
	 */
	public List<Resupply> findAllResupplies() {
		return new ResupplyDB().selectAllResupplies();
	}

	public List<Payment> findAllPayments() {
		return new PaymentDB().selectAllPayments();
	}

	public void startResupply(String supplierName) {
		tempResupply = new Resupply(supplierName);
	}

	/**
	 * Add a product in the resupply.
	 * 
	 * @param barcode
	 *            A String for the product's barcode.
	 * @param quantity
	 *            An Integer for the quantity of products.
	 * @throws NullResupplyException
	 *             If there is no resupply in the database.
	 */
	public void addProduct(String barcode, int quantity) throws NullResupplyException {
		Product prod = new ProductCtrl().findProduct(barcode);
		if (tempResupply == null)
			throw new NullResupplyException();
		else {
			tempResupply.addProduct(prod, quantity);
		}
	}

	public void finishResupply() {
		new ResupplyDB().insertResupply(tempResupply);
		tempResupply = null;
	}

	/**
	 * Updates the status for the resupply.
	 * 
	 * @param resupplyID
	 *            An Integer for the resupply'id.
	 * @param status
	 *            An Object of the type ProcessStatus for the resupply status.
	 */
	public void updateStatus(int resupplyID, ProcessStatus status) {
		new ResupplyDB().updateResupply(resupplyID, status);
	}
}
