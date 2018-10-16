package model;

import java.sql.Date;

/**
 * The Order ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Order extends Process {

	public int orderID;
	public DeliveryPoint point;

	/**
	 * The constructor for the class used when create an order using the
	 * delivery point.
	 * 
	 * @param point
	 *            An object of the type DeliveryPoint to create the order.
	 */
	public Order(DeliveryPoint point) {
		super();
		this.point = point;
	}

	/**
	 * The constructor of the class used to register an order.
	 * 
	 * @param totalAmount
	 *            A double for the total amount of the order.
	 * @param orderDate
	 *            An object of the type date for the order's date.
	 * @param status
	 *            An object of the type ProcessStatus for the order's status.
	 * @param employee
	 *            An object of the type Employee for the responsible employee
	 *            with the order.
	 * @param orderID
	 *            An Integer of the order's id.
	 * @param point
	 *            An Object of the type DeliveryPoint for the order's delivery
	 *            point.
	 */
	public Order(double totalAmount, Date orderDate, ProcessStatus status, Employee employee, int orderID,
			DeliveryPoint point) {
		super(totalAmount, orderDate, status, employee);
		this.orderID = orderID;
		this.point = point;
	}

}
