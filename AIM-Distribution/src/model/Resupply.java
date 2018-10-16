package model;

import java.sql.Date;

/**
 * The Resupply ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Resupply extends Process {

	public int resupplyID;
	public String supplier;

	/**
	 * The constructor for the class for creating a supplier.
	 * 
	 * @param supplier
	 *            A String for the supplier.
	 */
	public Resupply(String supplier) {
		super();
		this.supplier = supplier;
	}

	/**
	 * The constructor of the class for inserting a supplier in the database.
	 * 
	 * @param resupplyID
	 *            An Integer for the supplier id.
	 * @param supplier
	 */
	public Resupply(int resupplyID, String supplier) {
		this(supplier);
		this.resupplyID = resupplyID;
	}

	/**
	 * This method creates a supply.
	 * 
	 * @param resupplyID
	 *            An Integer for the resupply' id.
	 * @param supplier
	 * @param totalAmount
	 *            A double for the total amount.
	 * @param processDate
	 *            An Object of the type date for the resupply date.
	 * @param status
	 *            An Object of the type ProcessStatus for the order process
	 *            status.
	 * @param employee
	 *            An Object of the type Employee for the employee responsible
	 *            for the resupply.
	 */
	public Resupply(int resupplyID, String supplier, double totalAmount, Date processDate, ProcessStatus status,
			Employee employee) {
		super(totalAmount, processDate, status, employee);
		this.resupplyID = resupplyID;
		this.supplier = supplier;
	}

}
