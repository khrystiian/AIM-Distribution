package model;

/**
 * The Truck ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Truck {

	public int plateNo;
	public boolean available;

	/**
	 * The constructor of the class
	 * 
	 * @param plateNo
	 *            An Integer for the plate number of the truck.
	 */
	public Truck(int plateNo) {
		this.plateNo = plateNo;
		available = true;
	}
}
