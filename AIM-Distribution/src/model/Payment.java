package model;

import java.util.Date;

/**
 * The Payment ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Payment {

	public int orderID;
	public double amount;
	public Date paymentDate;

	/**
	 * The constructor of the class used to create a payment.
	 * 
	 * @param orderID
	 *            An Integer for the order's id.
	 * @param amount
	 *            A double for the total amount.
	 */
	public Payment(int orderID, double amount) {
		this.orderID = orderID;
		this.amount = amount;
		paymentDate = new Date();
	}

	/**
	 * The constructor of the class.
	 * 
	 * @param orderID
	 *            An Integer for the order's id.
	 * @param amount
	 *            A double for the total amount.
	 * @param paymentDate
	 *            An Object of the type Date for the payment date.
	 */
	public Payment(int orderID, double amount, Date paymentDate) {
		this.orderID = orderID;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

}
